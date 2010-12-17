package elicitation.action.project;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Scenario;

public class DeleteProjectScenarioServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response) {
		try {
			int sid = Integer.valueOf(request.getParameter("scenarioId"));
			Scenario scenario = new Scenario();
			scenario.setScenarioId(sid);
			String a = ProjectService.deleteScenario(scenario);
			if (!ActionSupport.SUCCESS.equals(a)) {
				System.out.println("Delete Scenario -" + sid + " FAILED@@");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
