package cn.edu.pku.dr.requirement.elicitation.data;

public class AmbiguityEvaluation implements java.io.Serializable {
    public static final String TABLE_NAME = "ambiguity_evaluation";

    public static final String VIEW_NAME = "ambiguity_evaluation";

    public static final String PRIMARY_KEY = "evaluationId";

    private Long evaluationId;

    private Long ambiguityId;

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

    public Long getEvaluationId() {
        return this.evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getAmbiguityId() {
        return this.ambiguityId;
    }

    public void setAmbiguityId(Long ambiguityId) {
        this.ambiguityId = ambiguityId;
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
        if (!(o instanceof AmbiguityEvaluation))
            return false;
        AmbiguityEvaluation bean = (AmbiguityEvaluation) o;
        if (evaluationId.equals(bean.getEvaluationId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return evaluationId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("evaluationId=" + evaluationId + ",");
        buffer.append("ambiguityId=" + ambiguityId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("isGood=" + isGood);
        buffer.append("]");
        return buffer.toString();
    }
}
