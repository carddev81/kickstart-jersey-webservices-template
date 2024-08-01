/**
 * 
 */
package gov.doc.isu.ws.core;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * CommonDTO instance used for common fields used within DTO's.
 *  
 * @author Richard Salas
 */
public class CommonDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1410197261618549115L;
    private Long createUserRefId;
    private Timestamp createTs;
    private Long updateUserRefId;
    private Timestamp updateTs;

    /**
     * 
     */
    public CommonDTO() {
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CommonDTO [createUserRefId=");
        builder.append(createUserRefId);
        builder.append(", createTs=");
        builder.append(createTs);
        builder.append(", updateUserRefId=");
        builder.append(updateUserRefId);
        builder.append(", updateTs=");
        builder.append(updateTs);
        builder.append("]");
        return builder.toString();
    }

}//end class
