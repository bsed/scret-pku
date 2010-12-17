package elicitation.action.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.struts.action.ActionServlet;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import elicitation.model.project.Data;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.project.Scenario;
import elicitation.model.user.SysUser;
import elicitation.utils.Utils;

public class ProjectAction extends ActionSupport implements ModelDriven<Project>{
	
	private Project project = null;//OUT 
	public String exe_getProjectDetail() throws Exception{		
		project = ProjectService.selectProject(project);
		//TODO select dataList and roleList
		// 2010-5-18 至此.
		if(project ==null) return ActionSupport.ERROR;
		List<Data> ds = ProjectService.selectDataList(project);
		project.addData(ds);
		List<Role> rs = ProjectService.selectRoleList(project);
		project.addRole(rs);
		List<Scenario> ss = ProjectService.selectScenarioList(project);
		project.addScenario(ss);
		project.solveScenarioRole();
		/**
		 * 权限控制部分
		 */
		
		ActionContext ctx =ActionContext.getContext(); 
		SysUser user = (SysUser) ctx.getSession().get("user");
		project.solvePermission(user);
		
		if(user!=null){
			project.solveUserRoleList(user);
		}
		return ActionSupport.SUCCESS;
	}
	@Override
	public Project getModel() {
		if(project  == null) project =new Project();
		return project;
	}
}
