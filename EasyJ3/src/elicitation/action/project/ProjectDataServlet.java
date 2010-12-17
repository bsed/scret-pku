package elicitation.action.project;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.Data;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;

public class ProjectDataServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		/**
		 * Get all project's datas.
		 */
		try {
			Project project = new Project();
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			project.setProjectId(projectId);
			List<Data> datalist = ProjectService.selectDataList(project);
			String ans = "";
			int i =   0 ;
			for (Data data : datalist) {
				ans += "['"+(i++)+"','" + data.getDataName() + "','" + data.getDataDes()
						+ "',"+data.getDataId()+"],";
			}
			if(ans.length() >2)
				ans = ans.substring(0,ans.length()-1);
			ans = "[" + ans + "]";
			ans = ans.replace('\n', ' ');//去除其中的换行符，不然json格式不对.
			System.out.println("ans="+ans);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(ans);
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
