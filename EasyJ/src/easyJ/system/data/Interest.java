package easyJ.system.data;

public class Interest implements java.io.Serializable {
    public static final String TABLE_NAME = "interest";

    public static final String VIEW_NAME = "interest";

    public static final String PRIMARY_KEY = "interestId";

    private Long interestId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String functionAddress;

    private String title;

    private Integer dataRows;

    private String columns;

    private String className;

    private String patternPosition;

    private String condition;

    private String orderbyColumns;

    private String method;

    private String userGroups;

    private String users;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getInterestId() {
        return this.interestId;
    }

    public void setInterestId(Long interestId) {
        this.interestId = interestId;
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

    public String getFunctionAddress() {
        return this.functionAddress;
    }

    public void setFunctionAddress(String functionAddress) {
        this.functionAddress = functionAddress;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDataRows() {
        return this.dataRows;
    }

    public void setDataRows(Integer dataRows) {
        this.dataRows = dataRows;
    }

    public String getColumns() {
        return this.columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPatternPosition() {
        return this.patternPosition;
    }

    public void setPatternPosition(String patternPosition) {
        this.patternPosition = patternPosition;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrderbyColumns() {
        return this.orderbyColumns;
    }

    public void setOrderbyColumns(String orderbyColumns) {
        this.orderbyColumns = orderbyColumns;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
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
        if (!(o instanceof Interest))
            return false;
        Interest bean = (Interest) o;
        if (interestId.equals(bean.getInterestId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return interestId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("interestId=" + interestId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("functionAddress=" + functionAddress + ",");
        buffer.append("title=" + title + ",");
        buffer.append("dataRows=" + dataRows + ",");
        buffer.append("columns=" + columns + ",");
        buffer.append("className=" + className + ",");
        buffer.append("patternPosition=" + patternPosition + ",");
        buffer.append("condition=" + condition + ",");
        buffer.append("orderbyColumns=" + orderbyColumns + ",");
        buffer.append("method=" + method + ",");
        buffer.append("userGroups=" + userGroups + ",");
        buffer.append("users=" + users);
        buffer.append("]");
        return buffer.toString();
    }
}
