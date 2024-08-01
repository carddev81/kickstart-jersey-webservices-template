package gov.doc.isu.ws.authorization;

import java.sql.Date;

import gov.doc.isu.ws.core.IDO;

/**
 * Data transfer object used for holding {@link TokenDO} business state.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class TokenDTO implements IDO<TokenDO>{

    private String token;
    private String userID;
    private String alias;
    private Long userRefId;
    private Date expirationDt;

    public TokenDTO() {
    }//end constructor

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }//end method

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }//end method

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }//end method

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }//end method

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }//end method

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }//end method

    /**
     * @return the userRefId
     */
    public Long getUserRefId() {
        return userRefId;
    }//end method

    /**
     * @param userRefId the userRefId to set
     */
    public void setUserRefId(Long userRefId) {
        this.userRefId = userRefId;
    }//end method

    /**
     * @return the expirationDt
     */
    public Date getExpirationDt() {
        return expirationDt;
    }//end method

    /**
     * @param expirationDt the expirationDt to set
     */
    public void setExpirationDt(Date expirationDt) {
        this.expirationDt = expirationDt;
    }//end method

    @Override
    public TokenDO toDO() {
        TokenDO tokenDO = new TokenDO();
        tokenDO.setAlias(this.alias);
        tokenDO.setToken(this.token);
        tokenDO.setUserID(this.userID);
        tokenDO.setUserRefId(this.userRefId);
        return tokenDO;
    }//end method

}
