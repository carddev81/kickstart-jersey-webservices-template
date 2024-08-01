package gov.doc.isu.ws.authorization;

import gov.doc.isu.ws.core.IDO;

/**
 * Data transfer object used for holding {@link RoleDO} business type state.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class RoleDTO implements IDO<RoleDO>{

    private Long roleRefId;
    private String roleName;
    private String roleDesc;

    public RoleDTO() {
        // TODO Auto-generated constructor stub
    }//end constructor

    @Override
    public RoleDO toDO() {
        return null;
    }//end method

    /**
     * @return the roleRefId
     */
    public Long getRoleRefId() {
        return roleRefId;
    }//end method

    /**
     * @param roleRefId the roleRefId to set
     */
    public void setRoleRefId(Long roleRefId) {
        this.roleRefId = roleRefId;
    }//end method

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }//end method

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }//end method

    /**
     * @return the roleDesc
     */
    public String getRoleDesc() {
        return roleDesc;
    }//end method

    /**
     * @param roleDesc the roleDesc to set
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }//end method

}//end class
