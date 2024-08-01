package gov.doc.isu.ws.core;

import java.sql.Timestamp;

/**
 * CommonDO instance used for common fields used within DO's.
 *  
 * @author Richard Salas
 */
public class CommonDO {

    private Long createUserRefId;
    private Timestamp createTs;
    private Long updateUserRefId;
    private Timestamp updateTs;
    private String deleteInd;

    public CommonDO() {
    }

    /**
     * @return the createUserRefId
     */
    public Long getCreateUserRefId() {
        return createUserRefId;
    }

    /**
     * @param createUserRefId the createUserRefId to set
     */
    public void setCreateUserRefId(Long createUserRefId) {
        this.createUserRefId = createUserRefId;
    }

    /**
     * @return the createTs
     */
    public Timestamp getCreateTs() {
        return createTs;
    }

    /**
     * @param createTs the createTs to set
     */
    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    /**
     * @return the updateUserRefId
     */
    public Long getUpdateUserRefId() {
        return updateUserRefId;
    }

    /**
     * @param updateUserRefId the updateUserRefId to set
     */
    public void setUpdateUserRefId(Long updateUserRefId) {
        this.updateUserRefId = updateUserRefId;
    }

    /**
     * @return the updateTs
     */
    public Timestamp getUpdateTs() {
        return updateTs;
    }

    /**
     * @param updateTs the updateTs to set
     */
    public void setUpdateTs(Timestamp updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * @return the deleteInd
     */
    public String getDeleteInd() {
        return deleteInd;
    }

    /**
     * @param deleteInd the deleteInd to set
     */
    public void setDeleteInd(String deleteInd) {
        this.deleteInd = deleteInd;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CommonDO [createUserRefId=");
        builder.append(createUserRefId);
        builder.append(", createTs=");
        builder.append(createTs);
        builder.append(", updateUserRefId=");
        builder.append(updateUserRefId);
        builder.append(", updateTs=");
        builder.append(updateTs);
        builder.append(", deleteInd=");
        builder.append(deleteInd);
        builder.append("]");
        return builder.toString();
    }

}//end class
