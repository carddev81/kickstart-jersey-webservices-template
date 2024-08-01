package gov.doc.isu.ws.authorization;

import gov.doc.isu.ws.core.CommonDO;
import gov.doc.isu.ws.core.IDTO;
import gov.doc.isu.ws.server.core.Role;

/**
 * Persistent class for the roles database table.
 * 
 * @author Richard Salas
 * @version 1.0.0
 */
public class RoleDO extends CommonDO implements IDTO<RoleDTO>{

    private Long roleRefId;
    private String roleName;
    private String roleDesc;

    public RoleDO() {
    }//end method

    @Override
    public RoleDTO toDTO() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleDesc(this.roleDesc);
        roleDTO.setRoleName(this.roleName);
        roleDTO.setRoleRefId(this.roleRefId);
        return roleDTO;
    }//end method

    public Role toEnum(){
        return Role.valueOf(this.roleName);
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

}
