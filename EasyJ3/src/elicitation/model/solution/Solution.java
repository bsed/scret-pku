package elicitation.model.solution;

import java.util.ArrayList;
import java.util.List;

import elicitation.model.project.Scenario;
import elicitation.model.question.Question;
import elicitation.model.user.SysUser;

/**
 * 
 * @author John
 * 解决方案
 * id | name |description | related problem list | related scenario list
 */
public class Solution {
	private int id ;
	private String name;
	private String description;
	private SysUser creator;
	/* wait/yes/no wait 等待决定,yes 投票中 ,no放弃*/
	private String status;
	private List<Question> relatedQuestions = new ArrayList<Question>();
	private List<Scenario> relatedScenarios = new ArrayList<Scenario>();
	private int voteNum;
	
	public int getVoteNum() {
		return voteNum;
	}
	public void setVoteNum(int voteNum) {
		this.voteNum = voteNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public String getDisplayDes(){
		if(description.length()>20){
			return description.substring(0,20) + "....(点击查看全部)";			
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public  void addQuestion(Question qu){
		if(!relatedQuestions.contains(qu)){
			relatedQuestions.add(qu);
		}
	}
	public List<Question> getRelatedQuestions() {
		return relatedQuestions;
	}
	public void setRelatedQuestions(List<Question> relatedQuestions) {
		this.relatedQuestions = relatedQuestions;
	}
	public void addScenario(Scenario sce){
		if(!relatedScenarios.contains(sce)){
			relatedScenarios.add(sce);
		}
	}
	public List<Scenario> getRelatedScenarios() {
		return relatedScenarios;
	}
	public void setRelatedScenarios(List<Scenario> relatedScenarios) {
		this.relatedScenarios = relatedScenarios;
	}
	public SysUser getCreator() {
		return creator;
	}
	public int getCreatorId(){
		return creator.getUserId();
	}
	public void setCreatorId(int id){
		creator = new SysUser();
		creator.setUserId(id);
	}
	public void setCreator(SysUser creator) {
		this.creator = creator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
