/**
 * 
 */
package gov.doc.isu.ws.core;

/**
 * Class used for configuring Active Directory Validation Exceptions.
 * 
 * @author Richard Salas
 * @version 1..0.0
 */
public class ActiveDirectoryValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ActiveDirectoryValidationException() {
    }//end constructor

    /**
     * @param message
     */
    public ActiveDirectoryValidationException(String message) {
        super(message);
    }//end constructor

    /**
     * @param cause
     */
    public ActiveDirectoryValidationException(Throwable cause) {
        super(cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     */
    public ActiveDirectoryValidationException(String message, Throwable cause) {
        super(message, cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ActiveDirectoryValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }//end constructor

}//end class
