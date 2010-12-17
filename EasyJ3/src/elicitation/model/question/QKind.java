package elicitation.model.question;

import elicitation.model.project.Project;
import elicitation.model.user.SysUser;

/**
 * 问题类别，允许用户自定义类别
 * 不同的类别针对不同的项目
 * 预定义的类别针对所有的工程.
 * */
public class QKind {
	private int id;
	private String name;
	private String description;
	private SysUser creator;
	private Project project;// belong to the project.
	
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public SysUser getCreator() {
		return creator;
	}
	public void setCreator(SysUser creator) {
		this.creator = creator;
	}
	public QKind(){		
	}
	public QKind(int id){
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/***************/
	public int getProjectId(){
		return project.getProjectId();
	}
	public void setProjectId(int id){
		if(project == null) project = new Project();
		project.setProjectId(id);
	}
	public int getCreatorId(){
		return creator.getUserId();
	}
	public void setCreatorId(int id){
		if(creator == null) creator = new SysUser();
		creator.setUserId(id);
	}
}
