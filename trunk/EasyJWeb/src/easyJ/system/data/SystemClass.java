package easyJ.system.data;

public class SystemClass implements java.io.Serializable {
    public static final String TABLE_NAME = "system_class";

    public static final String VIEW_NAME = "system_class";

    public static final String PRIMARY_KEY = "classId";
    
    public static final String ID_PROPERTY = "classId";
    
    public static final String DISPLAY_PROPERTY = "classChiName";

    private Long classId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private String classChiName;

    private String className;

    private String securityPolicy;

    public static Boolean isUpdatable(String property) {
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

    public String getClassChiName() {
        return this.classChiName;
    }

    public void setClassChiName(String classChiName) {
        this.classChiName = classChiName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSecurityPolicy() {
        return this.securityPolicy;
    }

    public void setSecurityPolicy(String securityPolicy) {
        this.securityPolicy = securityPolicy;
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
        if (!(o instanceof SystemClass))
            return false;
        SystemClass bean = (SystemClass) o;
        if (classId.equals(bean.getClassId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return classId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("classId=" + classId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("classChiName=" + classChiName + ",");
        buffer.append("className=" + className + ",");
        buffer.append("securityPolicy=" + securityPolicy);
        buffer.append("]");
        return buffer.toString();
    }
}
