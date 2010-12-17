package elicitation.model.request;

import java.util.ArrayList;
import java.util.List;

import elicitation.model.project.Project;
import elicitation.model.project.Role;
import elicitation.model.project.Scenario;
import elicitation.model.user.SysUser;

/**
 * 
 * @author John
 * 用户发出的请求，例如请求加入场景
 * TODO: 目前按照一次请求只能申请一个角色来处理，简化处理，后期会有改进
 */
public class Request {
	private int id ;
	private SysUser user;  // user send the request! 
	private String description;
	private Project project;
	private List<Role> roles = new ArrayList<Role>();
	public Request(){
		
	}
	public void setRequestUser(SysUser user){
		this.user = user;
	}
	public SysUser getRequestUser(){
		return this.user;
	}
	public int getRequestId(){
		return id;
	}
	public void setRequestId(int id){
		this.id = id;
	}
	public void setRequestProject(Project sce){
		this.project = sce;
	}
	public Project getRequestProject(){
		return this.project;
	}
	public String getRequestDescription(){
		return description;
	}
	public void setRequestDescription(String des){
		this.description = des;
	}
	public void addRequestRole(Role role){
		if(roles.contains(role)) return ;
		roles.add(role);
	}
	public void setRequestRoleList(List<Role> para){
		this.roles  =para;
	}
	public void addRequestRole(List<Role> para){
		if(para == null)return;
		for(Role role:para){
			addRequestRole(role);
		}
	}
	public List<Role> getRequestRoles(){
		return roles;
	}
	
	/************************For Ibatis ResultMap***************************/
	public void setProjectId(int id){
		if(project == null) project = new Project();
		project.setProjectId(id);
	}
	public int getProjectId(){
		return project.getProjectId();
	}
	public void setRoleId(int id){
		Role role = new Role();
		role.setRoleId(id);
		addRequestRole(role);
	}
	public void setUserId(int id){
		if(user  == null) user = new SysUser();
		user.setUserId(id);
	}
	public int getUserId(){
		return user.getUserId();
	}
	public int getRoleId(){
		return roles.get(0).getRoleId();
	}
	
}
