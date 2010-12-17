package elicitation.model.solution;

import java.util.ArrayList;
import java.util.List;

import elicitation.model.project.Scenario;
import elicitation.model.question.Question;
import junit.framework.TestCase;

public class SolutionServiceTest extends TestCase {

	public void testSelectSolution() {
		Solution solution = SolutionService.selectSolution(45);
		System.out.println(solution.getName()+" "+solution.getDescription()+" "+solution.getCreator().getUserName());
		for(Question ques:solution.getRelatedQuestions()){
			System.out.println(ques.getTitle()+" "+ques.getDescription());
		}
		for(Scenario sce:solution.getRelatedScenarios()){
			System.out.println(sce.getScenarioName());
		}		
		System.out.println("VoteNum="+solution.getVoteNum());
	}
	
	public void testSelectionSolutionFromPrevScenario(){
		Scenario sce = new Scenario();
		sce.setScenarioId(1);
		List<Solution> solutions  = SolutionService.selectSolutionFromPrevScenario(sce);
		for(Solution solution:solutions){
			System.out.println(solution.getName()+" "+solution.getDescription()+" "+solution.getCreator().getUserName());
			for(Question ques:solution.getRelatedQuestions()){
				System.out.println(ques.getTitle()+" "+ques.getDescription());
			}
			for(Scenario scenario:solution.getRelatedScenarios()){
				System.out.println(scenario.getScenarioName());
			}
		}
	}
	public void testSelectSolutionFromNextScenario(){
		Scenario sce = new Scenario();
		sce.setScenarioId(1);
		List<Solution> solutions  = SolutionService.selectSolutionFromNextScenario(sce);
		for(Solution solution:solutions){
			System.out.println(solution.getName()+" "+solution.getDescription()+" "+solution.getCreator().getUserName());
			for(Question ques:solution.getRelatedQuestions()){
				System.out.println(ques.getTitle()+" "+ques.getDescription());
			}
			for(Scenario scenario:solution.getRelatedScenarios()){
				System.out.println(scenario.getScenarioName());
			}
		}
	}
	public void testSelectVoteNum(){
		int num  = SolutionService.selectVoteNum(1);
		System.out.println(num);
	}
	public void testVote(){
		SolutionService.vote(1, 2);
	}
	public void testSelectScenario(){
		Scenario sce = new Scenario();
		sce.setScenarioId(1);
		Solution solution = new Solution();
		solution.setId(1);
		Scenario next = SolutionService.selectNextScenarioFromSolution(sce, solution);
		System.out.println(next);
		Scenario pre = SolutionService.selectPreScenarioFromSolution(next, solution);
		System.out.println(pre);
	}
	public void testInsertSolution(){
		Solution solution = new Solution();
		solution.setName("haha");
		solution.setDescription("hahhsha description");
		solution.setCreatorId(12);
		List<Question> qlist = new ArrayList<Question>();
		Question q = new Question();
		q.setId(11);
		qlist.add(q);
		
		List<Scenario> slist = new ArrayList<Scenario>();
		Scenario sce = new Scenario();
		sce.setScenarioId(123);
		slist.add(sce);
		solution.setRelatedQuestions(qlist);
		solution.setRelatedScenarios(slist);
		SolutionService.insertSolution(solution);
	}
	public void testUpgrade(){
		String a = SolutionService.upgrade(1, 2, 0);
		System.out.println(a);
	}

}
