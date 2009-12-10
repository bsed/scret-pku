package easyJ.system.data;

public class UserGroup implements java.io.Serializable {
    public static final String TABLE_NAME = "user_group";

    public static final String VIEW_NAME = "user_group";

    public static final String PRIMARY_KEY = "userGroupId";

    public static final String ID_PROPERTY = "userGroupId";

    public static final String DISPLAY_PROPERTY = "userGroupName";

    private Long userGroupId;

    private String userGroupName;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getUserGroupId() {
        return this.userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return this.userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
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
        if (!(o instanceof UserGroup))
            return false;
        UserGroup bean = (UserGroup) o;
        if (userGroupId.equals(bean.getUserGroupId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return userGroupId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("userGroupId=" + userGroupId + ",");
        buffer.append("userGroupName=" + userGroupName);
        buffer.append("]");
        return buffer.toString();
    }
}
