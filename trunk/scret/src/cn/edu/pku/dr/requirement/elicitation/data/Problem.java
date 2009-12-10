package cn.edu.pku.dr.requirement.elicitation.data;

public class Problem implements java.io.Serializable {
    public static final String TABLE_NAME = "problem";

    public static final String VIEW_NAME = "v_problem";

    public static final String PRIMARY_KEY = "problemId";

    private Long problemId;

    private Long scenarioId;

    private Long roleId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String anonymous;

    private String problemTitle;

    private Integer statusId;

    private java.sql.Date probelmSolvedTime;

    private Short problemAward;

    private java.sql.Date votingEndTime;

    private Long votingNum;

    private String problemContent;

    private Long problemVersionId;

    private String userName;

    private String problemStatus;

    private java.util.ArrayList solutions;

    private java.util.ArrayList ambiguitiys;

    private java.util.ArrayList problemvalues;

    private java.util.ArrayList problemreasons;

    private java.util.ArrayList problemsolutions;

    private java.util.ArrayList finalsolutions;

    public java.util.ArrayList getAmbiguitiys() {
        return ambiguitiys;
    }

    public void setAmbiguitiys(java.util.ArrayList ambiguitiys) {
        this.ambiguitiys = ambiguitiys;
    }

    public void setSolutions(java.util.ArrayList solutions) {
        this.solutions = solutions;
    }

    public java.util.ArrayList getSolutions() {
        return solutions;
    }

    public java.util.ArrayList getProblemvalues() {
        return problemvalues;
    }

    public void setProblemvalues(java.util.ArrayList problemvalues) {
        this.problemvalues = problemvalues;
    }

    public java.util.ArrayList getProblemreasons() {
        return problemreasons;
    }

    public void setProblemreasons(java.util.ArrayList problemreasons) {
        this.problemreasons = problemreasons;
    }

    public java.util.ArrayList getProblemsolutions() {
        return problemsolutions;
    }

    public void setProblemsolutions(java.util.ArrayList problemsolutions) {
        this.problemsolutions = problemsolutions;
    }

    public java.util.ArrayList getFinalsolutions() {
        return finalsolutions;
    }

    public void setFinalsolutions(java.util.ArrayList finalsolutions) {
        this.finalsolutions = finalsolutions;
    }

    public static Boolean isUpdatable(String property) {
        if ("problemContent".equals(property))
            return new Boolean(false);
        if ("problemVersionId".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        if ("problemStatus".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("solutions".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Solution";
        }
        if ("ambiguitiys".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Ambiguity";
        }
        if ("problemvalues".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Problemvalue";
        }
        if ("problemreasons".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Problemreason";
        }
        if ("problemsolutions".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Problemsolution";
        }
        if ("finalsolutions".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Finalsolution";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "solutions", "ambiguitiys", "problemvalues", "problemreasons",
            "problemsolutions", "finalsolutions"
        };
        return subClassProperties;
    }

    public Long getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getScenarioId() {
        return this.scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getProblemTitle() {
        return this.problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public Integer getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public java.sql.Date getProbelmSolvedTime() {
        return this.probelmSolvedTime;
    }

    public void setProbelmSolvedTime(java.sql.Date probelmSolvedTime) {
        this.probelmSolvedTime = probelmSolvedTime;
    }

    public Short getProblemAward() {
        return this.problemAward;
    }

    public void setProblemAward(Short problemAward) {
        this.problemAward = problemAward;
    }

    public java.sql.Date getVotingEndTime() {
        return this.votingEndTime;
    }

    public void setVotingEndTime(java.sql.Date votingEndTime) {
        this.votingEndTime = votingEndTime;
    }

    public Long getVotingNum() {
        return this.votingNum;
    }

    public void setVotingNum(Long votingNum) {
        this.votingNum = votingNum;
    }

    public String getProblemContent() {
        return this.problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public Long getProblemVersionId() {
        return this.problemVersionId;
    }

    public void setProblemVersionId(Long problemVersionId) {
        this.problemVersionId = problemVersionId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProblemStatus() {
        return this.problemStatus;
    }

    public void setProblemStatus(String problemStatus) {
        this.problemStatus = problemStatus;
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
        if (!(o instanceof Problem))
            return false;
        Problem bean = (Problem) o;
        if (problemId.equals(bean.getProblemId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return problemId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("scenarioId=" + scenarioId + ",");
        buffer.append("roleId=" + roleId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("anonymous=" + anonymous + ",");
        buffer.append("problemTitle=" + problemTitle + ",");
        buffer.append("statusId=" + statusId + ",");
        buffer.append("probelmSolvedTime=" + probelmSolvedTime + ",");
        buffer.append("problemAward=" + problemAward + ",");
        buffer.append("votingEndTime=" + votingEndTime + ",");
        buffer.append("votingNum=" + votingNum + ",");
        buffer.append("problemContent=" + problemContent + ",");
        buffer.append("problemVersionId=" + problemVersionId + ",");
        buffer.append("userName=" + userName + ",");
        buffer.append("problemStatus=" + problemStatus);
        buffer.append("]");
        return buffer.toString();
    }
}
