package elicitation.service.request;

import java.util.List;

import elicitation.model.project.Project;
import elicitation.model.request.Request;
import junit.framework.TestCase;

public class RequestServiceTest extends TestCase {

	public void testSelectRequests() throws Exception{
		Project pro = new Project();
		pro.setProjectId(98);
		List<Request> reqs = RequestService.selectRequests(pro);
		System.out.println();		
	}

}
