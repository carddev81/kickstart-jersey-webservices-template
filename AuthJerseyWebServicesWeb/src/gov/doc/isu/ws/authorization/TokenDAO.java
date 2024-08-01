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
 * Data Access Object used to execute CRUD operations against the access_tokens table.
 *  
 * @author Richard Salas
 * @version 1.0.0
 */
public class TokenDAO extends DAO<TokenDO, String>{

    //FIXME Incomplete class needs more implementation.
    private static final long serialVersionUID = 1L;
    private static Logger myLogger = Logger.getLogger("gov.doc.isu.ws.authorization.TokenDAO");

    public TokenDAO() {
    }//end constructor

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenDO getById(String token) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering getById() method which is used to select a access_token record by token.  Incoming parameter is: " + String.valueOf(token));
        }//end if

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DebugLevel debug = myLogger.isDebugEnabled() ? DebugLevel.ON : DebugLevel.OFF; // <-- setting the debug level for the DebugableStatement
        TokenDO tokenDO = null;

        try{
            String sql = "SELECT token, user_id, alias, expiration_dt, user_ref_id FROM access_token WHERE token = ?";
            conn = getConnection();
            ps = StatementFactory.getStatement(conn, sql, debug); // <-- insert this to debug
            ps.setString(1, token);

            if(myLogger.isDebugEnabled()){
                myLogger.debug(" debuggable statement= " + String.valueOf(ps));
            }// end if

            rs = ps.executeQuery();
            if(rs.next()){
                tokenDO = new TokenDO();
                tokenDO.setToken(rs.getString("token"));
                tokenDO.setUserRefId(rs.getLong("user_ref_id"));
                tokenDO.setExpirationDt(rs.getDate("expiration_dt"));
                tokenDO.setUserID(rs.getString("user_id"));
                tokenDO.setAlias(rs.getString("alias"));
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
            myLogger.error("SQLException occurred while in getById. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while trying to retrieve the access_token by token.");
        }catch(Exception e){
            myLogger.error("Exception occurred while in getById. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while trying to retrieve the access_token by token.");
            // add more logging here....
        }finally{
            destroyObjects(conn, ps, rs);
        } // end try/catch/finally

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting getById() method. Return value is: " + String.valueOf(tokenDO));
        }// end if
        return tokenDO;
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(TokenDO tokenDO) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering save() method which is used to save a new record into the DB. Incoming parameters is tokenDO=" + String.valueOf(tokenDO));
        }// end if
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DebugLevel debug = myLogger.isDebugEnabled() ? DebugLevel.ON : DebugLevel.OFF; // <-- setting the debug level for the DebugableStatement

        try{
            String sql = "INSERT INTO access_token(token, user_id, alias, expiration_dt, user_ref_id) VALUES (?,?,?,?,?)";
            conn = getConnection();
            ps = StatementFactory.getStatement(conn, sql, debug); // <-- insert this to debug
            // set values
            int i = 1;
            ps.setString(i++, tokenDO.getToken());
            ps.setString(i++, tokenDO.getUserID());
            ps.setString(i++, tokenDO.getAlias());
            ps.setDate(i++, tokenDO.getExpirationDt());// change to date
            ps.setLong(i++, tokenDO.getUserRefId());// change to date

            if(myLogger.isDebugEnabled()){
                myLogger.debug(" debuggable statement= " + String.valueOf(ps));
            }// end if

            int update = ps.executeUpdate();
            myLogger.info("save Count is " + update);
        }catch(SQLException e){
            try{
                if(ps != null){
                    myLogger.error(logWarningsFromStatement(ps));
                } // end if
            }catch(SQLException sqle){
                myLogger.error("An exception occurred while trying to log warnings from statement. Message is: " + sqle.getMessage(), e);
            } // end try/catch
            myLogger.error(logSQLException(e));
            myLogger.error("SQLException occurred while in save. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while attempting to save token.");
        }catch(Exception e){
            myLogger.error("Exception occurred while in save. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured occured while attempting to save token.");
        }finally{
            destroyObjects(conn, ps, rs);
        } // end try/catch/finally

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting save() method.");
        }// end if
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(TokenDO tokenDO) throws DAOException {
        if(myLogger.isDebugEnabled()){
            myLogger.debug("Entering delete() method which is used to delete an access_token record from the DB. Incoming parameters is tokenDO=" + String.valueOf(tokenDO));
        }// end if
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DebugLevel debug = myLogger.isDebugEnabled() ? DebugLevel.ON : DebugLevel.OFF; // <-- setting the debug level for the DebugableStatement

        try{
            String sql = "DELETE FROM access_token WHERE token = ?";
            conn = getConnection();
            ps = StatementFactory.getStatement(conn, sql, debug); // <-- insert this to debug
            // set values
            ps.setString(1, tokenDO.getToken());

            if(myLogger.isDebugEnabled()){
                myLogger.debug(" debuggable statement= " + String.valueOf(ps));
            }// end if

            int update = ps.executeUpdate();
            myLogger.info("delete count is " + update);
        }catch(SQLException e){
            try{
                if(ps != null){
                    myLogger.error(logWarningsFromStatement(ps));
                } // end if
            }catch(SQLException sqle){
                myLogger.error("An exception occurred while trying to log warnings from statement. Message is: " + sqle.getMessage(), e);
            } // end try/catch
            myLogger.error(logSQLException(e));
            myLogger.error("SQLException occurred while in delete. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured while attempting to delete token.");
        }catch(Exception e){
            myLogger.error("Exception occurred while in delete. SQL is: " + String.valueOf(ps) + ". Message is: " + e.getMessage(), e);
            throw new DAOException("SQL Error occured occured while attempting to delete token.");
        }finally{
            destroyObjects(conn, ps, rs);
        } // end try/catch/finally

        if(myLogger.isDebugEnabled()){
            myLogger.debug("Exiting delete() method.");
        }// end if
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(TokenDO dataObject) {
        
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDelete(TokenDO dataObject) {
        
    }//end method

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TokenDO> getDataList() {
        // TODO Auto-generated method stub
        return null;
    }//end method

}//end class
