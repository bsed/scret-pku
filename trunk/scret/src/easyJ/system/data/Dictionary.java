package easyJ.system.data;

public class Dictionary implements java.io.Serializable {
    public static final String TABLE_NAME = "dictionary";

    public static final String VIEW_NAME = "dictionary";

    public static final String PRIMARY_KEY = "dicValueId";

    public static final String TREE_ID_PROPERTY = "dicValueId";

    public static final String TREE_PARENT_ID_PROPERTY = "parentId";

    public static final String TREE_DISPLAY_PROPERTY = "dicValueName";
    
    public static final String ID_PROPERTY = "dicValueId";
    
    public static final String DISPLAY_PROPERTY = "dicValueName";

    public static final String TREE_FUNCTION_PROPERTY = "dicValueId";
    

    private String dicType;

    private String dicValueName;

    private String dicRight;

    private String useState;

    private Integer dicSequence;

    private String showType;

    private Long dicValueId;

    private Long parentId;

    private Long relatedValue;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getDicValueId() {
        return this.dicValueId;
    }

    public void setDicValueId(Long dicValueId) {
        this.dicValueId = dicValueId;
    }

    public String getDicType() {
        return this.dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDicValueName() {
        return this.dicValueName;
    }

    public void setDicValueName(String dicValueName) {
        this.dicValueName = dicValueName;
    }

    public Long getRelatedValue() {
        return this.relatedValue;
    }

    public void setRelatedValue(Long relatedValue) {
        this.relatedValue = relatedValue;
    }

    public String getDicRight() {
        return this.dicRight;
    }

    public void setDicRight(String dicRight) {
        this.dicRight = dicRight;
    }

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public Integer getDicSequence() {
        return this.dicSequence;
    }

    public void setDicSequence(Integer dicSequence) {
        this.dicSequence = dicSequence;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
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
        if (!(o instanceof Dictionary))
            return false;
        Dictionary bean = (Dictionary) o;
        if (dicValueId.equals(bean.getDicValueId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return dicValueId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("dicValueId=" + dicValueId + ",");
        buffer.append("dicTypeId=" + dicType + ",");
        buffer.append("parentId=" + parentId + ",");
        buffer.append("dicValueName=" + dicValueName + ",");
        buffer.append("relatedValue=" + relatedValue + ",");
        buffer.append("dicRight=" + dicRight + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("dicSequence=" + dicSequence + ",");
        buffer.append("showType=" + showType);
        buffer.append("]");
        return buffer.toString();
    }
}
