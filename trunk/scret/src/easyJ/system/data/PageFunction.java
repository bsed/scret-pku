package easyJ.system.data;

public class PageFunction implements java.io.Serializable {
    public static final String TABLE_NAME = "page_function";

    public static final String VIEW_NAME = "v_page_function";

    public static final String PRIMARY_KEY = "functionId";

    public static final String CONTEXT_ROLE_PROPERTY = "functionTypeRelatedValue";

    private Long functionId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String functionDisplayValue;

    private String functionName;

    private String functionImage;

    private String functionClass;

    private Long functionPosition;

    private String rightGroup;

    private String functionPage;

    private String functionContent;

    private String returnPage;

    private String userGroups;

    private String users;

    private Long functionTypeId;

    private String functionCondition;

    private String functionPositionValue;

    private Long functionPositionRelatedValue;

    private String functionTypeValue;

    private Long functionTypeRelatedValue;

    public static Boolean isUpdatable(String property) {
        if ("functionPositionValue".equals(property))
            return new Boolean(false);
        if ("functionPositionRelatedValue".equals(property))
            return new Boolean(false);
        if ("functionTypeValue".equals(property))
            return new Boolean(false);
        if ("functionTypeRelatedValue".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getFunctionId() {
        return this.functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
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

    public String getFunctionDisplayValue() {
        return this.functionDisplayValue;
    }

    public void setFunctionDisplayValue(String functionDisplayValue) {
        this.functionDisplayValue = functionDisplayValue;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionImage() {
        return this.functionImage;
    }

    public void setFunctionImage(String functionImage) {
        this.functionImage = functionImage;
    }

    public String getFunctionClass() {
        return this.functionClass;
    }

    public void setFunctionClass(String functionClass) {
        this.functionClass = functionClass;
    }

    public Long getFunctionPosition() {
        return this.functionPosition;
    }

    public void setFunctionPosition(Long functionPosition) {
        this.functionPosition = functionPosition;
    }

    public String getRightGroup() {
        return this.rightGroup;
    }

    public void setRightGroup(String rightGroup) {
        this.rightGroup = rightGroup;
    }

    public String getFunctionPage() {
        return this.functionPage;
    }

    public void setFunctionPage(String functionPage) {
        this.functionPage = functionPage;
    }

    public String getFunctionContent() {
        return this.functionContent;
    }

    public void setFunctionContent(String functionContent) {
        this.functionContent = functionContent;
    }

    public String getReturnPage() {
        return this.returnPage;
    }

    public void setReturnPage(String returnPage) {
        this.returnPage = returnPage;
    }

    public String getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(String userGroups) {
        this.userGroups = userGroups;
    }

    public String getUsers() {
        return this.users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public Long getFunctionTypeId() {
        return this.functionTypeId;
    }

    public void setFunctionTypeId(Long functionTypeId) {
        this.functionTypeId = functionTypeId;
    }

    public String getFunctionCondition() {
        return this.functionCondition;
    }

    public void setFunctionCondition(String functionCondition) {
        this.functionCondition = functionCondition;
    }

    public String getFunctionPositionValue() {
        return this.functionPositionValue;
    }

    public void setFunctionPositionValue(String functionPositionValue) {
        this.functionPositionValue = functionPositionValue;
    }

    public Long getFunctionPositionRelatedValue() {
        return this.functionPositionRelatedValue;
    }

    public void setFunctionPositionRelatedValue(
            Long functionPositionRelatedValue) {
        this.functionPositionRelatedValue = functionPositionRelatedValue;
    }

    public String getFunctionTypeValue() {
        return this.functionTypeValue;
    }

    public void setFunctionTypeValue(String functionTypeValue) {
        this.functionTypeValue = functionTypeValue;
    }

    public Long getFunctionTypeRelatedValue() {
        return this.functionTypeRelatedValue;
    }

    public void setFunctionTypeRelatedValue(Long functionTypeRelatedValue) {
        this.functionTypeRelatedValue = functionTypeRelatedValue;
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
        if (!(o instanceof PageFunction))
            return false;
        PageFunction bean = (PageFunction) o;
        if (functionId.equals(bean.getFunctionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return functionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("functionId=" + functionId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("functionDisplayValue=" + functionDisplayValue + ",");
        buffer.append("functionName=" + functionName + ",");
        buffer.append("functionImage=" + functionImage + ",");
        buffer.append("functionClass=" + functionClass + ",");
        buffer.append("functionPosition=" + functionPosition + ",");
        buffer.append("rightGroup=" + rightGroup + ",");
        buffer.append("functionPage=" + functionPage + ",");
        buffer.append("functionContent=" + functionContent + ",");
        buffer.append("returnPage=" + returnPage + ",");
        buffer.append("userGroups=" + userGroups + ",");
        buffer.append("users=" + users + ",");
        buffer.append("functionTypeId=" + functionTypeId + ",");
        buffer.append("functionCondition=" + functionCondition + ",");
        buffer.append("functionPositionValue=" + functionPositionValue + ",");
        buffer.append("functionPositionRelatedValue="
                + functionPositionRelatedValue + ",");
        buffer.append("functionTypeValue=" + functionTypeValue + ",");
        buffer.append("functionTypeRelatedValue=" + functionTypeRelatedValue);
        buffer.append("]");
        return buffer.toString();
    }
}
