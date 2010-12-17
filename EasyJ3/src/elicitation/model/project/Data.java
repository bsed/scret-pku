package elicitation.model.project;

import java.sql.Date;

public class Data {
	private int dataId;
	private String dataName;
	private String dataDes;
	private int creatorId;
	private int projectId;//方便与数据库表匹配
	private Date buildTime;
	private Date updateTime;
	public Data(){
		
	}
	public Data(String name,String des,int cid,int pid){
		this.dataDes = des;
		this.dataName = name;
		this.creatorId = cid;
		this.projectId = pid;
	}
	public int getDataId(){
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getProjectId(){
		return projectId;
	}
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataDes() {
		return dataDes;
	}

	public void setDataDes(String dataDes) {
		this.dataDes = dataDes;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	


}
