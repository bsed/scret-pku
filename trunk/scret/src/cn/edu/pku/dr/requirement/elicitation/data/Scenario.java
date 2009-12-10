package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Scenario implements java.io.Serializable {
    public static final String TABLE_NAME = "scenario";

    public static final String VIEW_NAME = "v_scenario";

    public static final String PRIMARY_KEY = "scenarioId";

    public static final String ID_PROPERTY = "scenarioId";

    public static final String DISPLAY_PROPERTY = "scenarioName";
    
    private Long scenarioId;

    private Long projectId;

    private Long cstId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private String scenarioName;

    private Long creatorId;

    private Integer feezeId;

    private java.sql.Date freezeTime;

    private String version;

    private java.sql.Date versionTime;

    private String dicType;

    private String projectName;

    private String cstName;

    private String userName;

    private java.util.ArrayList roles;

    private java.util.ArrayList descriptions;

    private java.util.ArrayList datas;

    private java.util.ArrayList problems;

    public static boolean isUpdatable(String property) {
        if ("projectName".equals(property))
            return false;
        if ("cstName".equals(property))
            return false;
        if ("userName".equals(property))
            return false;
        return true;
    }

    public static String getSubClass(String propertyName) {
        if ("datas".equals(propertyName))
            return "cn.edu.pku.dr.requirement.elicitation.data.ScenarioDataRelation";
        if ("roles".equals(propertyName))
            return "cn.edu.pku.dr.requirement.elicitation.data.ScenarioRoleRelation";
        if ("descriptions".equals(propertyName))
            return "cn.edu.pku.dr.requirement.elicitation.data.Description";
        if ("problems".equals(propertyName))
            return "cn.edu.pku.dr.requirement.elicitation.data.Problem";
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "datas", "roles", "descriptions", "problems"
        };
        return subClassProperties;
    }

    public Long getScenarioId() {
        return this.scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public String getScenarioName() {
        return this.scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getFeezeId() {
        return this.feezeId;
    }

    public void setFeezeId(Integer feezeId) {
        this.feezeId = feezeId;
    }

    public java.sql.Date getFreezeTime() {
        return this.freezeTime;
    }

    public void setFreezeTime(java.sql.Date freezeTime) {
        this.freezeTime = freezeTime;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public java.sql.Date getVersionTime() {
        return this.versionTime;
    }

    public void setVersionTime(java.sql.Date versionTime) {
        this.versionTime = versionTime;
    }

    public String getDicType() {
        return this.dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCstName() {
        return this.cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
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
        if (!(o instanceof Scenario))
            return false;
        Scenario bean = (Scenario) o;
        if (scenarioId.equals(bean.getScenarioId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return scenarioId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("scenarioId=" + scenarioId + ",");
        buffer.append("projectId=" + projectId + ",");
        buffer.append("cstId=" + cstId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("scenarioName=" + scenarioName + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("feezeId=" + feezeId + ",");
        buffer.append("freezeTime=" + freezeTime + ",");
        buffer.append("version=" + version + ",");
        buffer.append("versionTime=" + versionTime + ",");
        buffer.append("dicType=" + dicType + ",");
        buffer.append("projectName=" + projectName + ",");
        buffer.append("cstName=" + cstName + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public java.util.ArrayList getRoles() {
        return roles;
    }

    public void setRoles(java.util.ArrayList roles) {
        this.roles = roles;
    }

    public java.util.ArrayList getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(java.util.ArrayList descriptions) {
        this.descriptions = descriptions;
    }

    public java.util.ArrayList getDatas() {
        return datas;
    }

    public void setDatas(java.util.ArrayList datas) {
        this.datas = datas;
    }

    public java.util.ArrayList getProblems() {
        return problems;
    }

    public void setProblems(java.util.ArrayList problems) {
        this.problems = problems;
    }

}
