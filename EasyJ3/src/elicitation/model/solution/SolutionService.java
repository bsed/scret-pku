package elicitation.model.solution;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Scenario;
import elicitation.model.project.VersionService;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;
import elicitation.model.user.SysUser;
import elicitation.utils.Utils;
/**
 * 引入投票功能
 * vote()-- vote the solution.
 * pick()-- pick the solution as the best.s
 * */
public class SolutionService {
	static SqlMapClient client = Utils.getMapClient();
	/**
	 * 将Solution关联的Question 和Scenario 也一并找出了.
	 * @param slutionId
	 * @return
	 */
	public static Solution selectSolution(int slutionId){
		Solution solution =  null ;
		try{
			solution  = (Solution) client.queryForObject("solution.selectSolution",slutionId);
			List<Question> qlist = client.queryForList("solution.selectSolutionQuestionList",solution);
			solution.setRelatedQuestions(qlist);
			List<Scenario> slist = client.queryForList("solution.selectSolutionScenarioList",solution);
			solution.setRelatedScenarios(slist);
			int vnum = selectVoteNum(slutionId);
			solution.setVoteNum(vnum);
			SysUser user = (SysUser) client.queryForObject("user.getUserByUserID",solution.getCreatorId());
			solution.setCreator(user);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return solution;
		}
	}
	public static List<Solution> selectSolutionList(Scenario scenario){
		List<Solution> results = new ArrayList<Solution>();
		try {
			List<Solution> solutions = null;
			solutions = client.queryForList("solution.selectSolutionListofScenario",scenario);
			for(Solution solution:solutions){
				solution = selectSolution(solution.getId());				
				results.add(solution);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	/**
	 * table scenario_version, [perv_scenario,solution,next_scenario]
	 * TODO:
	 * 	   投票之前: 可能一个场景关联多个解决方案，
	 *     投票之后：一个场景必定关联一个解决方案吗？
	 * Stage 1: 
	 *    只允许最终确定一个解决方案 .
	 *     ---------> 可能需要提供解决方案的合并等.
	 * Stage 2:
	 * 	  允许指定多个解决方案. 
	 * 
	 * @return
	 */
	public static List<Solution> selectSolutionFromPrevScenario(Scenario prevsce){
		List<Solution> solutions = new ArrayList<Solution>();
		try{
			List<Integer> tmp  = client.queryForList("solution.selectSolutionFromPrevScenario",prevsce);
			for(Integer id:tmp){
				Solution solution = selectSolution(id);
				solutions.add(solution);
			}
			return solutions;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return solutions;
		}
	}
	/**
	 * 
	 * @param nextsce
	 * @return
	 * Solution - NextSce ] 多对多关系
	 */
	public static List<Solution> selectSolutionFromNextScenario(Scenario nextsce){
		List<Solution> solutions = new ArrayList<Solution>();
		try{
			List<Integer> tmp  =client.queryForList("solution.selectSolutionFromNextScenario",nextsce);
			for(Integer id:tmp){
				Solution solution = selectSolution(id);
				solutions.add(solution);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return solutions;
		}
	}
	/**
	 * 针对solutionId投一票
	 * @param solutionId
	 */
	public static String vote(int solutionId,int userId){
		try{
			HashMap para = new HashMap();
			para.put("solutionId", solutionId);
			para.put("userId", userId);
			Object numo = client.queryForObject("solution.selectVote",para);
			if(numo == null){
				client.insert("solution.insertVote",para);
				return ActionSupport.SUCCESS;
			}else{
				System.err.println("user["+userId+"] has voted.");
				return "REPEAT_VOTE";
			}
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		
	}
	public static int selectVoteNum(int solutionId){
		int num  = 0 ;
		try{
			Object numo =  client.queryForObject("solution.selectVoteNum",solutionId);
			if(numo != null)
				num = (Integer)numo;
		}catch(Exception e){
			e.printStackTrace();
		}finally{			
			return num;
		}
	}
	/**
	 * 当每个人都投票以后，项目管理者有权挑选最终的solution
	 * 投票是一个筛选和建议的过程/最终决定权在项目管理者手中
	 * 一个场景可能对应多个选出的解决方案。
	 * 例如：
	 * solution1-{sce1,sce2} ,solution2-{sce2,sce3}在sce2中管理员可以选solution1 和 solution2作为确定选择方案。
	 *     具体含义：将sce2拆分为两部分，分别并入sce1和sce3
	 * @param solutionId
	 * TODO: 当选中其中一个解决方案时，其他解决方案的状态是否需要变更?! 
	 */
	public static String pick(Solution solution,Scenario scenario){
		try{
			/**
			 * 1. 更新 table solution(status= 'yes')
			 */
			client.update("solution.pickSolution",solution);
			/**
			 * 2. 获取当前场景的 data/role/description.			 * 
			 */
			Scenario nextScenario = ProjectService.selectScenarioAllInfo(scenario);
			/**
			 * 3. 生成新的草稿 , 并关联 问题和解决方案.
			 */
			ProjectService.addScenario(nextScenario);
			
			List<Question> questions = scenario.getQuestion();
			QuestionService.mapQuestion2Scenario(questions, nextScenario);
			
			List<Solution> solutions = scenario.getSolutions();
			SolutionService.mapSolution2Scenario(solutions,nextScenario);
			/**
			 * 4. 考虑已有的版本如何处理？
			 *  
			 *    单单冻结？还是隐藏掉？
			 */
			
			/**
			  * 5. 冻结原版本的 提问和解决方案 功能. 
			 */
			ProjectService.freezeScenario(scenario); //冻结原有场景版本
			/**
			 * 6. 标识 所选的 解决方案. UI部分的功能.
			 */
			
			return ActionSupport.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
	}	
	public static String mapSolution2Scenario(List<Solution> solutions,	
			Scenario scenario) {
		try{
			HashMap para = new HashMap();
			int sceId = scenario.getScenarioId();
			for(Solution so:solutions){
				int solutionId = so.getId();
				para.put("scenarioId", sceId);
				para.put("solutionId", solutionId);
				client.insert("solution.insertSolutionScenario",para);
			
			}
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
		
	}
	public static Scenario selectNextScenarioFromSolution(Scenario presce,Solution solution){
		Scenario scenario = new Scenario();
		try{
			HashMap para = new HashMap();
			para.put("preId",presce.getScenarioId());
			para.put("solutionId",solution.getId());
			Integer id = (Integer) client.queryForObject("solution.selectNextScenarioFromSolution",para);
			scenario.setScenarioId(id);
			scenario = ProjectService.selectScenario(scenario);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return scenario;
		}
	}
	
	public static Scenario selectPreScenarioFromSolution(Scenario nextsce,Solution solution){
		Scenario scenario = new Scenario();
		// scenarioId = 0 表示该场景失效.
		try{
			HashMap para = new HashMap();
			para.put("nextId",nextsce.getScenarioId());
			para.put("solutionId",solution.getId());
			Integer id = (Integer) client.queryForObject("solution.selectPreScenarioFromSolution",para);
			if(id != null){
				scenario.setScenarioId(id);
				scenario = ProjectService.selectScenario(scenario);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return scenario;
		}
	}
	/***
	 * Include the related questions and scenarios.
	 * @param solution
	 * @return
	 */
	public static String insertSolution(Solution solution) {
		try{
			Object o = client.insert("solution.insertSolution", solution);
			if(o==null){
				return ActionSupport.ERROR;
			}
			int solutionId = (Integer)o;
			for(Question question:solution.getRelatedQuestions()){
				HashMap para =new HashMap();
				para.put("solutionId", solutionId);
				para.put("questionId", question.getId());
				client.insert("solution.insertSolutionQuestion",para);
			}
			for(Scenario scenario:solution.getRelatedScenarios()){
				HashMap para = new HashMap();
				para.put("scenarioId", scenario.getScenarioId());
				para.put("solutionId", solutionId);
				client.insert("solution.insertSolutionScenario",para);
			}
			return ActionSupport.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		
	}
	public static String upgrade(int prevScenarioId, int solutionId,
			int scenarioId) {
		HashMap para = new HashMap();
		para.put("prevScenarioId", prevScenarioId);
		para.put("solutionId", solutionId);
		para.put("scenarioId", scenarioId);
		Object o = null;
		try{
		o=	client.insert("solution.upgrade",para);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(o == null){
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	
	
}
