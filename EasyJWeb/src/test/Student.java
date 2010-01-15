package test;

import easyJ.common.*;

public class Student implements java.io.Serializable {
    public static final String TABLE_NAME = "student";

    public static final String VIEW_NAME = "student";

    public static final String PRIMARY_KEY = "studentId";

    private Long studentId;

    private String studentName;

    private String studentSex;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSex() {
        return this.studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
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
        if (!(o instanceof Student))
            return false;
        Student bean = (Student) o;
        if (studentId.equals(bean.getStudentId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return studentId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("studentId=" + studentId + ",");
        buffer.append("studentName=" + studentName + ",");
        buffer.append("studentSex=" + studentSex);
        buffer.append("]");
        return buffer.toString();
    }
}
