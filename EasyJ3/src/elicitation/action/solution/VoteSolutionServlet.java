package elicitation.action.solution;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.solution.SolutionService;
import elicitation.model.user.SysUser;
/**
 * 
 * @author John
 * 参与者对solution进行投票
 */
public class VoteSolutionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
			int solutionId = Integer.valueOf(request.getParameter("solutionId"));
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if(user == null){
				user = new SysUser();
				user.setUserId(98);
			}
			String res = SolutionService.vote(solutionId, user.getUserId());
			String ans = "{success:true,repeat:false}";
			if(ActionSupport.ERROR.equals(res)){
				ans = "{success:false}";
			}
			if("REPEAT_VOTE".equals(res)){
				ans="{success:true,repeat:true}";
			}
			response.getWriter().write(ans);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
