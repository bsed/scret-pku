package test;

public class Department implements java.io.Serializable {
    public static final String TABLE_NAME = "department";

    public static final String VIEW_NAME = "department";

    public static final String PRIMARY_KEY = "deptId";

    public static final String TREE_ID_PROPERTY = "deptId";

    public static final String TREE_PARENT_ID_PROPERTY = "depDeptId";

    public static final String TREE_DISPLAY_PROPERTY = "deptName";

    private String deptName;

    private Long depDeptId;

    private Long deptId;

    public boolean isUpdatable(String property) {
        return true;
    }

    public Long getDeptId() {
        return this.deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDepDeptId() {
        return this.depDeptId;
    }

    public void setDepDeptId(Long depDeptId) {
        this.depDeptId = depDeptId;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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
        if (!(o instanceof Department))
            return false;
        Department bean = (Department) o;
        if (deptId.equals(bean.getDeptId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return deptId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("deptId=" + deptId + ",");
        buffer.append("depDeptId=" + depDeptId + ",");
        buffer.append("deptName=" + deptName);
        buffer.append("]");
        return buffer.toString();
    }
}
