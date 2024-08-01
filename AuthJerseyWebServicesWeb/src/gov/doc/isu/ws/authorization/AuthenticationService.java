/**
 * 
 */
package gov.doc.isu.ws.authorization;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import gov.doc.isu.ws.core.ActiveDirectoryValidationException;
import gov.doc.isu.ws.core.DAOException;
import gov.doc.isu.ws.core.TicketExpiredException;
import gov.doc.isu.ws.server.core.Role;

/**
 * Authentication service used for handling the user authentication business operations.
 *  
 * @author Richard Salas
 * @version 1.0.0
 */
public class AuthenticationService {

    private static final Logger myLogger = Logger.getLogger("gov.doc.isu.ws.authorization.AuthenticationService");

    /**
     * Default constructor.
     */
    public AuthenticationService() {
    }//end constructor

    /**
     * Authenticates a user as a valid Active Directory user using the given userDTO.
     * 
     * @param userDTO the instance containing user data to be authenticated
     * @throws ActiveDirectoryValidationException occurs when user is not authorized to resource
     */
    public void authenticateUser(UserDTO userDTO) throws ActiveDirectoryValidationException{
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering authenticateUser() which is used to authenticate user as a valid Active Directory user. Used to authenticate a user. Incoming paramater is: userDTO=" + String.valueOf(userDTO));
        }//end if

        myLogger.info("Authenticating user " + String.valueOf(userDTO.getUserID()));
        ActiveDirectoryValidator.authenticateUser(userDTO.getUserID(), userDTO.getPassword());

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting authenticateUser() method. No return value.");
        }//end if
    }//end method

    /**
     * Gets a user by user id passed to this method.
     * 
     * @param userID the user id of the user to get
     * @return userDTO data transfer object containing user state
     * @throws DAOException occurs if there is an issue retrieving the user information
     */
    public UserDTO getUserByUserID(String userID) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getUserByUserID() which is used to get user by the given UserID.  Incoming paramater is: userID=" + String.valueOf(userID));
        }//end if

        UserDTO userDTO = null;
        UserDAO userDAO = new UserDAO();
        UserDO userDO = userDAO.getUserByUserID(userID);

        if(userDO != null){
            userDTO = userDO.toDTO();
        }//end if...else

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting authenticateUser() method. No return value.");
        }//end if
        return userDTO;
    }//end method

    /**
     * Issues a authenticated request token for the given userDTO.
     *  
     * @param userDTO the user data used for issuing an authenticated request token 
     * @return tokenDTO the data transfer object containing the authenticated request token data
     * @throws DAOException occurs when there is an issue trying to save the request token
     */
    public TokenDTO issueToken(UserDTO userDTO) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering issueToken() which issues a authenticated request token for the given userDTO.  Incoming paramater is: userDTO=" + String.valueOf(userDTO));
        }//end if

        TokenDO tokenDO = new TokenDO(); 
        tokenDO.setAlias(userDTO.getAlias());
        tokenDO.setUserID(userDTO.getUserID());
        tokenDO.setUserRefId(userDTO.getUserRefId());

        //get the next day
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date currentDatePlusOne = new Date(cal.getTime().getTime());
        tokenDO.setExpirationDt(currentDatePlusOne);

        //build token formated like <randomstring>-RTS000IS-yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String token = RandomStringUtils.randomAlphanumeric(20) + "-" + userDTO.getUserID().substring(0, userDTO.getUserID().indexOf("@")) + "-" + sdf.format(currentDatePlusOne);
        tokenDO.setToken(token);

        TokenDAO tokenDAO = new TokenDAO();
        tokenDAO.save(tokenDO);
        TokenDTO tokenDTO = tokenDO.toDTO();

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting issueToken() method. Return value issueToken=" +String.valueOf(tokenDTO));
        }//end if
        return tokenDTO;
    }//end method

    /**
     * Validates the given request token.
     *  
     * @param token the token to validate.
     * @return tokenDTO the token data retrieved when validating the given token
     * @throws Exception occurs when the ticket expires or the token is not valid
     */
    public TokenDTO validateToken(String token) throws Exception {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering validateToken() which is used to validate a token.  Incoming paramater is: token=" + String.valueOf(token));
        }//end if

        //retrieve token - exception possible
        TokenDAO tokenDAO = new TokenDAO();
        TokenDO tokenDO = tokenDAO.getById(token);
        //check expiration Dt - exception possible
        
        if(tokenDO == null){
            throw new IllegalStateException("User token could not be found was not found! The token searched for was " + String.valueOf(token));
        }//end if 
        

        Date currentDate= new Date(System.currentTimeMillis());
        if(currentDate.equals(tokenDO.getExpirationDt()) || currentDate.after(tokenDO.getExpirationDt())){
            tokenDAO.delete(tokenDO);
            throw new TicketExpiredException("User token expired.  The token that expired is:  " + String.valueOf(token));
        }//end if

        TokenDTO tokenDTO = tokenDO.toDTO();

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting validateToken() method. Return value tokenDTO=" +String.valueOf(tokenDTO));
        }//end if
        return tokenDTO;
    }//end method

    /**
     * Gets roles by using the given user reference id.
     * 
     * @param userRefId the user reference id used to retrieve the roles
     * @return roleList the list of roles retrieved
     * @throws DAOException occurs when attempting to retrieve the user roles
     */
    public List<Role> getRolesByUserRefId(Long userRefId) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getRolesByUserRefId() which is used to validate a token.  Incoming paramater is: userRefId=" + String.valueOf(userRefId));
        }//end if

        RoleDAO roleDAO = new RoleDAO();
        List<String> roleNamesList = roleDAO.getRoleNamesByUserRefId(userRefId);
        List<Role> roleList = roleNamesList.stream().map((name) -> Role.valueOf(name)).collect(Collectors.toList());

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting getRolesByUserRefId() method. Return value roleList=" + String.valueOf(roleList));
        }//end if
        return roleList;
    }//end method

}//end class
