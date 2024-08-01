/**
 * 
 */
package gov.doc.isu.ws.authorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.junderground.jdbc.DebugLevel;
import com.junderground.jdbc.StatementFactory;

import gov.doc.isu.ws.core.DAO;
import gov.doc.isu.ws.core.DAOException;

/**
 * Data Access Object used to execute CRUD operations against the users table.
 *  
 * @author Richard Salas
 * @version 1.0.0
 */
public class UserDAO extends DAO<UserDO, Long> {

    //FIXME Incomplete class needs more implementation.
    private static final long serialVersionUID = 1L;
    private static Logger myLogger = Logger.getLogger("gov.doc.isu.ws.authorization.UserDAO");

    /**
     * Get a user using the given user id.
     * 
     * @param userID the user id used in criteria 
     * @return userDO the user found 
     * @throws DAOException if an error occurs
     */
    public UserDO getUserByUserID(String userID) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getUserByUserID() method which is used to select a User record by user_id.  Incoming parameter is: " + String.valueOf(userID));
        }//end if

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DebugLevel debug = myLogger.isDebugEnabled() ? DebugLevel.ON : DebugLevel.OFF; // <-- setting the debug level for the DebugableStatement
        UserDO userDO = null;

        try{
            String sql = "SELECT user_ref_id, first_name, last_name, user_id, alias, create_user_ref_id, create_ts, update_user_ref_id, update_ts, delete_ind FROM users WHERE user_id = ? and delete_ind = 'N'";
            conn = getConnection();
            ps = StatementFactory.getStatement(conn, sql, debug); // <-- insert this to debug
            ps.setString(1, userID);

            if(myLogger.isDebugEnabled()){
                myLogger.debug(" debuggable statement= " + String.valueOf(ps));
            }// end if

            rs = ps.executeQuery();
            if(rs.next()){
                userDO = new UserDO();
                userDO.setUserRefId(rs.getLong("user_ref_id"));
                userDO.setFirstNm(rs.getString("first_name"));
                userDO.setLastNm(rs.getString("last_name"));
                userDO.setUserID(rs.getString("user_id"));
                userDO.setAlias(rs.getString("alias"));
            } // end while
        }catch(SQLException e){
            try{
                if(ps != null){
                    myLogger.error(logWarningsFromStatement(ps));
                } // end if
            }catch(SQLException sqle){
                myLogger.error("An exception occurred while trying to log warnings from statement. Message is: " + sqle.getMessage(), e);
            } // end try/catch
            myLogger.error(logSQLException(e));
            myLogger.error("SQLException occurred while in getUserByUserID. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while trying to retrieve the user by user id.");
        }catch(Exception e){
            myLogger.error("An exception occurred while in getUserByUserID. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while trying to retrieve the user by user id.");
            // add more logging here....
        }finally{
            destroyObjects(conn, ps, rs);
        } // end try/catch/finally

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting getByUserId() method. Return value is: " + String.valueOf(userDO));
        }// end if
        return userDO;
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDO> getDataList() {
        // TODO Auto-generated method stub
        return null;
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDO getById(Long refId) {
        // TODO Auto-generated method stub
        return null;
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(UserDO dataObject) {
        // TODO Auto-generated method stub
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(UserDO dataObject) {
        // TODO Auto-generated method stub
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDelete(UserDO dataObject) {
        // TODO Auto-generated method stub
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UserDO dataObject) {
        // TODO Auto-generated method stub
    }//end method

}// end class
