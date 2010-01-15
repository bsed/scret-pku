package cn.edu.pku.dr.requirement.elicitation.data;

public class ScenarioRoleRelation implements java.io.Serializable {
    public static final String TABLE_NAME = "scenario_role_relation";

    public static final String VIEW_NAME = "v_scenario_role_relation";

    public static final String PRIMARY_KEY = "scenaroRoleId";

    private Long scenaroRoleId;

    private Long roleId;

    private Long scenarioId;

    private String roleType;

    private Integer sequence;

    private String scenarioName;

    private String roleName;

    private Long projectId;

    private String projectName;

    public static Boolean isUpdatable(String property) {
        if ("scenarioName".equals(property))
            return new Boolean(false);
        if ("roleName".equals(property))
            return new Boolean(false);
        if ("projectId".equals(property))
            return new Boolean(false);
        if ("projectName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getScenaroRoleId() {
        return this.scenaroRoleId;
    }

    public void setScenaroRoleId(Long scenaroRoleId) {
        this.scenaroRoleId = scenaroRoleId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getScenarioId() {
        return this.scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getScenarioName() {
        return this.scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
        if (!(o instanceof ScenarioRoleRelation))
            return false;
        ScenarioRoleRelation bean = (ScenarioRoleRelation) o;
        if (scenaroRoleId.equals(bean.getScenaroRoleId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return scenaroRoleId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("scenaroRoleId=" + scenaroRoleId + ",");
        buffer.append("roleId=" + roleId + ",");
        buffer.append("scenarioId=" + scenarioId + ",");
        buffer.append("roleType=" + roleType + ",");
        buffer.append("sequence=" + sequence + ",");
        buffer.append("scenarioName=" + scenarioName + ",");
        buffer.append("roleName=" + roleName + ",");
        buffer.append("projectId=" + projectId + ",");
        buffer.append("projectName=" + projectName);
        buffer.append("]");
        return buffer.toString();
    }
}
