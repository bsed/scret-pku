package cn.edu.pku.dr.requirement.elicitation.data;

public class Solution implements java.io.Serializable {
    public static final String TABLE_NAME = "solution";

    public static final String VIEW_NAME = "solution";

    public static final String PRIMARY_KEY = "solutionId";

    private Long solutionId;

    private Long roleId;

    private Long problemId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String solutionContent;

    private String anonymous;

    private String isVoting;

    private Long agreeNum;

    private Long disagreeNum;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getSolutionId() {
        return this.solutionId;
    }

    public void setSolutionId(Long solutionId) {
        this.solutionId = solutionId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getSolutionContent() {
        return this.solutionContent;
    }

    public void setSolutionContent(String solutionContent) {
        this.solutionContent = solutionContent;
    }

    public String getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getIsVoting() {
        return this.isVoting;
    }

    public void setIsVoting(String isVoting) {
        this.isVoting = isVoting;
    }

    public Long getAgreeNum() {
        return this.agreeNum;
    }

    public void setAgreeNum(Long agreeNum) {
        this.agreeNum = agreeNum;
    }

    public Long getDisagreeNum() {
        return this.disagreeNum;
    }

    public void setDisagreeNum(Long disagreeNum) {
        this.disagreeNum = disagreeNum;
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
        if (!(o instanceof Solution))
            return false;
        Solution bean = (Solution) o;
        if (solutionId.equals(bean.getSolutionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return solutionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("solutionId=" + solutionId + ",");
        buffer.append("roleId=" + roleId + ",");
        buffer.append("problemId=" + problemId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("solutionContent=" + solutionContent + ",");
        buffer.append("anonymous=" + anonymous + ",");
        buffer.append("isVoting=" + isVoting + ",");
        buffer.append("agreeNum=" + agreeNum + ",");
        buffer.append("disagreeNum=" + disagreeNum);
        buffer.append("]");
        return buffer.toString();
    }
}
