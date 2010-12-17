package elicitation.action.scenario;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.action.project.ProjectAction;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.user.SysUser;
/**
 * 
 * @author John
 * 更新场景的描述内容
 */
public class UpdateDesServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		try{
			//request.setCharacterEncoding("utf-8");
			String des = request.getParameter("scenarioDes");
			int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
			int roleId = Integer.valueOf(request.getParameter("roleId"));
			System.out.println(des);
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			
			boolean result = ProjectService.updateScenarioRoleDes(scenarioId, roleId, des);
			String ans = "";
			if(result){
				ans = "{success:true}";				
			}else{
				ans = "{success:false}";
			}
			response.setContentType("text/html");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
