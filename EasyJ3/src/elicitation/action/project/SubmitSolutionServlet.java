package elicitation.action.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Scenario;
import elicitation.model.question.Question;
import elicitation.model.solution.Solution;
import elicitation.model.solution.SolutionService;
import elicitation.model.user.SysUser;
/**
 * 
 * @author John
 * 提交解决方案
 * 注意：
 *   
 *   1. 当前场景是默认的关联场景.
 */
public class SubmitSolutionServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		System.out.println("Get SubmitSolution");
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		try{
			System.out.println("SubmitSolutionServlet@@");
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			int scenarioId =Integer.valueOf(request.getParameter("scenarioId"));
			String questionList = request.getParameter("questionList");
			String []qs = questionList.split("[,]");
			List<Question> qlist = new ArrayList<Question>();
			for(String q:qs){
				if(q==null || q.equals("")) continue;
				int qid = Integer.valueOf(q);
				Question qu = new Question();
				qu.setId(qid);
				qlist.add(qu);
			}
			String scenarioList = request.getParameter("scenarioList");
			
			String[] ss = scenarioList.split("[,]");
			List<Scenario> slist = new ArrayList<Scenario>();
			for(String s:ss){
				if(s==null||"".equals(s))
					continue;
				int sceid = Integer.valueOf(s);
				//if(sceid == scenarioId) continue;
				Scenario sce = new Scenario();
				sce.setScenarioId(sceid);
				slist.add(sce);
			}
			/*
			 * 当前场景是默认的关联场景.
			 * */
			/*Scenario cur = new Scenario();
			cur.setScenarioId(scenarioId);
			slist.add(cur);*/
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if(user == null){
				user = new SysUser();
				user.setUserId(98);
			}
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Solution solution = new Solution();
			solution.setCreator(user);
			solution.setName(title);
			solution.setDescription(description);
			solution.setRelatedQuestions(qlist);
			solution.setRelatedScenarios(slist);
			
			String res = SolutionService.insertSolution(solution);
			String ans = "{success:false}";
			if(ActionSupport.SUCCESS.equals(res)){
				ans= "{success:true}";
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}
