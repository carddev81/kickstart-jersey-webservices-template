/**
 * 
 */
package gov.doc.isu.ws.core;

/**
 * 
 * Class used for configuring Service layer Exceptions.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ServiceException() {
    }//end constructor

    /**
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }//end constructor

    /**
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }//end constructor

}//end class
