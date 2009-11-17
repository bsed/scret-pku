package cn.edu.pku.dr.requirement.elicitation.data;

public class ProblemvalueEvaluation implements java.io.Serializable {
    public static final String TABLE_NAME = "problemvalue_evaluation";

    public static final String VIEW_NAME = "problemvalue_evaluation";

    public static final String PRIMARY_KEY = "problemvalueEvaluationId";

    private Long problemvalueEvaluationId;

    private Long problemvalueId;

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

    public Long getProblemvalueEvaluationId() {
        return this.problemvalueEvaluationId;
    }

    public void setProblemvalueEvaluationId(Long problemvalueEvaluationId) {
        this.problemvalueEvaluationId = problemvalueEvaluationId;
    }

    public Long getProblemvalueId() {
        return this.problemvalueId;
    }

    public void setProblemvalueId(Long problemvalueId) {
        this.problemvalueId = problemvalueId;
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
        if (!(o instanceof ProblemvalueEvaluation))
            return false;
        ProblemvalueEvaluation bean = (ProblemvalueEvaluation) o;
        if (problemvalueEvaluationId.equals(bean.getProblemvalueEvaluationId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemvalueEvaluationId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemvalueEvaluationId=" + problemvalueEvaluationId
                + ",");
        buffer.append("problemvalueId=" + problemvalueId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("isGood=" + isGood);
        buffer.append("]");
        return buffer.toString();
    }
}
