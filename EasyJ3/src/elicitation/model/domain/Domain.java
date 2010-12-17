package elicitation.model.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

import elicitation.model.project.Project;

public class Domain {
	private int domainId;
	private String domainName;
	private String domainDes;
	private int creatorId;
	private Date buildTime ;
	private Date updateTime;
	private ArrayList<Project> projects = new ArrayList<Project>();
	public Domain(){
		
	}
	public Domain(String name,String des,int cid){
		this.domainName = name;
		this.domainDes = des;
		this.creatorId = cid;
	}
	public Domain(int id,String name,String des,int cid){
		this.domainId = id;
		this.domainName = name;
		this.domainDes = des;
		this.creatorId = cid;
	}
	public ArrayList<Project> getProjects(){
		return projects;
	}
	public Project selectProject(int id){
		Iterator<Project> ite = projects.iterator();
		while(ite.hasNext()){
			Project pro = ite.next();
			if(pro.getProjectId() == id)
				return pro;
		}
		return null;
	}
	public void addProject(Project pro){
		if(projects.contains(pro)) return;
		projects.add(pro);
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainDes() {
		return domainDes;
	}
	public void setDomainDes(String domainDes) {
		this.domainDes = domainDes;
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
	public double getRand(){
		return Math.random();
	}
}

