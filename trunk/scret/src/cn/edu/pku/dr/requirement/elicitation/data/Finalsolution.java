package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Finalsolution implements java.io.Serializable, Comparable {
    public static final String TABLE_NAME = "finalsolution";

    public static final String VIEW_NAME = "v_finalsolution";

    public static final String PRIMARY_KEY = "finalsolutionId";

    private Long finalsolutionId;

    private Long problemId;

    private Long problemsolutionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private Long finalgoodNum;

    private Long finalbadNum;

    private String finalsolutionDate;

    private String userName;

    private ArrayList finalsolutionEvaluations;

    private ArrayList finalsolutionReplys;

    public int compareTo(Object other) {
        Finalsolution finalsolution = new Finalsolution();
        finalsolution = (Finalsolution) other;
        if (finalgoodNum.intValue() < finalsolution.getFinalgoodNum()
                .intValue())// 这里比较的是什么
            // sort方法实现的就是按照此比较的东西从小到大排列
            return -1;
        if (finalgoodNum.intValue() > finalsolution.getFinalgoodNum()
                .intValue())
            return 1;

        return 0;
    }

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String propertyName) {
        if ("finalsolutionEvaluations".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.FinalsolutionEvaluation";
        }
        if ("finalsolutionReplys".equals(propertyName)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.FinalsolutionReply";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "finalsolutionEvaluations", "finalsolutionReplys"
        };
        return subClassProperties;
    }

    public Long getFinalsolutionId() {
        return this.finalsolutionId;
    }

    public void setFinalsolutionId(Long finalsolutionId) {
        this.finalsolutionId = finalsolutionId;
    }

    public Long getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
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

    public Long getFinalgoodNum() {
        return this.finalgoodNum;
    }

    public void setFinalgoodNum(Long finalgoodNum) {
        this.finalgoodNum = finalgoodNum;
    }

    public Long getFinalbadNum() {
        return this.finalbadNum;
    }

    public void setFinalbadNum(Long finalbadNum) {
        this.finalbadNum = finalbadNum;
    }

    public String getFinalsolutionDate() {
        return this.finalsolutionDate;
    }

    public void setFinalsolutionDate(String finalsolutionDate) {
        this.finalsolutionDate = finalsolutionDate;
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
        if (!(o instanceof Finalsolution))
            return false;
        Finalsolution bean = (Finalsolution) o;
        if (finalsolutionId.equals(bean.getFinalsolutionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return finalsolutionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("finalsolutionId=" + finalsolutionId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("problemsolutionId=" + problemsolutionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("finalgoodNum=" + finalgoodNum + ",");
        buffer.append("finalbadNum=" + finalbadNum + ",");
        buffer.append("finalsolutionDate=" + finalsolutionDate + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }

    public ArrayList getFinalsolutionEvaluations() {
        return finalsolutionEvaluations;
    }

    public ArrayList getFinalsolutionReplys() {
        return finalsolutionReplys;
    }

    public void setFinalsolutionReplys(ArrayList finalsolutionReplys) {
        this.finalsolutionReplys = finalsolutionReplys;
    }

    public void setFinalsolutionEvaluations(ArrayList finalsolutionEvaluations) {
        this.finalsolutionEvaluations = finalsolutionEvaluations;
    }
}
