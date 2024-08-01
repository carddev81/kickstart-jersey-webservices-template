package gov.doc.isu.ws.authorization;
import gov.doc.isu.ws.core.CommonDTO;
import gov.doc.isu.ws.core.IDO;

/**
 * Data transfer object used for holding {@link TokenDO} business type state.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class UserDTO extends CommonDTO implements IDO<UserDO>{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long userRefId;
    private String firstNm;
    private String lastNm;
    private String userID;
    private String alias;
    private String password;
    
    
    public UserDTO() {
        // TODO Auto-generated constructor stub
    }//end constructor

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
     * @return the firstNm
     */
    public String getFirstNm() {
        return firstNm;
    }//end method

    /**
     * @param firstNm the firstNm to set
     */
    public void setFirstNm(String firstNm) {
        this.firstNm = firstNm;
    }//end method

    /**
     * @return the lastNm
     */
    public String getLastNm() {
        return lastNm;
    }//end method

    /**
     * @param lastNm the lastNm to set
     */
    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }//end method

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }//end method//end method

    @Override
    public UserDO toDO() {
        return null;
    }//end method//end method

    /** 
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserDTO [userRefId=");
        builder.append(userRefId);
        builder.append(", firstNm=");
        builder.append(firstNm);
        builder.append(", lastNm=");
        builder.append(lastNm);
        builder.append(", userID=");
        builder.append(userID);
        builder.append(", alias=");
        builder.append(alias);
        builder.append(", password=");
        builder.append(password);
        builder.append("]");
        return builder.toString();
    }//end method

}//end class
