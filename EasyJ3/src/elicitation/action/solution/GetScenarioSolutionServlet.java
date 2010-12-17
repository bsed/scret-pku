package elicitation.action.solution;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.Scenario;
import elicitation.model.solution.Solution;
import elicitation.model.solution.SolutionService;
/**
 * 
 * @author John
 * Refer：项目管理员确定最终解决方案时要用到.
 */
public class GetScenarioSolutionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
			Scenario sce = new Scenario();
			sce.setScenarioId(scenarioId);
			List<Solution>slist = SolutionService.selectSolutionList(sce);
			String ans = "";
			int i = 0 ;
			for(Solution solution:slist){
				ans += "["+(i++)+",'"+solution.getName()+"',"+solution.getVoteNum()+","+solution.getId()+"],";
			}
			if(ans.length()>2) ans = ans.substring(0,ans.length()-1);
			ans = "["+ans+"]";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
