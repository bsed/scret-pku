package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class ProblemsolutionReply implements java.io.Serializable {
    public static final String TABLE_NAME = "problemsolution_reply";

    public static final String VIEW_NAME = "v_problemsolution_reply";

    public static final String PRIMARY_KEY = "problemsolutionReplyId";

    private Long problemsolutionReplyId;

    private Long problemsolutionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String problemsolutionReplyContent;

    private Long goodNum;

    private Long badNum;

    private Long problemsolutionReplyOverdueVersion;

    private Long problemsolutionReplyProblemId;

    private Long problemsoltuionReplyReferenceId;

    private String isoverdue;

    private String userName;

    private ArrayList problemsolutionReplyEvaluations;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("problemsolutionReplyEvaluations".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation";
        }

        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "problemsolutionReplyEvaluations"
        };
        return subClassProperties;
    }

    public Long getProblemsolutionReplyId() {
        return this.problemsolutionReplyId;
    }

    public void setProblemsolutionReplyId(Long problemsolutionReplyId) {
        this.problemsolutionReplyId = problemsolutionReplyId;
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

    public String getProblemsolutionReplyContent() {
        return this.problemsolutionReplyContent;
    }

    public void setProblemsolutionReplyContent(
            String problemsolutionReplyContent) {
        this.problemsolutionReplyContent = problemsolutionReplyContent;
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

    public Long getProblemsolutionReplyOverdueVersion() {
        return this.problemsolutionReplyOverdueVersion;
    }

    public void setProblemsolutionReplyOverdueVersion(
            Long problemsolutionReplyOverdueVersion) {
        this.problemsolutionReplyOverdueVersion = problemsolutionReplyOverdueVersion;
    }

    public Long getProblemsolutionReplyProblemId() {
        return this.problemsolutionReplyProblemId;
    }

    public void setProblemsolutionReplyProblemId(
            Long problemsolutionReplyProblemId) {
        this.problemsolutionReplyProblemId = problemsolutionReplyProblemId;
    }

    public Long getProblemsoltuionReplyReferenceId() {
        return this.problemsoltuionReplyReferenceId;
    }

    public void setProblemsoltuionReplyReferenceId(
            Long problemsoltuionReplyReferenceId) {
        this.problemsoltuionReplyReferenceId = problemsoltuionReplyReferenceId;
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
        if (!(o instanceof ProblemsolutionReply))
            return false;
        ProblemsolutionReply bean = (ProblemsolutionReply) o;
        if (problemsolutionReplyId.equals(bean.getProblemsolutionReplyId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemsolutionReplyId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemsolutionReplyId=" + problemsolutionReplyId + ",");
        buffer.append("problemsolutionId=" + problemsolutionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("problemsolutionReplyContent="
                + problemsolutionReplyContent + ",");
        buffer.append("goodNum=" + goodNum + ",");
        buffer.append("badNum=" + badNum + ",");
        buffer.append("problemsolutionReplyOverdueVersion="
                + problemsolutionReplyOverdueVersion + ",");
        buffer.append("problemsolutionReplyProblemId="
                + problemsolutionReplyProblemId + ",");
        buffer.append("problemsoltuionReplyReferenceId="
                + problemsoltuionReplyReferenceId + ",");
        buffer.append("isoverdue=" + isoverdue + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public void setProblemsolutionReplyEvaluations(
            ArrayList problemsolutionReplyEvaluations) {
        this.problemsolutionReplyEvaluations = problemsolutionReplyEvaluations;
    }

    public ArrayList getProblemsolutionReplyEvaluations() {
        return problemsolutionReplyEvaluations;
    }
}
