/**
 * 
 */
package gov.doc.isu.ws.core;

/**
 * 
 * Class used for configuring Data Access Object Exceptions.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public DAOException() {
    }//end constructor

    /**
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }//end constructor

    /**
     * @param cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }//end constructor

}//end class
