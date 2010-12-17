package elicitation.action.review;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Role;
import elicitation.model.project.Scenario;
import elicitation.model.review.Review;
import elicitation.model.review.ReviewService;
import elicitation.model.user.SysUser;
/**
 * 
 * @author John
 *
 * 插入评论
 */
public class ReviewServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user == null){
			user = new SysUser();
			user.setUserId(-1);
		}
		
		String content = request.getParameter("review_scenario");
		Review review = new Review();
		review.setContent(content);
		review.setScenarioId(scenarioId);
		review.setUserId(user.getUserId());
		String roleList = request.getParameter("roleList");
		addRoleList(review,roleList);
		String ans =  ReviewService.insertReview(review);
		response.setContentType("text/html;charset=utf-8");
		if(ActionSupport.ERROR.equals(ans)){
			response.getWriter().write("{success:false}");
		}else{
			response.getWriter().write("{success:true}");
		}
	}
	void addRoleList(Review review,String roleList){
		String roles[] = roleList.split(",");
		for(int i = 0 ;i<roles.length;i++){
			int roleId = Integer.valueOf(roles[i]);
			Role role = new Role();
			role.setRoleId(roleId);
			review.addRole(role);
		}
	}
}
