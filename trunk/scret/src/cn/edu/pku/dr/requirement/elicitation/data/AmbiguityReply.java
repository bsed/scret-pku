package cn.edu.pku.dr.requirement.elicitation.data;

public class AmbiguityReply implements java.io.Serializable {
    public static final String TABLE_NAME = "ambiguity_reply";

    public static final String VIEW_NAME = "ambiguity_reply";

    public static final String PRIMARY_KEY = "ambiguityReplyId";

    private Long ambiguityReplyId;

    private Long ambiguityId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String ambiguityReplyContent;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getAmbiguityReplyId() {
        return this.ambiguityReplyId;
    }

    public void setAmbiguityReplyId(Long ambiguityReplyId) {
        this.ambiguityReplyId = ambiguityReplyId;
    }

    public Long getAmbiguityId() {
        return this.ambiguityId;
    }

    public void setAmbiguityId(Long ambiguityId) {
        this.ambiguityId = ambiguityId;
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

    public String getAmbiguityReplyContent() {
        return this.ambiguityReplyContent;
    }

    public void setAmbiguityReplyContent(String ambiguityReplyContent) {
        this.ambiguityReplyContent = ambiguityReplyContent;
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
        if (!(o instanceof AmbiguityReply))
            return false;
        AmbiguityReply bean = (AmbiguityReply) o;
        if (ambiguityReplyId.equals(bean.getAmbiguityReplyId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return ambiguityReplyId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("ambiguityReplyId=" + ambiguityReplyId + ",");
        buffer.append("ambiguityId=" + ambiguityId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("ambiguityReplyContent=" + ambiguityReplyContent);
        buffer.append("]");
        return buffer.toString();
    }
}
