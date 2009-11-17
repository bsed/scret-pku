package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Ambiguity implements java.io.Serializable, Comparable<Ambiguity> {
    public int compareTo(Ambiguity other) {
        Ambiguity ambiguity = new Ambiguity();
        ambiguity = (Ambiguity) other;
        if (goodNum.intValue() < ambiguity.goodNum.intValue())// 这里比较的是什么
                                                                // sort方法实现的就是按照此比较的东西从小到大排列
            return -1;
        if (goodNum.intValue() > ambiguity.goodNum.intValue())
            return 1;
        return 0;
    }

    public static final String TABLE_NAME = "ambiguity";

    public static final String VIEW_NAME = "v_ambiguity";

    public static final String PRIMARY_KEY = "ambiguityId";

    private Long ambiguityId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String ambiguityContent;

    private String anonymous;

    private Long ambiguityTypeId;

    private Long goodNum;

    private Long badNum;

    private String problemChange;

    private Long problemOverdueVersion;

    private Long ambiguityEvaluationProblemVersionId;

    private Long solvePendingVersion;

    private Long solvedVersion;

    private String confirmState;

    private String isoverdue;

    private String ambiguityTypeContent;

    private String userName;

    private ArrayList ambiguityEvaluations;

    private ArrayList ambiguityReplys;

    public static Boolean isUpdatable(String property) {
        if ("ambiguityTypeContent".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("ambiguityEvaluations".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation";
        }
        if ("ambiguityReplys".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.AmbiguityReply";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "ambiguityEvaluations", "ambiguityReplys"
        };
        return subClassProperties;
    }

    public Long getAmbiguityId() {
        return this.ambiguityId;
    }

    public void setAmbiguityId(Long ambiguityId) {
        this.ambiguityId = ambiguityId;
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

    public String getAmbiguityContent() {
        return this.ambiguityContent;
    }

    public void setAmbiguityContent(String ambiguityContent) {
        this.ambiguityContent = ambiguityContent;
    }

    public String getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public Long getAmbiguityTypeId() {
        return this.ambiguityTypeId;
    }

    public void setAmbiguityTypeId(Long ambiguityTypeId) {
        this.ambiguityTypeId = ambiguityTypeId;
    }

    public Long getGoodNum() {
        return this.goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }

    public Long getBadNum() {
        return this.badNum;
    }

    public void setBadNum(Long badNum) {
        this.badNum = badNum;
    }

    public String getProblemChange() {
        return this.problemChange;
    }

    public void setProblemChange(String problemChange) {
        this.problemChange = problemChange;
    }

    public Long getProblemOverdueVersion() {
        return this.problemOverdueVersion;
    }

    public void setProblemOverdueVersion(Long problemOverdueVersion) {
        this.problemOverdueVersion = problemOverdueVersion;
    }

    public Long getAmbiguityEvaluationProblemVersionId() {
        return this.ambiguityEvaluationProblemVersionId;
    }

    public void setAmbiguityEvaluationProblemVersionId(
            Long ambiguityEvaluationProblemVersionId) {
        this.ambiguityEvaluationProblemVersionId = ambiguityEvaluationProblemVersionId;
    }

    public Long getSolvePendingVersion() {
        return this.solvePendingVersion;
    }

    public void setSolvePendingVersion(Long solvePendingVersion) {
        this.solvePendingVersion = solvePendingVersion;
    }

    public Long getSolvedVersion() {
        return this.solvedVersion;
    }

    public void setSolvedVersion(Long solvedVersion) {
        this.solvedVersion = solvedVersion;
    }

    public String getConfirmState() {
        return this.confirmState;
    }

    public void setConfirmState(String confirmState) {
        this.confirmState = confirmState;
    }

    public String getAmbiguityTypeContent() {
        return this.ambiguityTypeContent;
    }

    public void setAmbiguityTypeContent(String ambiguityTypeContent) {
        this.ambiguityTypeContent = ambiguityTypeContent;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsoverdue() {
        return this.isoverdue;
    }

    public void setIsoverdue(String isoverdue) {
        this.isoverdue = isoverdue;
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
        if (!(o instanceof Ambiguity))
            return false;
        Ambiguity bean = (Ambiguity) o;
        if (ambiguityId.equals(bean.getAmbiguityId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return ambiguityId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("ambiguityId=" + ambiguityId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("ambiguityContent=" + ambiguityContent + ",");
        buffer.append("anonymous=" + anonymous + ",");
        buffer.append("ambiguityTypeId=" + ambiguityTypeId + ",");
        buffer.append("goodNum=" + goodNum + ",");
        buffer.append("badNum=" + badNum + ",");
        buffer.append("problemChange=" + problemChange + ",");
        buffer.append("problemOverdueVersion=" + problemOverdueVersion + ",");
        buffer.append("ambiguityEvaluationProblemVersionId="
                + ambiguityEvaluationProblemVersionId + ",");
        buffer.append("solvePendingVersion=" + solvePendingVersion + ",");
        buffer.append("solvedVersion=" + solvedVersion + ",");
        buffer.append("confirmState=" + confirmState + ",");
        buffer.append("isoverdue=" + isoverdue + ",");
        buffer.append("ambiguityTypeContent=" + ambiguityTypeContent + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public ArrayList getAmbiguityEvaluations() {
        return ambiguityEvaluations;
    }

    public void setAmbiguityEvaluations(ArrayList ambiguityEvaluations) {
        this.ambiguityEvaluations = ambiguityEvaluations;
    }

    public ArrayList getAmbiguityReplys() {
        return ambiguityReplys;
    }

    public void setAmbiguityReplys(ArrayList ambiguityReplys) {
        this.ambiguityReplys = ambiguityReplys;
    }
}
