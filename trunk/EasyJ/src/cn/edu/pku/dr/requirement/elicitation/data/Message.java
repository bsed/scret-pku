package cn.edu.pku.dr.requirement.elicitation.data;

public class Message implements java.io.Serializable {
    public static final String TABLE_NAME = "message";

    public static final String VIEW_NAME = "v_message";

    public static final String PRIMARY_KEY = "messageId";

    private Long messageId;

    private Long creatorId;

    private Long messageReceiver;

    private String messageTitle;

    private String messageContent;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private String isRead;

    private Long reMessageId;

    private String messageReceiverName;

    private String userName;

    public static Boolean isUpdatable(String property) {
        if ("messageReceiverName".equals(property))
            return new Boolean(false);
        if ("userName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getMessageReceiver() {
        return this.messageReceiver;
    }

    public void setMessageReceiver(Long messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public String getMessageTitle() {
        return this.messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
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

    public String getIsRead() {
        return this.isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Long getReMessageId() {
        return this.reMessageId;
    }

    public void setReMessageId(Long reMessageId) {
        this.reMessageId = reMessageId;
    }

    public String getMessageReceiverName() {
        return this.messageReceiverName;
    }

    public void setMessageReceiverName(String messageReceiverName) {
        this.messageReceiverName = messageReceiverName;
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
        if (!(o instanceof Message))
            return false;
        Message bean = (Message) o;
        if (messageId.equals(bean.getMessageId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return messageId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("messageId=" + messageId + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("messageReceiver=" + messageReceiver + ",");
        buffer.append("messageTitle=" + messageTitle + ",");
        buffer.append("messageContent=" + messageContent + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("isRead=" + isRead + ",");
        buffer.append("reMessageId=" + reMessageId + ",");
        buffer.append("messageReceiverName=" + messageReceiverName + ",");
        buffer.append("userName=" + userName);
        buffer.append("]");
        return buffer.toString();
    }
}
