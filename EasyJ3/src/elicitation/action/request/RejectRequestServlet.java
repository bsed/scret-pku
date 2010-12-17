package elicitation.action.request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.request.Request;
import elicitation.service.request.RequestService;

public class RejectRequestServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String reqlist = request.getParameter("reqList");
			String reqs[] = reqlist.split("[,]");
			for (int i = 0; i < reqs.length; i++) {
				if (reqs[i] == null || "".equals(reqs[i]))
					continue;
				int reqid = Integer.valueOf(reqs[i]);
				Request req = new Request();
				req.setRequestId(reqid);
				String ans = RequestService.rejectRequest(req);
				if (ActionSupport.ERROR.equals(ans)) {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("{success:false}");
					return;
				}
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("{success:true}");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
