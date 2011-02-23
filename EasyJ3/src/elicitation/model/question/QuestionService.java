package elicitation.model.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.project.Scenario;
import elicitation.model.user.SysUser;
import elicitation.utils.Utils;

/**
 * 提问有关的数据服务
 * 
 * @author John
 * 
 */
public class QuestionService {
	public static SqlMapClient client = Utils.getMapClient();
	public static Question selectQuestion(int questionId){
		Question qs = null; 
		try{
			qs  = (Question) client.queryForObject("question.selectQuestion",questionId);
			QKind kind = (QKind) client.queryForObject("question.selectQKindById" ,qs.getQkindId());
			qs.setQkind(kind);
			List<Scenario> sces = client.queryForList("question.selectRelatedScenario",qs.getId());
			qs.setRelatedScenarios(sces);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return qs;
		
		
	}
	/**
	 * 找出该版本场景的历史问题
	 * 
	 * TODO： 需要找出之前版本的问题吗？？还是仅仅是当前版本的历史问题..
	 * 
	 * 当前策略：
	 * 　　　　只找出当前版本的历史问题．
	 * @param sce
	 * @return
	 */
	public static List<Question> selectHistoryQuestions(Scenario sce){
		List<Question> qs = new ArrayList<Question>();
		try{
			qs = client.queryForList("question.selectHistoryQuestion",sce);	//TODO
			for(Question q:qs){
				SysUser user = (SysUser) client.queryForObject("user.getUserByUserID",q.getUserId());
				q.setUser(user);
				QKind  kind = (QKind) client.queryForObject("question.selectQKindById",q.getQkindId());
				q.setQkind(kind);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return qs;
		}
	}
	/**
	 * 找出当前场景的问题. 默认是  wait 问题.
	 * 
	 * @param sce
	 * @return
	 */
	public static List<Question> selectQuestions(Scenario sce){
		List<Question> qs = new ArrayList<Question>();
		try{
			qs = client.queryForList("question.selectQuestions",sce);	//TODO in sql
			for(Question q:qs){
				SysUser user = (SysUser) client.queryForObject("user.getUserByUserID",q.getUserId());
				q.setUser(user);
				QKind  kind = (QKind) client.queryForObject("question.selectQKindById",q.getQkindId());
				q.setQkind(kind);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return qs;
		}
	}
	
	public static List<Question> selectQuestions(Project project){
		List<Question> qs = new ArrayList<Question>();
		try{
			qs = client.queryForList("question.selectQuestionsByProject",project);	//TODO in sql
			for(Question q:qs){
				SysUser user = (SysUser) client.queryForObject("user.getUserByUserID",q.getUserId());
				q.setUser(user);
				QKind  kind = (QKind) client.queryForObject("question.selectQKindById",q.getQkindId());
				q.setQkind(kind);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return qs;
		}
	}
	public static String insertQuestionKind(QKind qk){
		try{
			client.insert("question.insertQuestionKind",qk);	//TODO in sql
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	public static String mapQuestion2Scenario(List<Question> qs ,Scenario sce){
		try{
			int sid = sce.getScenarioId();
			HashMap para = new HashMap();
			for(Question q:qs){
				para.put("scenarioId",sid);
				para.put("questionId", q.getId());
				client.insert("question.insertQuestion2Scenario",para);
			}
		}catch(Exception e){
			e.printStackTrace();	
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	public static String insertQuestion(Question qu){
		try{
			client.insert("question.insertQuestion",qu);	
			//插入问题相关场景
			List<Scenario> scenarios = qu.getRelatedScenarios();
			HashMap para = new HashMap();
			for(Scenario scenario:scenarios){
				para.put("scenarioId", scenario.getScenarioId());
				para.put("questionId", qu.getId());
				client.insert("question.insertQuestion2Scenario",para);
			}
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	public static List<QKind> selectQKind(Project project) {
		List<QKind>ks = new ArrayList<QKind>();
		try{
			ks = client.queryForList("question.selectQKind",project);//TODO in sql.
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return ks;
		}	
	}
	/**	 
	 * @param qid
	 * @param num
	 * @return
	 */
	public static String vote(int qid, int userId , int vote) {
		try{
			HashMap para = new HashMap();
			para.put("questionId", qid);
			para.put("userId", userId);
			para.put("vote", vote);			
			Object r = client.queryForObject("question.selectVote",para);
			if(r != null) {
				
				return String.valueOf(r);
			}
			Object o = client.insert("question.vote",para);
			if(o == null){
				return ActionSupport.ERROR;
			}else{
				System.out.println(o);
				return ActionSupport.SUCCESS;					
			}
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
	}
	public static int selectVoteNum(int qid ){
		try{
			Object r = client.queryForObject("question.selectVoteNum",qid);
			if(r == null)
				return 0;
			return (Integer) r;
		}catch(Exception e){
			e.printStackTrace();
			return Integer.MAX_VALUE;
		}
	}
	
}
