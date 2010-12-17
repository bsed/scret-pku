package elicitation.model.project;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

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
	 * 利用找到的scenario_id的大小来判断版本的先后。
	 * @param sce#id
	 * @return
	 */
	public static Scenario selectNextVersion(Scenario sce){
		try{
			sce = ProjectService.selectScenario(sce);
		}catch(SQLException se){
			se.printStackTrace();
		}
		List<Scenario> ss = selectScenarioVersion(sce.getScenarioName());
		int id = sce.getScenarioId();
		int size = ss.size();
		int min = Integer.MAX_VALUE;
		int imin = -1;
		for(int i = 0 ;i<size;i++){
			int tid = ss.get(i).getScenarioId();
			if(tid>id){
				if(tid<min) {
					min = tid;
					imin = i ;
				}
			}
		}
		if(min != Integer.MAX_VALUE){
			return ss.get(imin);
		}
		return null;
	}
	public static Scenario selectPreVersion(Scenario sce){
		try{
			sce = ProjectService.selectScenario(sce);
		}catch(SQLException se){
			se.printStackTrace();
		}
		List<Scenario> ss = selectScenarioVersion(sce.getScenarioName());
		int id = sce.getScenarioId();
		int size = ss.size();
		int max = Integer.MIN_VALUE;
		int imax = -1;
		for(int i = 0 ;i<size;i++){
			int tid = ss.get(i).getScenarioId();
			if(tid<id){
				if(tid>max) {
					max = tid;
					imax = i ;
				}
			}
		}
		if(max != Integer.MIN_VALUE){
			return ss.get(imax);
		}
		return null;
	}
	
}
