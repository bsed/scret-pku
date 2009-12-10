package cn.edu.pku.dr.requirement.elicitation.data;

public class UserProjectRelation implements java.io.Serializable {
    public static final String TABLE_NAME = "user_project_relation";

    public static final String VIEW_NAME = "v_user_project_relation";

    public static final String PRIMARY_KEY = "userProjectId";

    private Long userProjectId;

    private Long projectId;

    private Long userId;

    private Long userProjectState;

    private String applyReason;

    private String rejectReason;

    private String deleteReason;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private String useState;

    private Long creatorId;

    private String userName;

    private String email;

    private String phone;

    private String mobile;

    private String projectName;

    private String userProjectStateValue;

    private Long userProjectStateRelatedValue;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        if ("email".equals(property))
            return new Boolean(false);
        if ("phone".equals(property))
            return new Boolean(false);
        if ("mobile".equals(property))
            return new Boolean(false);
        if ("projectName".equals(property))
            return new Boolean(false);
        if ("userProjectStateValue".equals(property))
            return new Boolean(false);
        if ("userProjectStateRelatedValue".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getUserProjectId() {
        return this.userProjectId;
    }

    public void setUserProjectId(Long userProjectId) {
        this.userProjectId = userProjectId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserProjectState() {
        return this.userProjectState;
    }

    public void setUserProjectState(Long userProjectState) {
        this.userProjectState = userProjectState;
    }

    public String getApplyReason() {
        return this.applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getRejectReason() {
        return this.rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getDeleteReason() {
        return this.deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
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

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserProjectStateValue() {
        return this.userProjectStateValue;
    }

    public void setUserProjectStateValue(String userProjectStateValue) {
        this.userProjectStateValue = userProjectStateValue;
    }

    public Long getUserProjectStateRelatedValue() {
        return this.userProjectStateRelatedValue;
    }

    public void setUserProjectStateRelatedValue(
            Long userProjectStateRelatedValue) {
        this.userProjectStateRelatedValue = userProjectStateRelatedValue;
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
        if (!(o instanceof UserProjectRelation))
            return false;
        UserProjectRelation bean = (UserProjectRelation) o;
        if (userProjectId.equals(bean.getUserProjectId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return userProjectId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("userProjectId=" + userProjectId + ",");
        buffer.append("projectId=" + projectId + ",");
        buffer.append("userId=" + userId + ",");
        buffer.append("userProjectState=" + userProjectState + ",");
        buffer.append("applyReason=" + applyReason + ",");
        buffer.append("rejectReason=" + rejectReason + ",");
        buffer.append("deleteReason=" + deleteReason + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("userName=" + userName + ",");
        buffer.append("email=" + email + ",");
        buffer.append("phone=" + phone + ",");
        buffer.append("mobile=" + mobile + ",");
        buffer.append("projectName=" + projectName + ",");
        buffer.append("userProjectStateValue=" + userProjectStateValue + ",");
        buffer.append("userProjectStateRelatedValue="
                + userProjectStateRelatedValue);
        buffer.append("]");
        return buffer.toString();
    }
}
