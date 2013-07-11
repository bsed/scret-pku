package action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import model.Node;

import util.IbatisUtil;


public class FetchServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,
			  HttpServletResponse response) throws IOException,ServletException{
		String date = request.getParameter("date").trim();
		PrintWriter out = response.getWriter();
		if(date == "" ){
			out.print("{error:1}");
			return;
		}
		List<Node> nodes = null; 
		try{
			nodes = IbatisUtil.getNode(date);
		}catch(Exception e){
			
		}
		String outs = "";
		int size = nodes.size();
		JSONArray ja = JSONArray.fromObject(nodes);
		out.print(ja.toString());
	}
	public void doPost(HttpServletRequest request,
			  HttpServletResponse response)throws IOException,ServletException{
		
	}
	
}
