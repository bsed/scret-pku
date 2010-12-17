package elicitation.action.project;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Data;
import elicitation.model.project.ProjectService;

public class DeleteProjectDataServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try {			
			int did = Integer.valueOf(request.getParameter("dataId"));
			Data data = new Data();
			data.setDataId(did);
			String a = ProjectService.deleteData(data);
			if (!ActionSupport.SUCCESS.equals(a)) {
				System.out.println("Delete Data -" + did + " FAILED@@");
			}
			
			//response.sendRedirect(location);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
