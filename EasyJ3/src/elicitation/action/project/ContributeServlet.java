package elicitation.action.project;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.VersionService;
import elicitation.model.user.SysUser;
/**
 * 
 * @author John
 * Use Case：确定场景版本，用户点击[贡献完毕]，然后到这个Servlet进行数据库更新处理
 */
public class ContributeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user == null){
			user = new SysUser();
			user.setUserId(98);
		}
		String ans = "{success:true}";
		String res = VersionService.voteConfirm(user.getUserId(), scenarioId,roleId);
		if(ActionSupport.ERROR.equals(res)){
			ans = "{success:false}";
		}
		response.setContentType("text/html;charset=utf-8");
		try{
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();			
		}
		
	}
	
	
}
