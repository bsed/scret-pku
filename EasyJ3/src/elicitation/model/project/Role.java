package elicitation.model.project;

import java.sql.Date;

public class Role {
	private int roleId;
	
	private String roleName;
	private String roleDes;	
	private int creatorId;	
	private int projectId;
	////////////////////////////////////////////
	private Date buildTime;
	private Date updateTime;

	private float voteRate;
	public Role(){
		
	}
	public Role(int rid){
		this.roleId = rid;
	}
	public Role(String name, String des, int cid, int pid) {
		this.roleName = name;
		this.roleDes = des;
		this.creatorId = cid;
		this.projectId = pid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setRoleId(int rid){
		this.roleId = rid;
	}
	public int getRoleId() {
		return roleId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;		
	}
	public int getProjectId(){
		return projectId;
	}
	
	
	public void setRoleDes(String des){
		this.roleDes = des;
	}
	public String getRoleDes(){
		return this.roleDes;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public Date getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Role){
			Role robj = (Role)obj;
			return robj.roleId == this.roleId;
		}
		return false;
	}
	public int hashCode(){
		return Integer.valueOf(roleId).hashCode();
	}
	public String toString(){
		return roleId+" "+roleName+" "+roleDes;
	}
	public String getVoteRate(){
		return (voteRate*100) + "%";
	}
	public void setVoteRate(float vr) {
		this.voteRate = vr;
	}
	private boolean editPermission  =false;
	public void setEditPermission(boolean ep){
		editPermission = ep;
	}
	public boolean isEditPermission(){
		return editPermission;
	}
}
