package elicitation.action.question;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import elicitation.model.project.Project;
import elicitation.model.question.QKind;
import elicitation.model.question.QuestionService;

public class GetQKindServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String bookName = request.getParameter("searchQKind");

			int projectId = Integer.valueOf(request.getParameter("projectId"));
			Project project = new Project();
			project.setProjectId(projectId);
			List<QKind> kinds = QuestionService.selectQKind(project);
			String ans = "";
//			if (bookName != null && !"allqkind".equals(bookName))
//				bookName = bookName.substring(0, 3);
			for (QKind kind : kinds) {
				if (bookName != null && bookName.equals("allqkind")) {
					ans += "[" + kind.getId() + ",'" + kind.getName() + "'],";
				} else {
					String name = kind.getName();
					int end = name.length() > bookName.length() ? bookName.length() : name.length();
					if (bookName.equals(name.substring(0, end))) {
						ans += "[" + kind.getId() + ",'" + kind.getName()
								+ "'],";
					}
				}
			}
			if (ans.length() > 2)
				ans = ans.substring(0, ans.length() - 1);

			ans = "[" + ans + "]";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
