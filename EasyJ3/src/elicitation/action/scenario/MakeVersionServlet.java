package elicitation.action.scenario;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.VersionService;
import elicitation.model.question.Question;

public class MakeVersionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
		int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
		String ql = request.getParameter("questionList");
		List<Question> qlist = new ArrayList<Question>();
		if( !("".equals(ql) || ql == null) ){ 
			String []qs = ql.split("[,]");
			for(int i = 0 ;i<qs.length ;i++){
				Question q = new Question(Integer.valueOf(qs[i]));
				qlist.add(q);
			}
		}
		String ans = VersionService.makeVersion(scenarioId,qlist);
		String res = "{success:true}";
		if(ActionSupport.ERROR.equals(ans)){
			res = "{success:false}";
		}
		response.getWriter().write(res);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
