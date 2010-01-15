package elicitation.model.user;

import java.sql.Date;

public class SysUser implements java.io.Serializable {
    public static final String TABLE_NAME = "sys_user";

    public static final String VIEW_NAME = "sys_user";

    public static final String PRIMARY_KEY = "userId";

    private Long userId;

    private Long stakeholderTypeId;

    private Long cstId;

    private String useState;

    private Date buildTime;

    private Date updateTime;

    private String userName;

    private String email;

    private String phone;

    private String mobile;

    private String userGroupIds;

    private String userGroupNames;

    private String password;

    private String roleIds;

    private String roleNames;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getStakeholderTypeId() {
        return this.stakeholderTypeId;
    }

    public void setStakeholderTypeId(Long stakeholderTypeId) {
        this.stakeholderTypeId = stakeholderTypeId;
    }

    public Long getCstId() {
        return this.cstId;
    }

    public void setCstId(Long cstId) {
        this.cstId = cstId;
    }

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public Date getBuildTime() {
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

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserGroupIds() {
        return this.userGroupIds;
    }

    public void setUserGroupIds(String userGroupIds) {
        this.userGroupIds = userGroupIds;
    }

    public String getUserGroupNames() {
        return this.userGroupNames;
    }

    public void setUserGroupNames(String userGroupNames) {
        this.userGroupNames = userGroupNames;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return this.roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Object clone() {
        Object object = null;
//        try {
//            object = easyJ.common.BeanUtil.cloneObject(this);
//        } catch (easyJ.common.EasyJException ee) {
//            return null;
//        }
        return object;
    }

    public boolean equals(Object o) {
        if (!(o instanceof SysUser))
            return false;
        SysUser bean = (SysUser) o;
        if (userId.equals(bean.getUserId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return userId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("userId=" + userId + ",");
        buffer.append("stakeholderTypeId=" + stakeholderTypeId + ",");
        buffer.append("cstId=" + cstId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("userName=" + userName + ",");
        buffer.append("email=" + email + ",");
        buffer.append("phone=" + phone + ",");
        buffer.append("mobile=" + mobile + ",");
        buffer.append("userGroupIds=" + userGroupIds + ",");
        buffer.append("userGroupNames=" + userGroupNames + ",");
        buffer.append("password=" + password + ",");
        buffer.append("roleIds=" + roleIds + ",");
        buffer.append("roleNames=" + roleNames);
        buffer.append("]");
        return buffer.toString();
    }
}
