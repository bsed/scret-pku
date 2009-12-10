package cn.edu.pku.dr.requirement.elicitation.data;

public class ProblemvalueTypeValue implements java.io.Serializable {
    public static final String TABLE_NAME = "problemvalue_type_value";

    public static final String VIEW_NAME = "v_problemvalue_type_value";

    public static final String PRIMARY_KEY = "problemvalueTypeValueId";

    private Long problemvalueTypeValueId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private Long problemvalueTypeId;

    private Long typechangeProblemId;

    private String problemvalueContent;

    private String userName;

    public static Boolean isUpdatable(String property) {
        if ("problemvalueContent".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getProblemvalueTypeValueId() {
        return this.problemvalueTypeValueId;
    }

    public void setProblemvalueTypeValueId(Long problemvalueTypeValueId) {
        this.problemvalueTypeValueId = problemvalueTypeValueId;
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

    public Long getProblemvalueTypeId() {
        return this.problemvalueTypeId;
    }

    public void setProblemvalueTypeId(Long problemvalueTypeId) {
        this.problemvalueTypeId = problemvalueTypeId;
    }

    public Long getTypechangeProblemId() {
        return this.typechangeProblemId;
    }

    public void setTypechangeProblemId(Long typechangeProblemId) {
        this.typechangeProblemId = typechangeProblemId;
    }

    public String getProblemvalueContent() {
        return this.problemvalueContent;
    }

    public void setProblemvalueContent(String problemvalueContent) {
        this.problemvalueContent = problemvalueContent;
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
        if (!(o instanceof ProblemvalueTypeValue))
            return false;
        ProblemvalueTypeValue bean = (ProblemvalueTypeValue) o;
        if (problemvalueTypeValueId.equals(bean.getProblemvalueTypeValueId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemvalueTypeValueId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemvalueTypeValueId=" + problemvalueTypeValueId
                + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("problemvalueTypeId=" + problemvalueTypeId + ",");
        buffer.append("typechangeProblemId=" + typechangeProblemId + ",");
        buffer.append("problemvalueContent=" + problemvalueContent + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }
}
