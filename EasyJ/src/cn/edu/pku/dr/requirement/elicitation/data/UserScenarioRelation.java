package cn.edu.pku.dr.requirement.elicitation.data;

public class UserScenarioRelation implements java.io.Serializable {
    public static final String TABLE_NAME = "user_scenario_relation";

    public static final String VIEW_NAME = "v_user_scenario_relation";

    public static final String PRIMARY_KEY = "userScenarioId";

    private Long userScenarioId;

    private Long userId;

    private Long scenarioId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private Long applyState;

    private String applyReason;

    private String rejectReason;

    private String deleteReason;

    private String userName;

    private String email;

    private String phone;

    private String mobile;

    private String scenarioName;

    private String applyStateValue;

    private Long applyStateRelatedValue;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        if ("email".equals(property))
            return new Boolean(false);
        if ("phone".equals(property))
            return new Boolean(false);
        if ("mobile".equals(property))
            return new Boolean(false);
        if ("scenarioName".equals(property))
            return new Boolean(false);
        if ("applyStateValue".equals(property))
            return new Boolean(false);
        if ("applyStateRelatedValue".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getUserScenarioId() {
        return this.userScenarioId;
    }

    public void setUserScenarioId(Long userScenarioId) {
        this.userScenarioId = userScenarioId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScenarioId() {
        return this.scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
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

    public Long getApplyState() {
        return this.applyState;
    }

    public void setApplyState(Long applyState) {
        this.applyState = applyState;
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

    public String getScenarioName() {
        return this.scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getApplyStateValue() {
        return this.applyStateValue;
    }

    public void setApplyStateValue(String applyStateValue) {
        this.applyStateValue = applyStateValue;
    }

    public Long getApplyStateRelatedValue() {
        return this.applyStateRelatedValue;
    }

    public void setApplyStateRelatedValue(Long applyStateRelatedValue) {
        this.applyStateRelatedValue = applyStateRelatedValue;
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
        if (!(o instanceof UserScenarioRelation))
            return false;
        UserScenarioRelation bean = (UserScenarioRelation) o;
        if (userScenarioId.equals(bean.getUserScenarioId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return userScenarioId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("userScenarioId=" + userScenarioId + ",");
        buffer.append("userId=" + userId + ",");
        buffer.append("scenarioId=" + scenarioId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("applyState=" + applyState + ",");
        buffer.append("applyReason=" + applyReason + ",");
        buffer.append("rejectReason=" + rejectReason + ",");
        buffer.append("deleteReason=" + deleteReason + ",");
        buffer.append("userName=" + userName + ",");
        buffer.append("email=" + email + ",");
        buffer.append("phone=" + phone + ",");
        buffer.append("mobile=" + mobile + ",");
        buffer.append("scenarioName=" + scenarioName + ",");
        buffer.append("applyStateValue=" + applyStateValue + ",");
        buffer.append("applyStateRelatedValue=" + applyStateRelatedValue);
        buffer.append("]");
        return buffer.toString();
    }
}
