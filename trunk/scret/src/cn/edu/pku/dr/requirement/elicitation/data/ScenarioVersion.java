package cn.edu.pku.dr.requirement.elicitation.data;

public class ScenarioVersion implements java.io.Serializable {
    public static final String TABLE_NAME = "scenario_version";

    public static final String VIEW_NAME = "v_scenario_version";

    public static final String PRIMARY_KEY = "scenarioVersionId";

    private Long scenarioVersionId;

    private Long scenarioId;

    private String datas;

    private String participants;

    private String communicators;

    private String observers;

    private String descriptions;

    private String remarks;

    private String problems;

    private String solutions;

    private Long parentId;

    private String modifyMark;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String useState;

    private String scenarioName;

    private String creatorName;

    public static final String TREE_ID_PROPERTY = "scenarioVersionId";

    public static final String TREE_PARENT_ID_PROPERTY = "parentId";

    public static final String TREE_DISPLAY_PROPERTY = "scenarioName";

    public static final String TREE_FUNCTION_PROPERTY = "";

    public static Boolean isUpdatable(String property) {
        if ("scenarioName".equals(property))
            return new Boolean(false);
        if ("creatorName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getScenarioVersionId() {
        return this.scenarioVersionId;
    }

    public void setScenarioVersionId(Long scenarioVersionId) {
        this.scenarioVersionId = scenarioVersionId;
    }

    public Long getScenarioId() {
        return this.scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getDatas() {
        return this.datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getParticipants() {
        return this.participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getCommunicators() {
        return this.communicators;
    }

    public void setCommunicators(String communicators) {
        this.communicators = communicators;
    }

    public String getObservers() {
        return this.observers;
    }

    public void setObservers(String observers) {
        this.observers = observers;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProblems() {
        return this.problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getSolutions() {
        return this.solutions;
    }

    public void setSolutions(String solutions) {
        this.solutions = solutions;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getModifyMark() {
        return this.modifyMark;
    }

    public void setModifyMark(String modifyMark) {
        this.modifyMark = modifyMark;
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

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public String getScenarioName() {
        return this.scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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
        if (!(o instanceof ScenarioVersion))
            return false;
        ScenarioVersion bean = (ScenarioVersion) o;
        if (scenarioVersionId.equals(bean.getScenarioVersionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return scenarioVersionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("scenarioVersionId=" + scenarioVersionId + ",");
        buffer.append("scenarioId=" + scenarioId + ",");
        buffer.append("datas=" + datas + ",");
        buffer.append("participants=" + participants + ",");
        buffer.append("communicators=" + communicators + ",");
        buffer.append("observers=" + observers + ",");
        buffer.append("descriptions=" + descriptions + ",");
        buffer.append("remarks=" + remarks + ",");
        buffer.append("problems=" + problems + ",");
        buffer.append("solutions=" + solutions + ",");
        buffer.append("parentId=" + parentId + ",");
        buffer.append("modifyMark=" + modifyMark + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("scenarioName=" + scenarioName + ",");
        buffer.append("creatorName=" + creatorName);
        buffer.append("]");
        return buffer.toString();
    }
}
