package easyJ.tools;

public class Column implements java.io.Serializable {
    private String tableName;

    private Integer columnSequence;

    private String columnName;

    private String identity;

    private String isPrimaryKey;

    private String type;

    private Integer size;

    private Integer length;

    private Integer decimalFractionNum;

    private String isNullAllowed;

    private String defaultValue;

    private String comment;

    public static String VIEW_NAME = "V_TABLE_INFO";

    public static String PRIMARY_KEY = "a";

    public Column() {}

    public static String getSubClass(String property) {
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDecimalFractionNum() {
        return decimalFractionNum;
    }

    public void setDecimalFractionNum(Integer decimalFractionNum) {
        this.decimalFractionNum = decimalFractionNum;
    }

    public String getIsNullAllowed() {
        return isNullAllowed;
    }

    public void setIsNullAllowed(String isNullAllowed) {
        this.isNullAllowed = isNullAllowed;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Column))
            return false;
        Column column = (Column) o;
        if (this.columnName.equals(column.getColumnName()))
            return true;
        return false;
    }
}
