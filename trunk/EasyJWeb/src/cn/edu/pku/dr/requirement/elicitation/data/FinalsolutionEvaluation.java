package cn.edu.pku.dr.requirement.elicitation.data;

public class FinalsolutionEvaluation implements java.io.Serializable {
    public static final String TABLE_NAME = "finalsolution_evaluation";

    public static final String VIEW_NAME = "finalsolution_evaluation";

    public static final String PRIMARY_KEY = "finalsolutionEvaluationId";

    private Long finalsolutionEvaluationId;

    private Long finalsolutionId;

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

    public Long getFinalsolutionEvaluationId() {
        return this.finalsolutionEvaluationId;
    }

    public void setFinalsolutionEvaluationId(Long finalsolutionEvaluationId) {
        this.finalsolutionEvaluationId = finalsolutionEvaluationId;
    }

    public Long getFinalsolutionId() {
        return this.finalsolutionId;
    }

    public void setFinalsolutionId(Long finalsolutionId) {
        this.finalsolutionId = finalsolutionId;
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
        if (!(o instanceof FinalsolutionEvaluation))
            return false;
        FinalsolutionEvaluation bean = (FinalsolutionEvaluation) o;
        if (finalsolutionEvaluationId.equals(bean
                .getFinalsolutionEvaluationId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return finalsolutionEvaluationId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("finalsolutionEvaluationId=" + finalsolutionEvaluationId
                + ",");
        buffer.append("finalsolutionId=" + finalsolutionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("isGood=" + isGood);
        buffer.append("]");
        return buffer.toString();
    }
}
