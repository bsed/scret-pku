package elicitation.action.project;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Scenario;

/**
 * 
 * @author John 获取项目的所有场景 {id,scenarioName,scenarioId}
 */
public class GetProjectScenarioServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			Project project = new Project();
			project.setProjectId(projectId);
			List<Scenario> sl = ProjectService.selectScenarioList(project);
			String ans = "";
			int i = 0;
			for (Scenario sce : sl) {
				ans += "[" + (i++) + ",'" + sce.getScenarioName() + "',"
						+ sce.getScenarioId() + "],";
			}
			if (ans.length() > 2)
				ans = ans.substring(0, ans.length() - 1);
			ans = "[" + ans + "]";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
