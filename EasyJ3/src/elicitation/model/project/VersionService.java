package elicitation.model.project;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.question.Question;
import elicitation.utils.Utils;

/**
 * Table scenario_vote
 * 辅助确定场景版本
 * 1. 当参与者认为现状场景的表达完整，没有需要修改的地方，则点击按钮[贡献完毕];
 * */
public class VersionService {
	static SqlMapClient client = Utils.getMapClient();
	
	/**
	 * 投票确认自己贡献完毕
	 */
	public static String voteConfirm(int userId,int scenarioId,int roleId){
		HashMap para = new HashMap();
		para.put("userId", userId);
		para.put("scenarioId", scenarioId);
		para.put("roleId", roleId);
		try{
			Object o = client.queryForObject("version.selectVote",para);
			if(o == null){ //如果以前没有投票的话，先在投一票.
				System.out.println(client.insert("version.insertVote",para));
			}
			return ActionSupport.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
	}
	/**
	 * 获得投票率
	 * if 30% 
	 * */
	public static float getVoteRate(int scenarioId,int roleId){
		int total = -1 ;int voteNum = -1;
		try{
			Object tmp = null;
			tmp = client.queryForObject("version.selectRoleUserNum",roleId);
			if(tmp!= null){
				total = (Integer)tmp;
			}
			HashMap para = new HashMap();
			para.put("roleId", roleId);
			para.put("scenarioId", scenarioId);
			tmp = client.queryForObject("version.selectRoleVoterNum",para);
			if(tmp != null){
				voteNum = (Integer)tmp;
			}
			if(total ==  0 ) return 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		float x = (float)voteNum/total;
		x  = ((int)(x*100))/(float)100;
		return x;
	}
	public static String makeVersion(int scenarioId, List<Question> qlist) {
		try {
			client.startTransaction();
			client.update("version.makeVersion", scenarioId);
			for(Question q :qlist){
				client.update("question.fixQuestion",q);
			}
			client.commitTransaction();
			client.endTransaction();
			return ActionSupport.SUCCESS;
		} catch (Exception e) {
			return ActionSupport.ERROR;
		}
	}
	public static String makeVersion(int scenarioId) {
		try{
		client.update("version.makeVersion",scenarioId);
		}catch(Exception e){
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}

	/**
	 * 根据名字找出所有的 场景版本.
	 * 在sql语句中，order by build_time desc.
	 * 
	 * 
	 * freeze or version. 
	 * freeze 是冻结版本
	 * version 是活动版本
	 * TODO: 需要将解决方案的融入进来. version_x1 -solution_x1 - version_x1
	 * 			SolutionService#selectSolutionFromPrevScenario(Scenario prevsce)
	 * @param name
	 * @return
	 * @deprecated  --- 线性结构，后面已经改成多分支结构了.
	 */
	public static List<Scenario> selectScenarioVersion(String name){
		List<Scenario> scenarios = null; 
		try{
			scenarios=client.queryForList("version.selectScenarioVersion",name);
		}catch(Exception e){
			e.printStackTrace();
		}
		return scenarios;
	}
	/**
	 * 多分支结构
	 * @param sce#id
	 * @return
	 * @throws SQLException 
	 */
	public static List<Scenario> selectNextVersions(Scenario sce) throws SQLException{
		List<Scenario> nexts = client.queryForList("version.selectNextVersions",sce);
		return nexts;
	}
	/**
	 * Table scenario_version.
	 * 采用多分支技术后，要更改代码.
	 * @param sce
	 * @return
	 * @throws SQLException 
	 */
	public static Scenario selectPreVersion(Scenario sce) throws SQLException{
		Scenario pre = (Scenario) client.queryForObject("version.selectPreVersion",sce);
		return pre;
	}
	
}
