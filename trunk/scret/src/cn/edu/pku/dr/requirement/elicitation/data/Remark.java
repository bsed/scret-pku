package cn.edu.pku.dr.requirement.elicitation.data;

public class Remark implements java.io.Serializable {
    public static final String TABLE_NAME = "remark";

    public static final String VIEW_NAME = "v_remark";

    public static final String PRIMARY_KEY = "remarkId";

    private Long remarkId;

    private Long roleId;

    private Long descriptionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String remarkContent;

    private String roleName;

    private String userName;

    public static Boolean isUpdatable(String property) {
        if ("roleName".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getRemarkId() {
        return this.remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDescriptionId() {
        return this.descriptionId;
    }

    public void setDescriptionId(Long descriptionId) {
        this.descriptionId = descriptionId;
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

    public String getRemarkContent() {
        return this.remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (!(o instanceof Remark))
            return false;
        Remark bean = (Remark) o;
        if (remarkId.equals(bean.getRemarkId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return remarkId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("remarkId=" + remarkId + ",");
        buffer.append("roleId=" + roleId + ",");
        buffer.append("descriptionId=" + descriptionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("remarkContent=" + remarkContent + ",");
        buffer.append("roleName=" + roleName + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }
}
