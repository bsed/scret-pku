package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Problemreason implements java.io.Serializable,
        Comparable<Problemreason> {
    public int compareTo(Problemreason other) {
        Problemreason problemreason = new Problemreason();
        problemreason = (Problemreason) other;
        if (goodNum.intValue() < problemreason.goodNum.intValue())// 这里比较的是什么
            // sort方法实现的就是按照此比较的东西从小到大排列
            return -1;
        if (goodNum.intValue() > problemreason.goodNum.intValue())
            return 1;

        return 0;
    }

    public static final String TABLE_NAME = "problemreason";

    public static final String VIEW_NAME = "v_problemreason";

    public static final String PRIMARY_KEY = "problemreasonId";

    private Long problemreasonId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String problemreasonContent;

    private String anonymous;

    private Long goodNum;

    private Long badNum;

    private String problemChange;

    private Long problemreasonOverdueVersion;

    private Long problemreasonEvaluationProblemId;

    private Long solvePedingVersion;

    private Long solvedVersion;

    private String confirmState;

    private String isoverdue;

    private String userName;

    private ArrayList problemreasonEvaluations;

    private ArrayList problemreasonReplys;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property)) {
            return new Boolean(false);
        }
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("problemreasonEvaluations".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation";
        }
        if ("problemreasonReplys".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonReply";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "problemreasonEvaluations", "problemreasonReplys"
        };
        return subClassProperties;
    }

    public Long getProblemreasonId() {
        return this.problemreasonId;
    }

    public void setProblemreasonId(Long problemreasonId) {
        this.problemreasonId = problemreasonId;
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

    public String getProblemreasonContent() {
        return this.problemreasonContent;
    }

    public void setProblemreasonContent(String problemreasonContent) {
        this.problemreasonContent = problemreasonContent;
    }

    public String getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
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

    public Long getProblemreasonOverdueVersion() {
        return this.problemreasonOverdueVersion;
    }

    public void setProblemreasonOverdueVersion(Long problemreasonOverdueVersion) {
        this.problemreasonOverdueVersion = problemreasonOverdueVersion;
    }

    public Long getProblemreasonEvaluationProblemId() {
        return this.problemreasonEvaluationProblemId;
    }

    public void setProblemreasonEvaluationProblemId(
            Long problemreasonEvaluationProblemId) {
        this.problemreasonEvaluationProblemId = problemreasonEvaluationProblemId;
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
        if (!(o instanceof Problemreason)) {
            return false;
        }
        Problemreason bean = (Problemreason) o;
        if (problemreasonId.equals(bean.getProblemreasonId())) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return problemreasonId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemreasonId=" + problemreasonId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("problemreasonContent=" + problemreasonContent + ",");
        buffer.append("anonymous=" + anonymous + ",");
        buffer.append("goodNum=" + goodNum + ",");
        buffer.append("badNum=" + badNum + ",");
        buffer.append("problemChange=" + problemChange + ",");
        buffer.append("problemreasonOverdueVersion="
                + problemreasonOverdueVersion + ",");
        buffer.append("problemreasonEvaluationProblemId="
                + problemreasonEvaluationProblemId + ",");
        buffer.append("solvePedingVersion=" + solvePedingVersion + ",");
        buffer.append("solvedVersion=" + solvedVersion + ",");
        buffer.append("confirmState=" + confirmState + ",");
        buffer.append("isoverdue=" + isoverdue + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public ArrayList getProblemreasonEvaluations() {
        return problemreasonEvaluations;
    }

    public ArrayList getProblemreasonReplys() {
        return problemreasonReplys;
    }

    public void setProblemreasonReplys(ArrayList problemreasonReplys) {
        this.problemreasonReplys = problemreasonReplys;
    }

    public void setProblemreasonEvaluations(ArrayList problemreasonEvaluations) {
        this.problemreasonEvaluations = problemreasonEvaluations;
    }
}
