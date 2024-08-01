package gov.doc.isu.ws.authorization;

import gov.doc.isu.ws.core.CommonDO;
import gov.doc.isu.ws.core.IDTO;

/**
 * Persistent class for the users database table.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class UserDO extends CommonDO implements IDTO<UserDTO>{

    private Long userRefId;
    private String firstNm;
    private String lastNm;
    private String userID;
    private String alias;

    public UserDO() {
        
    }

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

    @Override
    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAlias(this.alias);
        userDTO.setUserID(this.userID);
        userDTO.setFirstNm(this.firstNm);
        userDTO.setUserRefId(this.userRefId);
        userDTO.setCreateTs(this.getCreateTs());
        userDTO.setCreateUserRefId(this.getCreateUserRefId());
        userDTO.setUpdateTs(this.getUpdateTs());
        userDTO.setUpdateUserRefId(this.getUpdateUserRefId());
        return userDTO;
    }//end method

}//end class
