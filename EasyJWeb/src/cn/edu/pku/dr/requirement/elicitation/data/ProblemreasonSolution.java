package cn.edu.pku.dr.requirement.elicitation.data;

public class ProblemreasonSolution implements java.io.Serializable {
    public static final String TABLE_NAME = "problemreason_solution";

    public static final String VIEW_NAME = "problemreason_solution";

    public static final String PRIMARY_KEY = "problemreasonSolutionId";

    private Long problemreasonSolutionId;

    private Long problemId;

    private Long problemsolutionId;

    private Long problemreasonId;

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

    public Long getProblemreasonSolutionId() {
        return this.problemreasonSolutionId;
    }

    public void setProblemreasonSolutionId(Long problemreasonSolutionId) {
        this.problemreasonSolutionId = problemreasonSolutionId;
    }

    public Long getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getProblemsolutionId() {
        return this.problemsolutionId;
    }

    public void setProblemsolutionId(Long problemsolutionId) {
        this.problemsolutionId = problemsolutionId;
    }

    public Long getProblemreasonId() {
        return this.problemreasonId;
    }

    public void setProblemreasonId(Long problemreasonId) {
        this.problemreasonId = problemreasonId;
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
        if (!(o instanceof ProblemreasonSolution))
            return false;
        ProblemreasonSolution bean = (ProblemreasonSolution) o;
        if (problemreasonSolutionId.equals(bean.getProblemreasonSolutionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemreasonSolutionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemreasonSolutionId=" + problemreasonSolutionId
                + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("problemsolutionId=" + problemsolutionId + ",");
        buffer.append("problemreasonId=" + problemreasonId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId);
        buffer.append("]");
        return buffer.toString();
    }
}
