package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Problemsolution implements java.io.Serializable, Comparable {
    public static final String TABLE_NAME = "problemsolution";

    public static final String VIEW_NAME = "v_problemsolution";

    public static final String PRIMARY_KEY = "problemsolutionId";

    private Long problemsolutionId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String problemsolutionContent;

    private Long goodNum;

    private Long badNum;

    private String problemChange;

    private Long problemsolutionOverdueVersion;

    private Long problemsolutionProblemId;

    private Long problemsolutionReferenceId;

    private String isVoting;

    private Long votingGoodNum;

    private Long votingBadNum;

    private String isoverdue;

    private String userName;

    private ArrayList problemsolutionEvaluations;

    private ArrayList problemsolutionReplys;

    public int compareTo(Object other) {
        Problemsolution problemsolution = new Problemsolution();
        problemsolution = (Problemsolution) other;
        if (goodNum.intValue() < problemsolution.goodNum.intValue())// 这里比较的是什么
            // sort方法实现的就是按照此比较的东西从小到大排列
            return -1;
        if (goodNum.intValue() > problemsolution.goodNum.intValue())
            return 1;

        return 0;
    }

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("problemsolutionEvaluations".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation";
        }
        if ("problemsolutionReplys".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReply";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "problemsolutionEvaluations", "problemsolutionReplys"
        };
        return subClassProperties;
    }

    public Long getProblemsolutionId() {
        return this.problemsolutionId;
    }

    public void setProblemsolutionId(Long problemsolutionId) {
        this.problemsolutionId = problemsolutionId;
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

    public String getProblemsolutionContent() {
        return this.problemsolutionContent;
    }

    public void setProblemsolutionContent(String problemsolutionContent) {
        this.problemsolutionContent = problemsolutionContent;
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

    public String getIsoverdue() {
        return this.isoverdue;
    }

    public void setIsoverdue(String isoverdue) {
        this.isoverdue = isoverdue;
    }

    public String getProblemChange() {
        return this.problemChange;
    }

    public void setProblemChange(String problemChange) {
        this.problemChange = problemChange;
    }

    public Long getProblemsolutionOverdueVersion() {
        return this.problemsolutionOverdueVersion;
    }

    public void setProblemsolutionOverdueVersion(
            Long problemsolutionOverdueVersion) {
        this.problemsolutionOverdueVersion = problemsolutionOverdueVersion;
    }

    public Long getProblemsolutionProblemId() {
        return this.problemsolutionProblemId;
    }

    public void setProblemsolutionProblemId(Long problemsolutionProblemId) {
        this.problemsolutionProblemId = problemsolutionProblemId;
    }

    public Long getProblemsolutionReferenceId() {
        return this.problemsolutionReferenceId;
    }

    public void setProblemsolutionReferenceId(Long problemsolutionReferenceId) {
        this.problemsolutionReferenceId = problemsolutionReferenceId;
    }

    public String getIsVoting() {
        return this.isVoting;
    }

    public void setIsVoting(String isVoting) {
        this.isVoting = isVoting;
    }

    public Long getVotingGoodNum() {
        return this.votingGoodNum;
    }

    public void setVotingGoodNum(Long votingGoodNum) {
        this.votingGoodNum = votingGoodNum;
    }

    public Long getVotingBadNum() {
        return this.votingBadNum;
    }

    public void setVotingBadNum(Long votingBadNum) {
        this.votingBadNum = votingBadNum;
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
        if (!(o instanceof Problemsolution))
            return false;
        Problemsolution bean = (Problemsolution) o;
        if (problemsolutionId.equals(bean.getProblemsolutionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemsolutionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemsolutionId=" + problemsolutionId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("problemsolutionContent=" + problemsolutionContent + ",");
        buffer.append("goodNum=" + goodNum + ",");
        buffer.append("badNum=" + badNum + ",");
        buffer.append("problemChange=" + problemChange + ",");
        buffer.append("problemsolutionOverdueVersion="
                + problemsolutionOverdueVersion + ",");
        buffer.append("problemsolutionProblemId=" + problemsolutionProblemId
                + ",");
        buffer.append("problemsolutionReferenceId="
                + problemsolutionReferenceId + ",");
        buffer.append("isVoting=" + isVoting + ",");
        buffer.append("votingGoodNum=" + votingGoodNum + ",");
        buffer.append("votingBadNum=" + votingBadNum + ",");
        buffer.append("isoverdue=" + isoverdue + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public ArrayList getProblemsolutionEvaluations() {
        return problemsolutionEvaluations;
    }

    public ArrayList getProblemsolutionReplys() {
        return problemsolutionReplys;
    }

    public void setProblemsolutionReplys(ArrayList problemsolutionReplys) {
        this.problemsolutionReplys = problemsolutionReplys;
    }

    public void setProblemsolutionEvaluations(
            ArrayList problemsolutionEvaluations) {
        this.problemsolutionEvaluations = problemsolutionEvaluations;
    }
}
