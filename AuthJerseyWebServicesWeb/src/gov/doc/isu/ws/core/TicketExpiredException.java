/**
 * 
 */
package gov.doc.isu.ws.core;

/**
 * 
 * Class used for configuring Token Ticket Expired Exceptions.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class TicketExpiredException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public TicketExpiredException() {
    }//end constructor

    /**
     * @param message
     */
    public TicketExpiredException(String message) {
        super(message);
    }//end constructor

    /**
     * @param cause
     */
    public TicketExpiredException(Throwable cause) {
        super(cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     */
    public TicketExpiredException(String message, Throwable cause) {
        super(message, cause);
    }//end constructor

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TicketExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }//end constructor

}//end class
