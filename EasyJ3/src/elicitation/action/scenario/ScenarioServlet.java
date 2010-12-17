package elicitation.action.scenario;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Data;
import elicitation.model.project.VersionService;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.project.Scenario;
import elicitation.model.solution.SolutionService;
import elicitation.model.user.SysUser;
/**
 * addScenarioServlet.so
 * @author John
 *
 */
public class ScenarioServlet extends HttpServlet {
	/**
	 * Scenario 中包含Data \Role 等，
	 * 在addScenario()函数中一并载入数据库.
	 */
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		String name = (String) request.getParameter("scenarioName");
		String dataList = (String)request.getParameter("dataList");
		String roleList = (String)request.getParameter("roleList");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		//Debug 
		if(user == null) {
			user = new SysUser();
			user.setUserId(98);
		}
		int projectId = Integer.valueOf(request.getParameter("projectId"));
		
		Scenario cur_sce = new Scenario();
		cur_sce.setCreatorId(user.getUserId());
		cur_sce.setProjectId(projectId);
		cur_sce.setScenarioName(name);		
		addRoleList(cur_sce,roleList);
		addDataList(cur_sce,dataList);
		//Debug end.
		String ans = "";
		try {
			 ans = ProjectService.addScenario(cur_sce);
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		
		String ans1 = "";
		String solutionId_str = request.getParameter("solutionId");
		if(solutionId_str == null || "".equals(solutionId_str)){
			//简单的添加场景. 
			//addScenario()已经做了
		}else{
			//走 Solution 升级这条线
			int prevScenarioId = Integer.valueOf(request.getParameter("prevScenarioId"));
			int solutionId = Integer.valueOf(solutionId_str);
			ans1 = SolutionService.upgrade(prevScenarioId,solutionId,cur_sce.getScenarioId());
		}
		
		String res = "";
		if(ans.equals(ActionSupport.ERROR)|| ans1.equals(ActionSupport.ERROR)){
			res ="{success:false}";
		}else {
			res = "{success:true,scenarioId:"+cur_sce.getScenarioId()+"}";
		}

		try {
			response.getWriter().write(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void addRoleList(Scenario sce,String roleList){
		String roles[] = roleList.split(",");
		for(int i = 0 ;i<roles.length;i++){
			int roleId = Integer.valueOf(roles[i]);
			Role role = new Role();
			role.setRoleId(roleId);
			sce.addRole(role);
		}
	}
	void addDataList(Scenario sce,String dataList){
		String datas[] = dataList.split(",");
		for(int i = 0;i<datas.length ;i++){
			int did = Integer.valueOf(datas[i]);
			Data data = new Data();data.setDataId(did);
			sce.addData(data);
		}
	}
	
}
