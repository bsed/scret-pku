package elicitation.model.user;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.project.Scenario;
import elicitation.service.user.UserRoleRelationService;
import elicitation.service.user.UserService;
/**
 * 
 * @author John
 * Reserve the basic info of user.
 * Refer to Class CompositeUser.
 */
public class SysUser implements java.io.Serializable {
    public static final String TABLE_NAME = "sys_user";
    public static final String VIEW_NAME = "sys_user";
    public static final String PRIMARY_KEY = "user_id";
    private int userId;
    private String useState="Y";
    private Date buildTime;
    private Date updateTime;
    private String userName;
    private String email;
    private String phone;    
    private String mobile;
    private String password;
    
    ///private List<UserProjectRole> projectsRelated = new ArrayList<UserProjectRole>();
    ///Relation with project.(role-scenarios).
    public SysUser(){    	
    }
    public SysUser(int id){
    	userId = id;
    }
    public SysUser(int id,String name,String passwd) {
    	userId = id;
    
		userName =name;
	
    	this.password = passwd;
    }
    
    public String getState(){
    	return useState;
    }
    public void setState(String state){
    	useState = state;
    }

    public static String getSubClass(String property) {
        return null;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

   

    public Date getBuildTime() {
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

    public String getUserName() {
        return this.userName;
    }
    public String getName(){
    	return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean equals(Object o) {
        if (!(o instanceof SysUser))
            return false;
        SysUser bean = (SysUser) o;
        return bean.userName.equals(userName);        		
    }
    public int hashCode(){
    	return userName.hashCode();
    }
    /**
     * 更细一层次，要判断是不是属于某个 role,这样才能决定显示哪个编辑按钮框. //TODO
     * @param scenario
     * @return
     */
	public boolean isMemberOfScenario(List<Role> p_roles,List<Role> s_roles) {
		for(Role role:p_roles){
			if(s_roles.contains(role))
				return true;
		}
		return false;
	}
	public boolean isMemberOfProject(List<Role> p_roles) {
		if(p_roles == null || p_roles.size() == 0) return false;
		return true;
	}
}