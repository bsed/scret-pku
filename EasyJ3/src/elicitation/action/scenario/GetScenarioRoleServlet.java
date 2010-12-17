package elicitation.action.scenario;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.project.Scenario;

public class GetScenarioRoleServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int scenarioId = Integer
					.valueOf(request.getParameter("scenarioId"));
			Scenario scenario = new Scenario();
			scenario.setScenarioId(scenarioId);
			List<Role> roles = ProjectService.selectRoleList(scenario);
			String ans = "";
			int i = 0;
			for (Role role : roles) {
				ans += "[" + (i++) + ",'" + role.getRoleName() + "','"
						+ role.getRoleDes() + "'," + role.getRoleId() + "],";
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
