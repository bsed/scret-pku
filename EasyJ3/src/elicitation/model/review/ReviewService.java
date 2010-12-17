package elicitation.model.review;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.utils.Utils;

public class ReviewService {
	static SqlMapClient client = Utils.getMapClient();
	public static Review selectReview(int rid){
		Review rv = null;
		try {
			rv = (Review) client.queryForObject("review.selectReview",rid);
			selectReviewRole(rv);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return rv;
	}
	private static void selectReviewRole(Review review){
		try{
			List roles = client.queryForList("review.selectReviewRole",review.getId());
			for(Object ob:roles){
				Role  role = new Role();
				int roleId = Integer.valueOf(ob.toString());
				role.setRoleId(roleId);
				role = ProjectService.selectRole(role);
				review.addRole(role);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static List<Review> selectScenarioReview(int sid){
		List rs = null;
		try {
			rs = client.queryForList("review.selectScenarioReview",sid);
			for(Object or:rs){
				Review review = (Review)or;
				selectReviewRole(review);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 
	 * @param review
	 * @return
	 * scenarioId/userId/content.
	 * 
	 * id 和 time是在插入的时候自动添加的.
	 */
	public static String insertReview(Review review){
		try {
			Object r = client.insert("review.insertReview",review);
			if(r == null){
				System.err.println("ReviewService#insertReview failed");
				return ActionSupport.ERROR;
			}
			//insert the review-role relation.
			HashMap para = new HashMap();	
			int reid = review.getId();
			para.put("review_id",reid);
			for(Role role:review.getRoles()){
				para.put("role_id",role.getRoleId());
				client.insert("review.insertReviewRole",para);
			}
			
			return ActionSupport.SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		
	}
}
