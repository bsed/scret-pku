package easyJ.system.data;

public class UserPropertyRight implements java.io.Serializable {
    public static final String TABLE_NAME = "user_property_right";

    public static final String VIEW_NAME = "v_user_property_right";

    public static final String PRIMARY_KEY = "userPropertyId";

    private Long userPropertyId;

    private Long propertyId;

    private Long userId;

    private String whetherEdit;

    private String whetherDisplay;

    private String whetherQuery;

    private Short editSequence;

    private Short displaySequence;

    private Short querySequence;

    private String userName;

    private String hidden;

    private String propertyName;

    private String propertyChiName;

    private Integer showWidth;

    private Integer showHeigth;

    private String validateRule;

    private String scopeQuery;

    private String showType;

    private String showProperty;

    private String propertyValueTable;

    private String description;

    private String type;

    private Short sequence;

    private Boolean fromDictionary;

    private String className;

    private String classChiName;

    public static Boolean isUpdatable(String property) {
        if ("userName".equals(property))
            return new Boolean(false);
        if ("hidden".equals(property))
            return new Boolean(false);
        if ("propertyName".equals(property))
            return new Boolean(false);
        if ("propertyChiName".equals(property))
            return new Boolean(false);
        if ("showWidth".equals(property))
            return new Boolean(false);
        if ("showHeigth".equals(property))
            return new Boolean(false);
        if ("validateRule".equals(property))
            return new Boolean(false);
        if ("scopeQuery".equals(property))
            return new Boolean(false);
        if ("showType".equals(property))
            return new Boolean(false);
        if ("showProperty".equals(property))
            return new Boolean(false);
        if ("propertyValueTable".equals(property))
            return new Boolean(false);
        if ("description".equals(property))
            return new Boolean(false);
        if ("type".equals(property))
            return new Boolean(false);
        if ("sequence".equals(property))
            return new Boolean(false);
        if ("fromDictionary".equals(property))
            return new Boolean(false);
        if ("className".equals(property))
            return new Boolean(false);
        if ("classChiName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getUserPropertyId() {
        return this.userPropertyId;
    }

    public void setUserPropertyId(Long userPropertyId) {
        this.userPropertyId = userPropertyId;
    }

    public Long getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWhetherEdit() {
        return this.whetherEdit;
    }

    public void setWhetherEdit(String whetherEdit) {
        this.whetherEdit = whetherEdit;
    }

    public String getWhetherDisplay() {
        return this.whetherDisplay;
    }

    public void setWhetherDisplay(String whetherDisplay) {
        this.whetherDisplay = whetherDisplay;
    }

    public String getWhetherQuery() {
        return this.whetherQuery;
    }

    public void setWhetherQuery(String whetherQuery) {
        this.whetherQuery = whetherQuery;
    }

    public Short getEditSequence() {
        return this.editSequence;
    }

    public void setEditSequence(Short editSequence) {
        this.editSequence = editSequence;
    }

    public Short getDisplaySequence() {
        return this.displaySequence;
    }

    public void setDisplaySequence(Short displaySequence) {
        this.displaySequence = displaySequence;
    }

    public Short getQuerySequence() {
        return this.querySequence;
    }

    public void setQuerySequence(Short querySequence) {
        this.querySequence = querySequence;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHidden() {
        return this.hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyChiName() {
        return this.propertyChiName;
    }

    public void setPropertyChiName(String propertyChiName) {
        this.propertyChiName = propertyChiName;
    }

    public Integer getShowWidth() {
        return this.showWidth;
    }

    public void setShowWidth(Integer showWidth) {
        this.showWidth = showWidth;
    }

    public Integer getShowHeigth() {
        return this.showHeigth;
    }

    public void setShowHeigth(Integer showHeigth) {
        this.showHeigth = showHeigth;
    }

    public String getValidateRule() {
        return this.validateRule;
    }

    public void setValidateRule(String validateRule) {
        this.validateRule = validateRule;
    }

    public String getScopeQuery() {
        return this.scopeQuery;
    }

    public void setScopeQuery(String scopeQuery) {
        this.scopeQuery = scopeQuery;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getShowProperty() {
        return this.showProperty;
    }

    public void setShowProperty(String showProperty) {
        this.showProperty = showProperty;
    }

    public String getPropertyValueTable() {
        return this.propertyValueTable;
    }

    public void setPropertyValueTable(String propertyValueTable) {
        this.propertyValueTable = propertyValueTable;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getSequence() {
        return this.sequence;
    }

    public void setSequence(Short sequence) {
        this.sequence = sequence;
    }

    public Boolean getFromDictionary() {
        return this.fromDictionary;
    }

    public void setFromDictionary(Boolean fromDictionary) {
        this.fromDictionary = fromDictionary;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassChiName() {
        return this.classChiName;
    }

    public void setClassChiName(String classChiName) {
        this.classChiName = classChiName;
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
        if (!(o instanceof UserPropertyRight))
            return false;
        UserPropertyRight bean = (UserPropertyRight) o;
        if (userPropertyId.equals(bean.getUserPropertyId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return userPropertyId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("userPropertyId=" + userPropertyId + ",");
        buffer.append("propertyId=" + propertyId + ",");
        buffer.append("userId=" + userId + ",");
        buffer.append("whetherEdit=" + whetherEdit + ",");
        buffer.append("whetherDisplay=" + whetherDisplay + ",");
        buffer.append("whetherQuery=" + whetherQuery + ",");
        buffer.append("editSequence=" + editSequence + ",");
        buffer.append("displaySequence=" + displaySequence + ",");
        buffer.append("querySequence=" + querySequence + ",");
        buffer.append("userName=" + userName + ",");
        buffer.append("hidden=" + hidden + ",");
        buffer.append("propertyName=" + propertyName + ",");
        buffer.append("propertyChiName=" + propertyChiName + ",");
        buffer.append("showWidth=" + showWidth + ",");
        buffer.append("showHeigth=" + showHeigth + ",");
        buffer.append("validateRule=" + validateRule + ",");
        buffer.append("scopeQuery=" + scopeQuery + ",");
        buffer.append("showType=" + showType + ",");
        buffer.append("showProperty=" + showProperty + ",");
        buffer.append("propertyValueTable=" + propertyValueTable + ",");
        buffer.append("description=" + description + ",");
        buffer.append("type=" + type + ",");
        buffer.append("sequence=" + sequence + ",");
        buffer.append("fromDictionary=" + fromDictionary + ",");
        buffer.append("className=" + className + ",");
        buffer.append("classChiName=" + classChiName);
        buffer.append("]");
        return buffer.toString();
    }
}
