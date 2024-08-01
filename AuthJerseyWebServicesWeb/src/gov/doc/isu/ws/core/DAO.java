package gov.doc.isu.ws.core;

import static gov.doc.isu.ws.core.Constants.LINESEPERATOR;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * DAO class contains methods for managing database connections.
 *
 * @author Joe Burris JCCC original author
 * @author Richard Salas JCC modification author (changed name and added Generics to this class)
 */
public abstract class DAO<T, IdT> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Logger myLogger = Logger.getLogger("gov.doc.isu.ws.core.DAO");
    
    public static final String JNDI_NAME = "jdbc/WebAPI";

    /**
     * Used to obtain connection to database.
     *
     * @return Connection Database connection
     * @throws DAOException
     *         DAOException
     */
    public Connection getConnection() throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getConnection() method which is used to obtain connection to database.");
        }//end if

        // Use this connection for deployed project.
        Connection conn = getConnection(JNDI_NAME);

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting getConnection() method.  Return value is: " + String.valueOf(conn));
        }//end if
        return conn;
    }// end getConnection

    /**
     * This method is used to retrieve a database connection from connnetionPool, we allow it to re-try at most three times. If failed, a exception is thrown.
     *
     * @param datasourceNameUrlString
     *        String
     * @return java.sql.Connection Database connection
     * @throws DAOException
     *         An exception if the method fails.
     */
    public Connection getConnection(String datasourceNameUrlString) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getConnection() method which is used to retrieve a database connection from connnetionPool, we allow it to re-try at most three times. If failed, a exception is thrown. Incoming parameter is: " + String.valueOf(datasourceNameUrlString));
        }//end if

        DataSource ds = null;
        Connection conn = null;
        try{
            InitialContext ic = new InitialContext();
            ds = (DataSource) ic.lookup(datasourceNameUrlString);
        }catch(NamingException ne){
            myLogger.error("NamingException while looking up DB context  : " + ne.getMessage());
            throw new DAOException("NamingException while looking up DB context  : " + ne.getMessage());
        } // end catch
        int count = 0;
        try{
            while(null == conn){
                // now get the database connection
                conn = ds.getConnection();
                if(conn == null){
                    count++;
                    if(count >= 4){
                        myLogger.error("failed to get connection in: " + count + " trials." + LINESEPERATOR);
                        throw new Exception("failed to get connection in: " + count + " trials." + LINESEPERATOR);
                    } // end if
                    try{
                        myLogger.error("couldn't get connection and waiting....");
                        Thread.sleep(3000); // wait 3000 millis
                    }catch(InterruptedException ie){
                        myLogger.error("Interrupted while waiting to get connection: " + ie.getMessage());
                        throw new Exception("Interrupted while waiting to get connection: " + ie.getMessage());
                    } // end try/catch
                } // end if
            } // end while
        }catch(Exception e){
            myLogger.error("Error accessing jdbc connection through datasource:" + ds);
            throw new DAOException("Error accessing jdbc connection through datasource:" + ds + " " + e.getMessage());
        } // end catch

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting getConnection() method. Returning connection: " + String.valueOf(conn));
        }//end if
        return conn;
    } // end getConnection

    /**
     * This method is used to destroy or close the database connection, prepared statement, and result set objects in one call.
     *
     * @param conn
     *        Connection
     * @param pstmt
     *        PreparedStatement
     * @param rs
     *        ResultSet
     */
    public void destroyObjects(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering destroyObjects() method is used to destroy or close the database connection, prepared statement, and result set objects in one call. Incoming parameters are: conn=" + String.valueOf(conn) + ", pstmt=" + String.valueOf(pstmt) + ", rs=" + String.valueOf(rs));
        }//end if

        try{
            if(rs != null){
                if(rs.getWarnings() != null){
                    myLogger.warn(logWarningsFromResultSet(rs));
                } // end if
                rs.close();
            } // end if
        }catch(SQLException e){
            myLogger.error("Exception - Destroy ResultSet - " + e.getMessage() + logSQLException(e));
        } // end try/catch
        try{
            if(pstmt != null){
                if(pstmt.getWarnings() != null){
                    myLogger.warn(logWarningsFromStatement(pstmt));
                } // end if
                pstmt.close();
            } // end if
        }catch(SQLException e){
            myLogger.error("Exception - Destroy Statement - " + e.getMessage() + logSQLException(e));
        } // end try/catch
        try{
            if(conn != null){
                if(!conn.getAutoCommit()){
                    conn.setAutoCommit(true);
                }// end if
                conn.close();
            } // end if
        }catch(SQLException e){
            myLogger.error("Exception - Destroy Connection - " + e.getMessage() + logSQLException(e));
        } // end try/catch
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting destroyObjects() method.");
        }//end if
    } // end destroyObjects

    /**
     * This method is used to print warnings from the result set.
     *
     * @param rs
     *        ResultSet
     * @throws SQLException
     *         An exception if the method fails.
     */
    public void printWarningsFromResultSet(ResultSet rs) throws SQLException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering printWarningsFromResultSet() method which is used to print warnings from the result set. Incoming parameter is: " + String.valueOf(rs));
        }//end if

        printWarnings(rs.getWarnings());

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting printWarningsFromResultSet method.");
        }//end if
    }// end printWarningsFromResultSet

    /**
     * This method is used to print an SQL warning.
     *
     * @param warning
     *        SQLWarning
     * @throws SQLException
     *         An exception if the method fails.
     */
    public void printWarnings(SQLWarning warning) throws SQLException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering printWarnings() method which is used to print an SQL warning. Incoming parameter is: " + String.valueOf(warning));
        }//end if

        if(warning != null){
            myLogger.warn(LINESEPERATOR + "---Warning---" + LINESEPERATOR);
            while(warning != null){
                myLogger.warn("Message: " + warning.getMessage());
                myLogger.warn("SQLState: " + warning.getSQLState());
                myLogger.warn("Vendor error code: ");
                myLogger.warn(warning.getErrorCode());
                myLogger.warn("");
                warning = warning.getNextWarning();
            } // end while
        } // end if

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting printWarnings.");
        }//end if
    } // end printWarnings

    /**
     * This method is used to log the warnings from a result set.
     *
     * @param rs
     *        ResultSet
     * @return String String
     * @throws SQLException
     *         An exception if the method fails.
     */
    public String logWarningsFromResultSet(ResultSet rs) throws SQLException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering logWarningsFromResultSet() method which is used to log the warnings from a result set. Incoming parameter is: " + String.valueOf(rs));
        }//end if

        String s = logableWarnings(rs.getWarnings());

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting logWarningsFromResultSet() method.  Return value is: " + String.valueOf(s));
        }//end if
        return s;
    }// end logWarningsFromResultSet

    /**
     * This method is used to log the warnings from a statement.
     *
     * @param stmt
     *        Statement
     * @return String String
     * @throws SQLException
     *         An exception if the method fails.
     */
    public String logWarningsFromStatement(Statement stmt) throws SQLException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering logWarningsFromStatement() method which is used to log the warnings from a statement. Incoming parameter is: " + String.valueOf(stmt));
        }//end if

        String s = logableWarnings(stmt.getWarnings());

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting logWarningsFromStatement() method.  Return value is: " + String.valueOf(s));
        }//end if
        return s;
    }// end logWarningsFromStatement

    /**
     * This method is used to construct an SQL warning in a loggable format.
     *
     * @param warning
     *        SQLWarning
     * @return String String
     * @throws SQLException
     *         An exception if the method fails.
     */
    public String logableWarnings(SQLWarning warning) throws SQLException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering logableWarnings() method which is used to construct an SQL warning in a loggable format. Incoming parameter is: " + String.valueOf(warning));
        }//end if

        StringBuffer sb = new StringBuffer();
        if(warning != null){
            sb.append(LINESEPERATOR + "---Warning---" + LINESEPERATOR);
            while(warning != null){
                sb.append("Message: " + warning.getMessage() + LINESEPERATOR);
                sb.append("SQLState: " + warning.getSQLState() + LINESEPERATOR);
                sb.append("Vendor error code: " + LINESEPERATOR + warning.getErrorCode() + LINESEPERATOR);
                warning = warning.getNextWarning();
            } // end while
        } // end if

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting logableWarnings().  Return value is: sb=" + String.valueOf(sb));
        }//end if
        return sb.toString();
    } // end logableWarnings

    /**
     * This method is used to log an SQL exception.
     *
     * @param ex
     *        SQLException
     * @return String
     */
    public String logSQLException(SQLException ex) {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering logSQLException() method which is used to log an SQL exception. Incoming parameter is: " + String.valueOf(ex));
        }//end if

        SQLException nextException = ex;
        StringBuffer sb = new StringBuffer();
        sb.append(LINESEPERATOR + "---SQLExceptions---" + LINESEPERATOR);
        while(nextException != null){
            if(nextException instanceof SQLException){
                sb.append(nextException.toString() + LINESEPERATOR);
                sb.append("SQLState: " + ((SQLException) nextException).getSQLState() + LINESEPERATOR);
                sb.append("Error Code: " + ((SQLException) nextException).getErrorCode() + LINESEPERATOR);
                sb.append("Message: " + nextException.getMessage() + LINESEPERATOR);
                Throwable t = ex.getCause();
                while(t != null){
                    sb.append("Cause: " + t + LINESEPERATOR);
                    t = t.getCause();
                } // end while
                nextException = nextException.getNextException();
            } // end if
        } // end while

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting logSQLException() method.  Return value is:  " + String.valueOf(sb));
        }//end if
        return sb.toString();
    } // end logSQLException

    /**
     * This method is used to print an SQL exception.
     *
     * @param ex
     *        SQLException
     */
    public void printSQLException(SQLException ex) {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering printSQLException() method which is used to print an SQL exception. Incoming parameter is: " + String.valueOf(ex));
        }//end if

        SQLException nextException = ex;
        while(nextException != null){
            if(nextException instanceof SQLException){
                nextException.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) nextException).getSQLState());
                System.err.println("Error Code: " + ((SQLException) nextException).getErrorCode());
                System.err.println("Message: " + nextException.getMessage());
                Throwable t = ex.getCause();
                while(t != null){
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                } // end while
                nextException = nextException.getNextException();
            } // end if
        } // end while
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting printSQLException() method.");
        }//end if
    } // end printSQLException

    /**
     * Gets a list of records from a table.
     * 
     * @return the list of dataObject object retrieved from a table
     * @throws DAOException
     */
    public abstract List<T> getDataList() throws DAOException;
    
    /**
     * Gets a record from a table using the give refId. 
     * @param refId the reference id of the record in a table
     * @return the data object
     * @throws DAOException
     */
    public abstract T getById(IdT refId) throws DAOException;
    
    /**
     * Saves a record in a table using the given dataObject.
     * 
     * @param dataObject the dataObject with save data
     * @throws DAOException
     */
    public abstract void save(T dataObject) throws DAOException;

    /**
     * Updates a record in a table using the given dataObject.
     * 
     * @param dataObject the dataObject with update data
     * @throws DAOException occurs when attempting to update a record
     */
    public abstract void update(T dataObject) throws DAOException;

    /**
     * Soft deletes a record in a table using the given dataObject by settings the records delete_indicator to 'Y'.
     * 
     * @param dataObject the dataObject with delete data
     * @throws DAOException occurs when attempting a soft delete of a record
     */
    public abstract void softDelete(T dataObject) throws DAOException;

    /**
     * Deletes a record in a table using the given dataObject.
     * 
     * @param dataObject the dataObject with delete data
     * @throws DAOException occurs when attempting a delete of a record
     */
    public abstract void delete(T dataObject) throws DAOException;

} // end class
