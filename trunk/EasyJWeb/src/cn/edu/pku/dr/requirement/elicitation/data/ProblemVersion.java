package cn.edu.pku.dr.requirement.elicitation.data;

public class ProblemVersion implements java.io.Serializable {
    public static final String TABLE_NAME = "problem_version";

    public static final String VIEW_NAME = "problem_version";

    public static final String PRIMARY_KEY = "problemVersionId";

    private String problemContent;

    private Long problemVersionId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public String getProblemContent() {
        return this.problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public Long getProblemVersionId() {
        return this.problemVersionId;
    }

    public void setProblemVersionId(Long problemVersionId) {
        this.problemVersionId = problemVersionId;
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
        if (!(o instanceof ProblemVersion))
            return false;
        ProblemVersion bean = (ProblemVersion) o;
        if (problemVersionId.equals(bean.getProblemVersionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemVersionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemContent=" + problemContent + ",");
        buffer.append("problemVersionId=" + problemVersionId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId);
        buffer.append("]");
        return buffer.toString();
    }
}
