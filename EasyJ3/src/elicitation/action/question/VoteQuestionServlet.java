package elicitation.action.question;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.question.QuestionService;
import elicitation.model.user.SysUser;

public class VoteQuestionServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		int qid = Integer.valueOf(request.getParameter("questionId"));
		int num =  Integer.valueOf(request.getParameter("num"));
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user == null) {
			return ;
		}
		int uid = user.getUserId();
		String res = QuestionService.vote(qid,uid,num);
		String ans = "{success:true,repeat:false}";
		if(ActionSupport.ERROR.equals(res)){
			ans = "{success:false}";
		}
		if("1".equals(res)){
			ans="{success:true,repeat:1}";
		}else if("-1".equals(res)){
			ans="{success:true,repeat:-1}";
		}
		try{
			response.getWriter().write(ans);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
