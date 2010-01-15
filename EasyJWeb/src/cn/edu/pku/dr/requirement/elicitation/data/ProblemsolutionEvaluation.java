package cn.edu.pku.dr.requirement.elicitation.data;

public class ProblemsolutionEvaluation implements java.io.Serializable {
    public static final String TABLE_NAME = "problemsolution_evaluation";

    public static final String VIEW_NAME = "problemsolution_evaluation";

    public static final String PRIMARY_KEY = "problemsolutionEvaluationId";

    private Long problemsolutionEvaluationId;

    private Long problemsolutionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String isGood;

    private Integer stage;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getProblemsolutionEvaluationId() {
        return this.problemsolutionEvaluationId;
    }

    public void setProblemsolutionEvaluationId(Long problemsolutionEvaluationId) {
        this.problemsolutionEvaluationId = problemsolutionEvaluationId;
    }

    public Long getProblemsolutionId() {
        return this.problemsolutionId;
    }

    public void setProblemsolutionId(Long problemsolutionId) {
        this.problemsolutionId = problemsolutionId;
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

    public String getIsGood() {
        return this.isGood;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    public Integer getStage() {
        return this.stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
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
        if (!(o instanceof ProblemsolutionEvaluation))
            return false;
        ProblemsolutionEvaluation bean = (ProblemsolutionEvaluation) o;
        if (problemsolutionEvaluationId.equals(bean
                .getProblemsolutionEvaluationId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemsolutionEvaluationId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemsolutionEvaluationId="
                + problemsolutionEvaluationId + ",");
        buffer.append("problemsolutionId=" + problemsolutionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("isGood=" + isGood);
        buffer.append("stage=" + stage);
        buffer.append("]");
        return buffer.toString();
    }
}
