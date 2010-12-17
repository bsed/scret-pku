package elicitation.model.review;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import elicitation.model.project.Role;
import elicitation.model.user.SysUser;
import elicitation.service.user.UserService;


/**
 * 
 * @author John
 * 
 * 相关者对现状场景的评论.
 */
public class Review {
	private int id;
	private int userId;
	private Date time;
	private String content;
	private int scenarioId;
	private List<Role> roles = new ArrayList<Role>();
	public Review(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(int scenarioId) {
		this.scenarioId = scenarioId;
	}
	public String toString(){
		return id+" "+userId+" "+time+" "+content+" "+scenarioId;
	}
	/***
	 * for struts info display.
	 * @return
	 */
	public SysUser getUser(){
		SysUser user = new SysUser();
		user.setUserId(userId);
		return UserService.selectUserByID(user);
	}
	public void addRole(Role role){
		roles.add(role);
	}
	public List<Role> getRoles(){
		return roles;
	}
}
