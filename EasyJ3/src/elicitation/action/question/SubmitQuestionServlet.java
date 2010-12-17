package elicitation.action.question;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Scenario;
import elicitation.model.question.QKind;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;
import elicitation.model.user.SysUser;

public class SubmitQuestionServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		String ans = "{success:false}";
		try{
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
			
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			int qkindId = Integer.valueOf(request.getParameter("qkindId"));
			QKind kind = new QKind(qkindId);
			Question qu = new Question();
			qu.setDescription(description);
			qu.setTitle(title);
			qu.setQkind(kind);
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if(user == null){
				user = new SysUser();
				user.setUserId(98);
			}
			qu.setUser(user);
			Scenario sce = new Scenario();
			sce.setScenarioId(scenarioId);
			
			String scenarioList = request.getParameter("scenarioList");
			String []list = scenarioList.split("[,]");
			for(String sid:list){
				int sceid = Integer.valueOf(sid);
				Scenario sce1 = new Scenario();
				sce1.setScenarioId(sceid);
				qu.addRelatedScenario(sce1);
			}
			String res = QuestionService.insertQuestion(qu);
			if(ActionSupport.SUCCESS.equals(res)) 
				ans = "{success:true}";
			else 
				ans = "{success:false}";
			
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}
}
