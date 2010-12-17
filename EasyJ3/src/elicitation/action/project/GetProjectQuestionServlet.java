package elicitation.action.project;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;

public class GetProjectQuestionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			Project project =new Project();
			project.setProjectId(projectId);
			List<Question> questions = QuestionService.selectQuestions(project);
			String ans = "";
			int i = 0 ;
			for(Question question:questions){
				ans += "["+ (i++) +",'"+question.getTitle()+"','"+question.getQkind().getName()+"',"+question.getId()+"],";
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
