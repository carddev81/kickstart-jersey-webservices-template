package gov.doc.isu.ws.authorization;

import java.io.Serializable;
import java.sql.Date;

import gov.doc.isu.ws.core.IDTO;

/**
 * Persistent class for the access_tokens database table.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class TokenDO implements Serializable, IDTO<TokenDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String token;
    private String userID;
    private String alias;
    private Long userRefId;
    private Date expirationDt;
    
    public TokenDO() {
        
    }//end constructor

    @Override
    public TokenDTO toDTO() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAlias(this.alias);
        tokenDTO.setExpirationDt(this.expirationDt);
        tokenDTO.setToken(this.token);
        tokenDTO.setUserID(userID);
        tokenDTO.setUserRefId(userRefId);
        return tokenDTO;
    }//end method

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

    /** 
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TokenDO [token=");
        builder.append(token);
        builder.append(", userID=");
        builder.append(userID);
        builder.append(", alias=");
        builder.append(alias);
        builder.append(", userRefId=");
        builder.append(userRefId);
        builder.append(", expirationDt=");
        builder.append(expirationDt);
        builder.append("]");
        return builder.toString();
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

}
