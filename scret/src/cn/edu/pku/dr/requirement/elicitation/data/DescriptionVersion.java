package cn.edu.pku.dr.requirement.elicitation.data;

public class DescriptionVersion implements java.io.Serializable {
    public static final String TABLE_NAME = "description_version";

    public static final String VIEW_NAME = "description_version";

    public static final String PRIMARY_KEY = "descriptionVersionId";

    private Long descriptionVersionId;

    private Long descriptionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String discriptionContent;

    private Long parentId;

    private Long scenarioVersionId;

    private String descriptionVersionContent;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getDescriptionVersionId() {
        return this.descriptionVersionId;
    }

    public void setDescriptionVersionId(Long descriptionVersionId) {
        this.descriptionVersionId = descriptionVersionId;
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

    public String getDiscriptionContent() {
        return this.discriptionContent;
    }

    public void setDiscriptionContent(String discriptionContent) {
        this.discriptionContent = discriptionContent;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getScenarioVersionId() {
        return this.scenarioVersionId;
    }

    public void setScenarioVersionId(Long scenarioVersionId) {
        this.scenarioVersionId = scenarioVersionId;
    }

    public String getDescriptionVersionContent() {
        return this.descriptionVersionContent;
    }

    public void setDescriptionVersionContent(String descriptionVersionContent) {
        this.descriptionVersionContent = descriptionVersionContent;
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
        if (!(o instanceof DescriptionVersion))
            return false;
        DescriptionVersion bean = (DescriptionVersion) o;
        if (descriptionVersionId.equals(bean.getDescriptionVersionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return descriptionVersionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("descriptionVersionId=" + descriptionVersionId + ",");
        buffer.append("descriptionId=" + descriptionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("discriptionContent=" + discriptionContent + ",");
        buffer.append("parentId=" + parentId + ",");
        buffer.append("scenarioVersionId=" + scenarioVersionId + ",");
        buffer.append("descriptionVersionContent=" + descriptionVersionContent);
        buffer.append("]");
        return buffer.toString();
    }
}
