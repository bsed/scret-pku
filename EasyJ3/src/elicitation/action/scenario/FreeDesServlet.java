package elicitation.action.scenario;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.ProjectService;
import elicitation.model.user.SysUser;

public class FreeDesServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
			int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
			int roleId = Integer.valueOf(request.getParameter("roleId"));
			boolean result = ProjectService.freeScenarioRoleDes(scenarioId, roleId);
			String ans = "";
			if(result){
				ans = "{success:true}";				
			}else{
				ans = "{success:false}";
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
