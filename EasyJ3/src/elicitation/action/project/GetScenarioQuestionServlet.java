package elicitation.action.project;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.Scenario;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;

public class GetScenarioQuestionServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
			int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
			Scenario scenario =new Scenario();
			scenario.setScenarioId(scenarioId);
			List<Question> questions = QuestionService.selectQuestions(scenario);
			String ans = "";
			int i = 0 ;
			for(Question question:questions){
				ans += "["+ (i++) +",'"+question.getTitle()+"','"+question.getQkind().getName()+"',"+question.getId()+
				","+question.getVoteNum()+"],";
			}
			if(ans.length()>2)
				ans = ans.substring(0,ans.length()-1);
			ans = "["+ans+"]";
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
