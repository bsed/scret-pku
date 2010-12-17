package elicitation.model.review;

import java.util.List;

import elicitation.model.project.Role;

import junit.framework.TestCase;

public class ReviewServiceTest extends TestCase {

	public void testSelectReview() {
		Review rv = ReviewService.selectReview(12);
		System.out.println(rv);
	}

	public void testSelectScenarioReview() {
		List<Review> rlist = ReviewService.selectScenarioReview(111);
		for(Review rv:rlist){
			System.out.println(rv);
		}
	}
	public void testInsertReview(){
		Review review = new Review();
		review.setUserId(10);
		review.setScenarioId(100);
		review.setContent("sadfsadfasdf");
		Role role = new Role();
		role.setRoleId(12);
		review.addRole(role);
		String a = ReviewService.insertReview(review);
		System.out.println(a);
		
	}

}
