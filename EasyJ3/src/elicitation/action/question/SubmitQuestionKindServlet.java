package elicitation.action.question;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.question.QKind;
import elicitation.model.question.QuestionService;
import elicitation.model.user.SysUser;

/**
 * 提交自定义问题类别
 * @author John
 *
 */
public class SubmitQuestionKindServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		try{
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			SysUser creator = (SysUser) request.getSession().getAttribute("user");
			if(creator == null){
				creator = new SysUser();
				creator.setUserId(98);
			}
			String description = request.getParameter("description");
			String name = request.getParameter("name");
			
			QKind qk = new QKind();
			qk.setCreator(creator);
			qk.setName(name);
			qk.setDescription(description);
			qk.setProjectId(projectId);
			
			String res = QuestionService.insertQuestionKind(qk);
			response.setContentType("text/html;charset=utf-8");
			String ans ="{success:true}";
			if(ActionSupport.ERROR.equals(res)){
				ans = "{success:false}";
			}
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
