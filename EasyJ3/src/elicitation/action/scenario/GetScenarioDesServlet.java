package elicitation.action.scenario;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.ProjectService;

/**
 * 
 * @author John
 * scenario.jsp
 *    edit_scenario.js
 *        edit the role's description for the scenario. 
 * when load the description ,need to lock the item in the table.
 */
public class GetScenarioDesServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int scenarioId = Integer
					.valueOf(request.getParameter("scenarioId"));
			int roleId = Integer.valueOf(request.getParameter("roleId"));
			boolean isFree = ProjectService.isFreeScenarioRoleDes(scenarioId,roleId);
			String des = "the description is locked by other client.";
			if(isFree){
				des = ProjectService.selectScenarioRoleDes(scenarioId,
						roleId);
				ProjectService.lockScenarioRoleDes(scenarioId,roleId);
				System.out.println(des);			
				des = "[[0,'"+des+"']]";
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(des);
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
