package cn.edu.pku.dr.requirement.elicitation.data;

public class FinalsolutionReply implements java.io.Serializable {
    public static final String TABLE_NAME = "finalsolution_reply";

    public static final String VIEW_NAME = "v_finalsolution_reply";

    public static final String PRIMARY_KEY = "finalsolutionReplyId";

    private Long finalsolutionReplyId;

    private Long finalsolutionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String finalsolutionReplyContent;

    private Long goodNum;

    private Long badNum;

    private Long finalsolutionReplyReferenceId;

    private String userName;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getFinalsolutionReplyId() {
        return this.finalsolutionReplyId;
    }

    public void setFinalsolutionReplyId(Long finalsolutionReplyId) {
        this.finalsolutionReplyId = finalsolutionReplyId;
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

    public String getFinalsolutionReplyContent() {
        return this.finalsolutionReplyContent;
    }

    public void setFinalsolutionReplyContent(String finalsolutionReplyContent) {
        this.finalsolutionReplyContent = finalsolutionReplyContent;
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

    public Long getFinalsolutionReplyReferenceId() {
        return this.finalsolutionReplyReferenceId;
    }

    public void setFinalsolutionReplyReferenceId(
            Long finalsolutionReplyReferenceId) {
        this.finalsolutionReplyReferenceId = finalsolutionReplyReferenceId;
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
        if (!(o instanceof FinalsolutionReply))
            return false;
        FinalsolutionReply bean = (FinalsolutionReply) o;
        if (finalsolutionReplyId.equals(bean.getFinalsolutionReplyId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return finalsolutionReplyId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("finalsolutionReplyId=" + finalsolutionReplyId + ",");
        buffer.append("finalsolutionId=" + finalsolutionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("finalsolutionReplyContent=" + finalsolutionReplyContent
                + ",");
        buffer.append("goodNum=" + goodNum + ",");
        buffer.append("badNum=" + badNum + ",");
        buffer.append("finalsolutionReplyReferenceId="
                + finalsolutionReplyReferenceId + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }
}
