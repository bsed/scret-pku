package easyJ.system.data;

public class Property implements java.io.Serializable {
    public static final String TABLE_NAME = "property";

    public static final String VIEW_NAME = "v_property";

    public static final String PRIMARY_KEY = "propertyId";

    public static final String ID_PROPERTY = "propertyId";

    public static final String DISPLAY_PROPERTY = "propertyChiName";

    private Long classId;

    private Long propertyId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private String propertyName;

    private String propertyChiName;

    private Integer showWidth;

    private Integer showHeigth;

    private String validateRule;

    private String scopeQuery;

    private String propertyValueTable;

    private String type;

    private Short sequence;

    private Boolean fromDictionary;

    private String showType;

    private String showProperty;

    private String hidden;

    private String description;

    private String className;

    private String classChiName;

    public static Boolean isUpdatable(String property) {
        if ("className".equals(property))
            return new Boolean(false);
        if ("classChiName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getClassId() {
        return this.classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
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

    public String getPropertyValueTable() {
        return this.propertyValueTable;
    }

    public void setPropertyValueTable(String propertyValueTable) {
        this.propertyValueTable = propertyValueTable;
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

    public String getHidden() {
        return this.hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof Property))
            return false;
        Property bean = (Property) o;
        if (propertyId.equals(bean.getPropertyId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return propertyId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("classId=" + classId + ",");
        buffer.append("propertyId=" + propertyId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("propertyName=" + propertyName + ",");
        buffer.append("propertyChiName=" + propertyChiName + ",");
        buffer.append("showWidth=" + showWidth + ",");
        buffer.append("showHeigth=" + showHeigth + ",");
        buffer.append("validateRule=" + validateRule + ",");
        buffer.append("scopeQuery=" + scopeQuery + ",");
        buffer.append("propertyValueTable=" + propertyValueTable + ",");
        buffer.append("type=" + type + ",");
        buffer.append("sequence=" + sequence + ",");
        buffer.append("fromDictionary=" + fromDictionary + ",");
        buffer.append("showType=" + showType + ",");
        buffer.append("showProperty=" + showProperty + ",");
        buffer.append("hidden=" + hidden + ",");
        buffer.append("description=" + description + ",");
        buffer.append("className=" + className + ",");
        buffer.append("classChiName=" + classChiName);
        buffer.append("]");
        return buffer.toString();
    }
}
