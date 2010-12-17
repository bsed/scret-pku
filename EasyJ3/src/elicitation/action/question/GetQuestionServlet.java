package elicitation.action.question;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author John
 * 暂时放弃吧，使用question.jsp来做更为容易吧.
 */
public class GetQuestionServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		int qid = Integer.valueOf(request.getParameter("questionId"));
		
	}
}
