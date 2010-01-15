package cn.edu.pku.dr.requirement.elicitation.data;

public class ProblemreasonEvaluation implements java.io.Serializable {
    public static final String TABLE_NAME = "problemreason_evaluation";

    public static final String VIEW_NAME = "problemreason_evaluation";

    public static final String PRIMARY_KEY = "problemreasonEvaluationId";

    private Long problemreasonEvaluationId;

    private Long problemreasonId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String isGood;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getProblemreasonEvaluationId() {
        return this.problemreasonEvaluationId;
    }

    public void setProblemreasonEvaluationId(Long problemreasonEvaluationId) {
        this.problemreasonEvaluationId = problemreasonEvaluationId;
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

    public String getIsGood() {
        return this.isGood;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
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
        if (!(o instanceof ProblemreasonEvaluation))
            return false;
        ProblemreasonEvaluation bean = (ProblemreasonEvaluation) o;
        if (problemreasonEvaluationId.equals(bean
                .getProblemreasonEvaluationId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemreasonEvaluationId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemreasonEvaluationId=" + problemreasonEvaluationId
                + ",");
        buffer.append("problemreasonId=" + problemreasonId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("isGood=" + isGood);
        buffer.append("]");
        return buffer.toString();
    }
}
