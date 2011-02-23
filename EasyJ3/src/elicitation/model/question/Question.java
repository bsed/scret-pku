package elicitation.model.question;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import elicitation.model.project.Scenario;
import elicitation.model.user.SysUser;

/**
 * 用户对场景的提问
 * */
public class Question {
	private int id;
	private String title;
	private String description;
	private QKind  qkind;
	private List<Scenario> relatedScenarios = new ArrayList<Scenario>(); // related scenarios.
	private SysUser user;      //the user put the question!
	
	private Date buildTime;
	private String status;
	public Question(){
		
	}
	public Question(int id){
		this.id = id;
	}
	public int getQuestionId(){
		return id;
	}
	public void setBuildTime(Date time){
		buildTime = time;
	}
	public Date getBuildTime(){
		return buildTime;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return this.status;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public QKind getQkind() {
		return qkind;
	}
	public void setQkind(QKind qkind) {
		this.qkind = qkind;
	}
	public List<Scenario> getRelatedScenarios() {
		return relatedScenarios;
	}
	public void addRelatedScenario(Scenario sce){
		if(relatedScenarios.contains(sce)) return;
		relatedScenarios.add(sce);
	}
	public void setRelatedScenarios(List<Scenario> relatedScenarios) {
		this.relatedScenarios = relatedScenarios;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	
	
	/************for ibatis ************/
	
	public void setUserId(int id){
		if(user == null) user = new SysUser();
		user.setUserId(id);
	}
	public int getUserId(){
		return user.getUserId();
	}//qKindId
	public void setQkindId(int id){
		if(qkind == null) qkind = new QKind();
		qkind.setId(id);
	}
	public int getQkindId(){
		return qkind.getId();
	}
	public int getVoteNum(){
		return QuestionService.selectVoteNum(getId());
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Question){
			Question q = (Question)o;
			return q.id == this.id;
		}
		return false;
	}
	public int hashCode(){
		return Integer.valueOf(id).hashCode();
	}
	
}

