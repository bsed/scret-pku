package elicitation.action.solution;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Scenario;
import elicitation.model.solution.Solution;
import elicitation.model.solution.SolutionService;

public class PickSolutionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		int solutionId = Integer.valueOf(request.getParameter("solutionId"));
		int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
		
		Solution solution = new Solution();
		solution.setId(solutionId);
		Scenario sce = new Scenario();
		sce.setScenarioId(scenarioId);
		String res = SolutionService.pick(solution,sce);
		String ans = "{success:false}";
		if(ActionSupport.SUCCESS.equals(res)){
			ans = "{success:true}";
		}
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(ans);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
