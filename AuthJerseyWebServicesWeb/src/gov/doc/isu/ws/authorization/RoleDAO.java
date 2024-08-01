package gov.doc.isu.ws.authorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.junderground.jdbc.DebugLevel;
import com.junderground.jdbc.StatementFactory;

import gov.doc.isu.ws.core.DAO;
import gov.doc.isu.ws.core.DAOException;

/**
 * Data Access Object used to execute CRUD operations against the roles table.
 *  
 * @author Richard Salas
 * @version 1.0.0
 */
public class RoleDAO extends DAO<RoleDO, Long> {

    //FIXME Incomplete class needs more implementation.
    private static final long serialVersionUID = 1L;
    private static Logger myLogger = Logger.getLogger("gov.doc.isu.ws.authorization.RoleDAO");

    public RoleDAO() {
    }//end constructor

    /**
     * Gets the role names using the given userRefId.
     * @param userRefId the user reference id used for retrieving the role names.
     * @return roleNameList the list of role names
     * @throws DAOException occurs when attempting to retrieve role names from the roles table
     */
    public List<String> getRoleNamesByUserRefId(Long userRefId) throws DAOException{
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getRoleNamesByUserRefId() method which is used to select role names record by user_ref_id.  Incoming parameter is: userRefId=" + String.valueOf(userRefId));
        }//end if

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DebugLevel debug = myLogger.isDebugEnabled() ? DebugLevel.ON : DebugLevel.OFF; // <-- setting the debug level for the DebugableStatement
        List<String> roleNameList = new ArrayList<>();

        try{
            String sql = "SELECT role_nm FROM roles r INNER JOIN user_roles ur on ur.role_ref_id = r.role_ref_id INNER JOIN users u on u.user_ref_id = ur.user_ref_id WHERE u.user_ref_id = ?";
            conn = getConnection();
            ps = StatementFactory.getStatement(conn, sql, debug); // <-- insert this to debug
            ps.setLong(1, userRefId);

            if(myLogger.isDebugEnabled()){
                myLogger.debug(" debuggable statement= " + String.valueOf(ps));
            }// end if

            rs = ps.executeQuery();
            while(rs.next()){
                roleNameList.add(rs.getString("role_nm"));
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
            myLogger.error("SQLException occurred while in getRoleNamesByUserRefId. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while trying to retrieve the role_nm by user_ref_id.");
        }catch(Exception e){
            myLogger.error("Exception occurred while in getRoleNamesByUserRefId. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while trying to retrieve the role_nm by user_ref_id.");
            // add more logging here....
        }finally{
            destroyObjects(conn, ps, rs);
        } // end try/catch/finally

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting getRoleNamesByUserRefId() method. Return value is: roleNameList=" + String.valueOf(roleNameList));
        }// end if

        return roleNameList;
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleDO> getDataList() throws DAOException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleDO getById(Long refId) throws DAOException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RoleDO dataObject) throws DAOException {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(RoleDO dataObject) throws DAOException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDelete(RoleDO dataObject) throws DAOException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(RoleDO dataObject) throws DAOException {
    }

}//end class
