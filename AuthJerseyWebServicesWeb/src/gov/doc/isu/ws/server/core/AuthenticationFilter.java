/**
 *
 */
package gov.doc.isu.ws.server.core;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import gov.doc.isu.ws.authorization.AuthenticationService;
import gov.doc.isu.ws.authorization.TokenDTO;
import gov.doc.isu.ws.core.DAOException;
import gov.doc.isu.ws.core.TicketExpiredException;

/**
 * This AuthenticationFilter is responsible for authenticating user requests by validating request tokens and whether or not user has the proper elevated permissions per their {@code Role}.
 *
 * @author rsalas [filled in the template with the logic]
 * @author cassiomolin [created template]
 * @version 1.0.1
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger myLogger = Logger.getLogger("gov.doc.isu.ws.server.endpoints.AuthenticationEndpoint");
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Context
    private ResourceInfo resourceInfo;//used for retrieving the user roles on Class files and Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering filter() which validates user request tokens and makes sure the user has the correct permissions to access the endpoint methods/classes. Incoming parameter is: requestContext=" + String.valueOf(requestContext));
        }//end if

        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if(!isTokenBasedAuthentication(authorizationHeader)){
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = null;

        try{
            token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            // Validate the token
            validateToken(token);
            SecurityContext currentContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext(){

                @Override
                public String getAuthenticationScheme() {
                    return AUTHENTICATION_SCHEME;
                }

                @Override
                public Principal getUserPrincipal() {
                    return () -> "IVR.WEBSERVICE@OA.MO.GOV";//ApplicationSettings get value here
                }//end method

                @Override
                public boolean isSecure() {
                    return currentContext.isSecure();
                }//end method

                @Override
                public boolean isUserInRole(String arg0) {
                    return true;
                }//end method
            });

        }catch(Exception e){
            myLogger.error("Error occurred while trying to ...", e);
            abortWithUnauthorized(requestContext);
        }//end try...catch

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting filter() method. No return value.");
        }//end if
    }//end method

    /**
     * Returns true or false on whether or not the request has the common token based Header.
     *
     * @param authorizationHeader the token based header to check against
     * @return true or false on whether or not the request has the common token based Header.
     */
    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }//end method

    /**
     * Sends the abort with 401 response of unauthorized.
     *
     * @param requestContext the current ContainerRequestContext used for setting the abortWith Response
     */
    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build());
    }//end method

    /**
     * Validates whether a token is valid or not.
     *
     * @param token the token to validate
     * @return tokenDTO the validated {@code TokenDTO} instance
     * @throws Exception occurs when the token is invalid
     */
    private TokenDTO validateToken(String token) throws Exception {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering validateToken() which validates whether a token is valid or not. Incoming paramater is: token=" + String.valueOf(token));
        }//end if

        AuthenticationService service = new AuthenticationService();

        TokenDTO tokenDTO = null;
        try{
            tokenDTO = service.validateToken(token);
        }catch(TicketExpiredException e){
            myLogger.error("TicketExpiredException occurred because the token expired.  Error message is: " + e.getMessage(), e);
            throw e;
        }catch(Exception e){
            myLogger.error("Exception occurred while validating the token.  Error message is: " + e.getMessage(), e);
            throw e;
        }//end method

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting validateToken() method. Return value is tokenDTO=" + String.valueOf(tokenDTO));
        }//end if
        return tokenDTO;
    }//end method

}//end class
