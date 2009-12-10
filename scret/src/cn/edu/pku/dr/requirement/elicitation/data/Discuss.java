package cn.edu.pku.dr.requirement.elicitation.data;

public class Discuss implements java.io.Serializable {
    public static final String TABLE_NAME = "discuss";

    public static final String VIEW_NAME = "v_discuss";

    public static final String PRIMARY_KEY = "discussId";

    public static final String TREE_ID_PROPERTY = "discussId";

    public static final String TREE_PARENT_ID_PROPERTY = "discussParentId";

    public static final String TREE_DISPLAY_PROPERTY = "discussTitle";

    public static final String TREE_FUNCTION_PROPERTY = "";

    public static final String EXPAND_PROPERTY = "discussContent";

    private Long discussId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String discussTitle;

    private String discussContent;

    private String discussType;

    private Long discussSourceId;

    private Long discussParentId;

    private String anonymous;

    private Long discussTopicId;

    private String userName;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getDiscussId() {
        return this.discussId;
    }

    public void setDiscussId(Long discussId) {
        this.discussId = discussId;
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

    public String getDiscussTitle() {
        return this.discussTitle;
    }

    public void setDiscussTitle(String discussTitle) {
        this.discussTitle = discussTitle;
    }

    public String getDiscussContent() {
        return this.discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }

    public String getDiscussType() {
        return this.discussType;
    }

    public void setDiscussType(String discussType) {
        this.discussType = discussType;
    }

    public Long getDiscussSourceId() {
        return this.discussSourceId;
    }

    public void setDiscussSourceId(Long discussSourceId) {
        this.discussSourceId = discussSourceId;
    }

    public Long getDiscussParentId() {
        return this.discussParentId;
    }

    public void setDiscussParentId(Long discussParentId) {
        this.discussParentId = discussParentId;
    }

    public String getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public Long getDiscussTopicId() {
        return this.discussTopicId;
    }

    public void setDiscussTopicId(Long discussTopicId) {
        this.discussTopicId = discussTopicId;
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
        if (!(o instanceof Discuss))
            return false;
        Discuss bean = (Discuss) o;
        if (discussId.equals(bean.getDiscussId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return discussId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("discussId=" + discussId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("discussTitle=" + discussTitle + ",");
        buffer.append("discussContent=" + discussContent + ",");
        buffer.append("discussType=" + discussType + ",");
        buffer.append("discussSourceId=" + discussSourceId + ",");
        buffer.append("discussParentId=" + discussParentId + ",");
        buffer.append("anonymous=" + anonymous + ",");
        buffer.append("discussTopicId=" + discussTopicId + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }
}
