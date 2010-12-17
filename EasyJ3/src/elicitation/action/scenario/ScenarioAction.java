package elicitation.action.scenario;

import java.sql.SQLException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Scenario;
import elicitation.model.project.Data;
import elicitation.model.project.Role;
import elicitation.model.project.VersionService;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;
import elicitation.model.user.SysUser;

import java.util.List;
public class ScenarioAction extends ActionSupport implements ModelDriven<Scenario>{
	private Scenario scenario = null;
	
	public String exe_getScenarioDetail() throws SQLException{
		/**
		 * 根据ID，获取场景信息，包括use_state{draft,version}
		 */
		scenario = ProjectService.selectScenario(scenario);
		List<Data> ds = ProjectService.selectDataList(scenario);
		
		scenario.addData(ds);
		
		List<Role> rs = ProjectService.selectRoleList(scenario);
	
		scenario.addRole(rs);
		//TODO select role's description. 
		ProjectService.selectScenarioRoleDes(scenario);		
		/**
		 * 权限控制部分
		 */
		ActionContext ctx =ActionContext.getContext(); 
		SysUser user = (SysUser) ctx.getSession().get("user");
		scenario.solvePermission(user);		
		return ActionSupport.SUCCESS;
	}


	public Scenario getModel() {
		if(scenario == null) scenario = new Scenario();
		return scenario;
	}
	
}
