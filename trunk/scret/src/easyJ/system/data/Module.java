package easyJ.system.data;

public class Module implements java.io.Serializable {
    public static final String TABLE_NAME = "module";

    public static final String VIEW_NAME = "v_module";

    public static final String PRIMARY_KEY = "moduleId";

    public static final String TREE_ID_PROPERTY = "moduleId";

    public static final String TREE_PARENT_ID_PROPERTY = "parentId";

    public static final String TREE_DISPLAY_PROPERTY = "moduleName";

    public static final String TREE_FUNCTION_PROPERTY = "moduleAddress";

    public static final String CONTEXT_ROLE_PROPERTY = "moduleRoleTypeRelatedValue";

    private Long moduleId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private Long parentId;

    private String moduleName;

    private String moduleAddress;

    private String imageAddress;

    private Long newWindow;

    private String rightGroup;

    private String users;

    private String userGroups;

    private String moduleType;

    private Long moduleRoleTypeId;

    private String parentName;

    private String newWindowValue;

    private Long newWindowRelatedValue;

    private String moduleRoleTypeValue;

    private Long moduleRoleTypeRelatedValue;

    public static Boolean isUpdatable(String property) {
        if ("parentName".equals(property))
            return new Boolean(false);
        if ("newWindowValue".equals(property))
            return new Boolean(false);
        if ("newWindowRelatedValue".equals(property))
            return new Boolean(false);
        if ("moduleRoleTypeValue".equals(property))
            return new Boolean(false);
        if ("moduleRoleTypeRelatedValue".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public java.sql.Date getBuildTime() {
        return this.buildTime;
    }

    public void setBuildTime(java.sql.Date buildTime) {
        this.buildTime = buildTime;
    }

    public java.sql.Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(java.sql.Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleAddress() {
        return this.moduleAddress;
    }

    public void setModuleAddress(String moduleAddress) {
        this.moduleAddress = moduleAddress;
    }

    public String getImageAddress() {
        return this.imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public Long getNewWindow() {
        return this.newWindow;
    }

    public void setNewWindow(Long newWindow) {
        this.newWindow = newWindow;
    }

    public String getRightGroup() {
        return this.rightGroup;
    }

    public void setRightGroup(String rightGroup) {
        this.rightGroup = rightGroup;
    }

    public String getUsers() {
        return this.users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(String userGroups) {
        this.userGroups = userGroups;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public Long getModuleRoleTypeId() {
        return this.moduleRoleTypeId;
    }

    public void setModuleRoleTypeId(Long moduleRoleTypeId) {
        this.moduleRoleTypeId = moduleRoleTypeId;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getNewWindowValue() {
        return this.newWindowValue;
    }

    public void setNewWindowValue(String newWindowValue) {
        this.newWindowValue = newWindowValue;
    }

    public Long getNewWindowRelatedValue() {
        return this.newWindowRelatedValue;
    }

    public void setNewWindowRelatedValue(Long newWindowRelatedValue) {
        this.newWindowRelatedValue = newWindowRelatedValue;
    }

    public String getModuleRoleTypeValue() {
        return this.moduleRoleTypeValue;
    }

    public void setModuleRoleTypeValue(String moduleRoleTypeValue) {
        this.moduleRoleTypeValue = moduleRoleTypeValue;
    }

    public Long getModuleRoleTypeRelatedValue() {
        return this.moduleRoleTypeRelatedValue;
    }

    public void setModuleRoleTypeRelatedValue(Long moduleRoleTypeRelatedValue) {
        this.moduleRoleTypeRelatedValue = moduleRoleTypeRelatedValue;
    }

    public Object clone() {
        Object object = null;
        try {
            object = easyJ.common.BeanUtil.cloneObject(this);
        } catch (easyJ.common.EasyJException ee) {
            return null;
        }
        return object;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Module))
            return false;
        Module bean = (Module) o;
        if (moduleId.equals(bean.getModuleId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return moduleId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("moduleId=" + moduleId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("parentId=" + parentId + ",");
        buffer.append("moduleName=" + moduleName + ",");
        buffer.append("moduleAddress=" + moduleAddress + ",");
        buffer.append("imageAddress=" + imageAddress + ",");
        buffer.append("newWindow=" + newWindow + ",");
        buffer.append("rightGroup=" + rightGroup + ",");
        buffer.append("users=" + users + ",");
        buffer.append("userGroups=" + userGroups + ",");
        buffer.append("moduleType=" + moduleType + ",");
        buffer.append("moduleRoleTypeId=" + moduleRoleTypeId + ",");
        buffer.append("parentName=" + parentName + ",");
        buffer.append("newWindowValue=" + newWindowValue + ",");
        buffer.append("newWindowRelatedValue=" + newWindowRelatedValue + ",");
        buffer.append("moduleRoleTypeValue=" + moduleRoleTypeValue + ",");
        buffer.append("moduleRoleTypeRelatedValue="
                + moduleRoleTypeRelatedValue);
        buffer.append("]");
        return buffer.toString();
    }
}
