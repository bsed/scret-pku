package cn.edu.pku.dr.requirement.elicitation.data;

public class AmbiguityTypeValue implements java.io.Serializable {
    public static final String TABLE_NAME = "ambiguity_type_value";

    public static final String VIEW_NAME = "v_ambiguity_type_value";

    public static final String PRIMARY_KEY = "ambiguityTypeValueId";

    private Long ambiguityTypeValueId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private Long ambiguityTypeId;

    private Long typechangeProblemId;

    private String ambiguityContent;

    private String userName;

    public static Boolean isUpdatable(String property) {
        if ("ambiguityContent".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getAmbiguityTypeValueId() {
        return this.ambiguityTypeValueId;
    }

    public void setAmbiguityTypeValueId(Long ambiguityTypeValueId) {
        this.ambiguityTypeValueId = ambiguityTypeValueId;
    }

    public Long getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
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

    public Long getAmbiguityTypeId() {
        return this.ambiguityTypeId;
    }

    public void setAmbiguityTypeId(Long ambiguityTypeId) {
        this.ambiguityTypeId = ambiguityTypeId;
    }

    public Long getTypechangeProblemId() {
        return this.typechangeProblemId;
    }

    public void setTypechangeProblemId(Long typechangeProblemId) {
        this.typechangeProblemId = typechangeProblemId;
    }

    public String getAmbiguityContent() {
        return this.ambiguityContent;
    }

    public void setAmbiguityContent(String ambiguityContent) {
        this.ambiguityContent = ambiguityContent;
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
        if (!(o instanceof AmbiguityTypeValue))
            return false;
        AmbiguityTypeValue bean = (AmbiguityTypeValue) o;
        if (ambiguityTypeValueId.equals(bean.getAmbiguityTypeValueId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return ambiguityTypeValueId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("ambiguityTypeValueId=" + ambiguityTypeValueId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("ambiguityTypeId=" + ambiguityTypeId + ",");
        buffer.append("typechangeProblemId=" + typechangeProblemId + ",");
        buffer.append("ambiguityContent=" + ambiguityContent + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }
}
