package elicitation.action.question;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Scenario;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;

public class QuestionHistoryAction implements Action{
	private Scenario sce = new Scenario();
	private List<Question> questions = null;
	public void setScenarioId(int id){
		sce.setScenarioId(id);
	}
	public List<Question> getHistoryQuestion(){
		if(questions == null) questions  =new ArrayList<Question>();
		return questions;
	}
	@Override
	public String execute() throws Exception {
		questions = QuestionService.selectHistoryQuestions(sce);
		return ActionSupport.SUCCESS;
	}
	

}
