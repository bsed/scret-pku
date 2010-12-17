package elicitation.action.domain;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.domain.Domain;
import elicitation.model.domain.DomainService;

/**
 * 
 * @author John 新建项目的时候，获取全部的Domain数据，以便选择项目的数据
 */
public class GetDomainListServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Domain> dlist = DomainService.selectDomainList();
			String ans = "";

			for (int i = 0; i < dlist.size(); i++) {
				Domain d = dlist.get(i);
				ans += "[" + i + ",'" + d.getDomainName() + "','"
						+ d.getDomainDes() + "'," + d.getDomainId() + "],";
			}
			ans = ans.substring(0, ans.length() - 1);
			ans = "[" + ans + "]";
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache,must-revalidate"); // 设置相应无缓存
			response.getWriter().write(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
