package gov.doc.isu.ws.authorization;


import org.apache.log4j.Logger;



import waffle.windows.auth.IWindowsAuthProvider;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

import com.sun.jna.platform.win32.Win32Exception;

import gov.doc.isu.ws.core.ActiveDirectoryValidationException;

/**
 * Utility class using Active Directory to authenticate valid user sign on.
 *
 * @author Steven L. Skinner
 */

public class ActiveDirectoryValidator {

    private static final Logger LOGGER = Logger.getLogger("gov.doc.isu.webUtil.ActiveDirectoryValidator");

    /**
     * This method verifies username and password are correct for this domain
     *
     * @param userNameDomain
     *        the fully qualified Active Directory name
     * @param password
     *        the users password
     * @return String username
     * @throws ActiveDirectoryValidationException
     *         if an ActiveDirectoryValidationException occurred
     */
    public static String authenticateUser(String userNameDomain, String password) throws ActiveDirectoryValidationException {
        LOGGER.debug("Entering ActiveDirectoryValidator.authenticateUser()");
        LOGGER.debug("Paramaters: userNameDomain=" + String.valueOf(userNameDomain) + ", password is protected");
        try{
            String domain = null;
            String userNameHold = null;

            String[] nameArray = userNameDomain.split("@");
            if(nameArray.length == 2){
                userNameHold = nameArray[0];
                domain = nameArray[1];
            }// end if
            LOGGER.debug("Authenticating user name and password through Active Directory");
            IWindowsAuthProvider prov = new WindowsAuthProviderImpl();
            prov.logonDomainUser(userNameHold, domain, password);
        }catch(Win32Exception e){
            LOGGER.error("Win32Exception occurred in ActiveDirectoryValidator.authenticateUser(). e=" + e.getMessage());
            throw new ActiveDirectoryValidationException(e.getMessage());
        }// end try/catch
        LOGGER.debug("Exiting ActiveDirectoryValidator.authenticateUser().");
        return userNameDomain;
    }// end authenticateUser

    /**
     * This method verifies that the user name is a current user in domain
     *
     * @param userNameDomain
     *        the fully qualified active directory name
     * @return String username
     * @throws ActiveDirectoryValidationException
     *         if an ActiveDirectoryValidationException occurred
     */
    public static String verifyUser(String userNameDomain) throws ActiveDirectoryValidationException {
        LOGGER.debug("Entering ActiveDirectoryValidator.verifyUser()");
        LOGGER.debug("Paramaters: userNameDomain=" + String.valueOf(userNameDomain));
        try{
            IWindowsAuthProvider prov = new WindowsAuthProviderImpl();
            LOGGER.debug("verifying user is valid Active Directory User.");
            prov.lookupAccount(userNameDomain);
            LOGGER.debug("User is valid");
        }catch(Win32Exception e){
            LOGGER.error("Win32Exception occurred in ActiveDirectoryValidator.authenticateUser(). e=" + e.getMessage());
            throw new ActiveDirectoryValidationException(e.getMessage());
        }// end try/catch
        LOGGER.debug("Exiting ActiveDirectoryValidator.verifyUser().");
        return userNameDomain;
    }// end verifyUser

}// end class ActiveDirectoryValidator
