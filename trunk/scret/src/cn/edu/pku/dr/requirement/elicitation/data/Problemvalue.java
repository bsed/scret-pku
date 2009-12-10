package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Problemvalue implements java.io.Serializable,
        Comparable<Problemvalue> {
    public int compareTo(Problemvalue other) {
        Problemvalue problemvalue = new Problemvalue();
        problemvalue = (Problemvalue) other;
        if (goodNum.intValue() < problemvalue.goodNum.intValue())// 这里比较的是什么
                                                                    // sort方法实现的就是按照此比较的东西从小到大排列
            return -1;
        if (goodNum.intValue() > problemvalue.goodNum.intValue())
            return 1;

        return 0;
    }

    public static final String TABLE_NAME = "problemvalue";

    public static final String VIEW_NAME = "v_problemvalue";

    public static final String PRIMARY_KEY = "problemvalueId";

    private Long problemvalueId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String problemvalueContent;

    private String anonymous;

    private Long problemvalueTypeId;

    private Long goodNum;

    private Long badNum;

    private String problemChange;

    private Long problemvalueOverdueVersion;

    private Long problemvalueEvaluationProblemVersionId;

    private Long solvePedingVersion;

    private Long solvedVersion;

    private String confirmState;

    private String isoverdue;

    private String problemvalueTypeContent;

    private String userName;

    private ArrayList problemvalueEvaluations;

    private ArrayList problemvalueReplys;

    public static Boolean isUpdatable(String property) {
        if ("problemvalueTypeContent".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("problemvalueEvaluations".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation";
        }
        if ("problemvalueReplys".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueReply";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "problemvalueEvaluations", "problemvalueReplys"
        };
        return subClassProperties;
    }

    public Long getProblemvalueId() {
        return this.problemvalueId;
    }

    public void setProblemvalueId(Long problemvalueId) {
        this.problemvalueId = problemvalueId;
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

    public String getProblemvalueContent() {
        return this.problemvalueContent;
    }

    public void setProblemvalueContent(String problemvalueContent) {
        this.problemvalueContent = problemvalueContent;
    }

    public String getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public Long getProblemvalueTypeId() {
        return this.problemvalueTypeId;
    }

    public void setProblemvalueTypeId(Long problemvalueTypeId) {
        this.problemvalueTypeId = problemvalueTypeId;
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

    public Long getProblemvalueOverdueVersion() {
        return this.problemvalueOverdueVersion;
    }

    public void setProblemvalueOverdueVersion(Long problemvalueOverdueVersion) {
        this.problemvalueOverdueVersion = problemvalueOverdueVersion;
    }

    public Long getProblemvalueEvaluationProblemVersionId() {
        return this.problemvalueEvaluationProblemVersionId;
    }

    public void setProblemvalueEvaluationProblemVersionId(
            Long problemvalueEvaluationProblemVersionId) {
        this.problemvalueEvaluationProblemVersionId = problemvalueEvaluationProblemVersionId;
    }

    public Long getSolvePedingVersion() {
        return this.solvePedingVersion;
    }

    public void setSolvePedingVersion(Long solvePedingVersion) {
        this.solvePedingVersion = solvePedingVersion;
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

    public String getIsoverdue() {
        return this.isoverdue;
    }

    public void setIsoverdue(String isoverdue) {
        this.isoverdue = isoverdue;
    }

    public String getProblemvalueTypeContent() {
        return this.problemvalueTypeContent;
    }

    public void setProblemvalueTypeContent(String problemvalueTypeContent) {
        this.problemvalueTypeContent = problemvalueTypeContent;
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
        if (!(o instanceof Problemvalue))
            return false;
        Problemvalue bean = (Problemvalue) o;
        if (problemvalueId.equals(bean.getProblemvalueId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemvalueId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemvalueId=" + problemvalueId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("problemvalueContent=" + problemvalueContent + ",");
        buffer.append("anonymous=" + anonymous + ",");
        buffer.append("problemvalueTypeId=" + problemvalueTypeId + ",");
        buffer.append("goodNum=" + goodNum + ",");
        buffer.append("badNum=" + badNum + ",");
        buffer.append("problemChange=" + problemChange + ",");
        buffer.append("problemvalueOverdueVersion="
                + problemvalueOverdueVersion + ",");
        buffer.append("problemvalueEvaluationProblemVersionId="
                + problemvalueEvaluationProblemVersionId + ",");
        buffer.append("solvePedingVersion=" + solvePedingVersion + ",");
        buffer.append("solvedVersion=" + solvedVersion + ",");
        buffer.append("confirmState=" + confirmState + ",");
        buffer.append("isoverdue=" + isoverdue + ",");
        buffer.append("problemvalueTypeContent=" + problemvalueTypeContent
                + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public ArrayList getProblemvalueEvaluations() {
        return problemvalueEvaluations;
    }

    public void setProblemvalueEvaluations(ArrayList problemvalueEvaluations) {
        this.problemvalueEvaluations = problemvalueEvaluations;
    }

    public ArrayList getProblemvalueReplys() {
        return problemvalueReplys;
    }

    public void setProblemvalueReplys(ArrayList problemvalueReplys) {
        this.problemvalueReplys = problemvalueReplys;
    }
}
