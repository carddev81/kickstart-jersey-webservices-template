/**
 * 
 */
package gov.doc.isu.ws.server.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import gov.doc.isu.ws.authorization.AuthenticationService;
import gov.doc.isu.ws.authorization.TokenDTO;
import gov.doc.isu.ws.authorization.UserDTO;
import gov.doc.isu.ws.core.ActiveDirectoryValidationException;
import gov.doc.isu.ws.core.DAOException;

/**
 * Authentication Web service endpoints for authenticating users requests and issueing tokens.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
@Path("/authenticate")
public class AuthenticationEndpoint {

    private static Logger myLogger = Logger.getLogger("gov.doc.isu.ws.server.endpoints.AuthenticationEndpoint");

    /**
     * 
     */
    public AuthenticationEndpoint() {}// end constructor

    /**
     * Authenticates a user to make sure he/she is an Active Directory user and issues and returns a request token.
     * 
     * @param userDTO the user data object
     * @return either the 401 forbidden response or a 200 response with a token
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(UserDTO userDTO) {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering authenticateUser() which is used to authenticate user as a valid Active Directory user.  Incoming paramater is: userDTO=" + String.valueOf(userDTO));
        }//end if

        Response response = null;
        try{

            // Authenticate the user using the credentials provided
            UserDTO user = authenticate(userDTO);

            // Issue a token for the user
            TokenDTO token = issueToken(user);

            // Return the token on the response
            response = Response.ok(token).build();

        }catch(Exception e){
            response =  Response.status(Response.Status.FORBIDDEN).build();
        }//end try...catch

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting authenticateUser() method. Return value is: response=" + String.valueOf(response));
        }//end if
        return response;
    }//end method

    /**
     * Authenticates the given userDTO containing user data.
     * 
     * @param userDTO the user data
     * @return user the userDTO information
     * @throws Exception occurs when a user fails authentication
     */
    private UserDTO authenticate(UserDTO userDTO) throws Exception {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering authenticate() which is used to authenticate user as a valid Active Directory user.  Incoming paramater is: userDTO=" + String.valueOf(userDTO));
        }//end if

        UserDTO user = null;
        AuthenticationService service = new AuthenticationService();

        try{
            service.authenticateUser(userDTO);
            user = service.getUserByUserID(userDTO.getUserID());
        }catch(ActiveDirectoryValidationException e){
            myLogger.error("ActiveDirectoryValidationException occurred while verifying whether or not the user is a Windows Active Directory user.  Error message is: " + e.getMessage() + ".  Value used for verification process is: UserDTO=" + String.valueOf(userDTO), e);
            throw e;
        }catch(DAOException e){
            myLogger.error("DAOException occurred while checking whether or not the user is an accepted user (within the database).  Error message is: " + e.getMessage() + ".  Value used for verification process is: UserDTO=" + String.valueOf(userDTO), e);
            throw e;
        }// end try...catch

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting authenticate() method. Return value is: user=" + String.valueOf(user));
        }//end if
        return user;
    }// end method

    /**
     * Issues a authenticated request token using the given userDTO containing user data.
     * 
     * @param userDTO the user data used for issuing a token
     * @return tokenDTO the request token data
     * @throws Exception occurs when attempting to issue a request token
     */
    private TokenDTO issueToken(UserDTO userDTO) throws Exception {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering issueToken() which issues a authenticated request token using the given userDTO containing user data.  Incoming paramater is: userDTO=" + String.valueOf(userDTO));
        }//end if

        TokenDTO tokenDTO = null;
        AuthenticationService service = new AuthenticationService();

        try{
            tokenDTO = service.issueToken(userDTO);
        }catch(DAOException e){
            myLogger.error("DAOException occurred while creating authorization token for user. Error message is: " + e.getMessage() + ".  Value used for token generation was: UserDTO=" + String.valueOf(userDTO), e);
            throw e;
        }// end try...catch

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting issueToken() method. Return value is: tokenDTO=" + String.valueOf(tokenDTO));
        }//end if
        return tokenDTO;
    }// end method

}// end class
