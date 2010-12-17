package elicitation.action.user;

import java.util.HashMap;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import elicitation.model.domain.Domain;
import elicitation.model.domain.DomainService;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.user.CompositeUser;
import elicitation.model.user.SysUser;
import elicitation.model.user.CompositeUser.UserProjectRole;
import elicitation.service.user.UserRoleRelationService;
/**
 * 
 * @author John
 * 得到UserCenter.jsp所需要的信息
 */
public class UserAction extends ActionSupport implements ModelDriven<CompositeUser>
{
	private CompositeUser model = null;
	public String exe_userCenter() throws Exception{
		List<HashMap> plist = UserRoleRelationService.selectProjects(model.getSysUser());
		for(HashMap<String, Integer> map :plist){
			int pid= map.get("project_id");
			int isCreator = map.get("is_creator");
			int rid = map.get("role_id");
			Project project = ProjectService.selectProject(new Project(pid));
			
			if(isCreator == 1){
				model.addProjectsCreated(project);
			}else{
				Role role = ProjectService.selectRole(new Role(rid));
				model.addProjectsAttended(project, role);
			}
		}
		SysUser user = (SysUser) ActionContext.getContext().getSession().get("user");
		List<Domain> dlist = DomainService.selectDomainList(user);
		model.addDomainCreated(dlist);
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache,must-revalidate"); //保证首页及时刷新.
		return ActionSupport.SUCCESS;
	}

	public CompositeUser getModel() {
		if(model ==null) model = new CompositeUser();
		return model;
	}
}
