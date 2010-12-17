package elicitation.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import elicitation.model.domain.Domain;
import elicitation.model.project.Project;
import elicitation.model.project.Role;
import elicitation.model.request.Request;

public class CompositeUser implements Serializable{
	private SysUser suser ;	
	private List<UserProjectRole> projectsRelated = new ArrayList<UserProjectRole>();
	/*
	 * 没有设立 domainRelated.在UserCenter只展示用户自己创建的领域
	 * */
	private List<Domain> domains = new ArrayList<Domain>();	
	public CompositeUser(){
		
	}
	public void setUserId(int uid){
		if(suser != null){
			suser.setUserId(uid);
		}else{
			suser = new SysUser();
			suser.setUserId(uid);
		}
	}
//	public void setSysUser(SysUser para){
//		this.suser = para;
//	}
	public SysUser getSysUser(){
		return this.suser;
	}
		
	/**
     * add/delete/select/update
     * @param para
     */
    public void addProjectsCreated(Project para){
    	if(para == null) return;
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.project.getProjectId() == para.getProjectId()){
    			return;
    		}
    	}
    	projectsRelated.add(new UserProjectRole(suser.getUserId(),para,1));    	
    }    
    public void addDomainCreated(Domain para){
    	if(para == null) return ;
    	for(Domain domain:domains){
    		if(domain.getDomainId() == para.getDomainId())
    			return ;
    	}
    	domains.add(para);
    }
    public List<Domain> getDomainCreated(){
    	return domains;
    }
//    public Project selectProjectsCreated(Project para){
//    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
//    	while(ite.hasNext()){
//    		UserProjectRole upr = ite.next();
//    		if(upr.project.getProjectId() == para.getProjectId()
//    				&&upr.isCreator==1)
//    			return upr.project;
//    	}
//    	return null;
//    }
    public List<Project> getProjectsCreated(){
    	List<Project> ps = new ArrayList<Project>();
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.isCreator == 1)
    			ps.add(upr.project);
    	}
    	return ps;
    }
    private List<Request> receive_requests = new ArrayList<Request>();
    public void setRequestReceived(List<Request> res){
    	if(res == null)return;
    	receive_requests = res;
    }
    public List<Request> getRequestReceived(){
    	return receive_requests;
    }
   /* public void deleteProjectsCreated(Project para){
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.project.getProjectId() == para.getProjectId())
    			projectsRelated.remove(upr);
    	}
    }*/
    public void addProjectsAttended(Project para,Role role){
    	if(para == null || role == null) return;
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.project.getProjectId() == para.getProjectId()){
    			upr.roles.add(role);
    			return ;
    		}
    	}  
    	UserProjectRole upr = new UserProjectRole((int)suser.getUserId(),para,0);
    	upr.roles.add(role);
    	projectsRelated.add(upr);    	
    }
    /*public UserProjectRole selectProjectsAttended(Project para){
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.project.getProjectId() == para.getProjectId() && upr.isCreator == 0){
    			return upr;
    		}
    	}  
    	return null;
    }*/
   public List<UserProjectRole> getProjectsAttended(){
    	List<UserProjectRole> attends = new ArrayList<UserProjectRole>();
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.isCreator == 0)
    			attends.add(upr);
    	}
    	return attends;
    }
    /*public void deleteProjectsAttended(Project para,Role pararole){
    	Iterator<UserProjectRole> ite = projectsRelated.iterator();
    	while(ite.hasNext()){
    		UserProjectRole upr = ite.next();
    		if(upr.isCreator == 0 && 
    				upr.roles.contains(pararole)){
    			upr.roles.remove(pararole);
    			// if user has no role,then remove it completely.
    			if(upr.roles.size() == 0)    				
    				projectsRelated.remove(upr);
    		}	
    	}
    }*/
	 /**
     * Aggregate Class.
     * Refer to Table user_project_role.
     * @author John
     *
     */
    public class UserProjectRole{
    	public int userId;
    	public Project project;
    	// the roles the user possessed in the project.
    	public List<Role> roles = new ArrayList<Role>(); 
    	public int isCreator = 0 ; 
    	public UserProjectRole(){
    	}	
    	public UserProjectRole(long uid,Project project,int isCreator){
    		this.userId = (int)uid;
    		this.project = project;
    		this.isCreator = isCreator;
    	}
    	public UserProjectRole(int uid, Project project,int isCreator){
    		this.userId = uid;
    		this.project = project;
    		this.isCreator = isCreator;
    	}
    	public Project getProject(){
    		return project;
    	}
    	public List<Role> getRoles(){
    		return roles;
    	}
    }
	public void addDomainCreated(List<Domain> dlist) {
		for(Domain dom:dlist){
			addDomainCreated(dom);
		}
	}
}
