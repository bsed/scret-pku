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
 * 对应：ArrayReader.
 * GetScenarioSolutionJsonServlet 对应 :JsonReader.  
 *       ----- 要表达Solution关联哪些问题，方便用户知晓覆盖度.
 *       ----- 或者直接计算出覆盖度来..->效果不如上面的炫...
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
				ans += "["+(i++)+",'"+solution.getName()+"',"+solution.getVoteNum()+","+solution.getId()+","+solution.getRelatedQuestions().size()+"],";
				
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
