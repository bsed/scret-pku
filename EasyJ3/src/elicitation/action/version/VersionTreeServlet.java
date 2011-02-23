package elicitation.action.version;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Scenario;
import elicitation.model.project.VersionService;

public class VersionTreeServlet extends HttpServlet {
	@Override
	protected void	doGet(HttpServletRequest request,HttpServletResponse response)  {
		int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
		Scenario root = new Scenario(scenarioId);
		try{
			String tree = ProjectService.buildTreeJson(root);
		
			response.setContentType("text/javascript;charset=utf-8");
			response.getWriter().write(tree);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
