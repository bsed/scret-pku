package elicitation.model.question;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.project.Scenario;
import elicitation.model.user.SysUser;
import junit.framework.TestCase;

public class QuestionServiceTest extends TestCase {

	public void testSelectQuestions() {
		Scenario sce = new Scenario();
		sce.setScenarioId(105);
		sce.setCreatorId(92);
		List<Question> qs = sce.getQuestion();
		for(Question q:qs){
			System.out.println(q.getId()+" "+q.getTitle()+" ="+q.getDescription()+
					" kind="+q.getQkind().getName()+" user="+q.getUser().getUserName());
		}
	}
	public void testVoteQuestion(){
		String res = QuestionService.vote(2, 1, 1);
		System.out.println(res);
	}
	public void testVoteNum(){
		int num = QuestionService.selectVoteNum(40);
		System.out.println(num);
	}
	
	public void testInsertQuestionKind() {
		QKind kind = new QKind();
		kind.setCreatorId(1);
		kind.setProjectId(1);
		kind.setName("name");
		kind.setDescription("des");
		String asn = QuestionService.insertQuestionKind(kind);
		System.out.println(asn);
		assert(asn.equals(ActionSupport.SUCCESS));
	}
	public void testInsertQuestion() {
		int projectId = 95;
		int scenarioId = 105;
		String title = "Question";
		String description = "description";
		int qkindId = 1;
		QKind kind = new QKind(qkindId);
		Question qu = new Question();
		qu.setDescription(description);
		qu.setTitle(title);
		qu.setQkind(kind);
		
		
		SysUser	user = new SysUser();
		user.setUserId(98);
		
		qu.setUser(user);
		Scenario sce = new Scenario();
		sce.setScenarioId(scenarioId);
		
		String res = QuestionService.insertQuestion(qu);
		
		System.out.println(res);
	}

	public void testSelectQKind() {
		Project project =new Project();
		project.setProjectId(105);
		List<QKind>ks =  QuestionService.selectQKind(project);
		for(QKind kind:ks){
			System.out.println(kind.getId()+" "+kind.getName());
		}
	}
	
	public void testSelectQuestion(){
		Question question= QuestionService.selectQuestion(40);
		List<Scenario> ss = question.getRelatedScenarios();
		for(Scenario scenario:ss){
			System.out.println(scenario.getScenarioId()+" "+scenario.getScenarioName());
		}
	}	

}
