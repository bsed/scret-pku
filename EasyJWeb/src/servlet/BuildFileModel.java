package servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DrawModel;
import model.FileModel;



import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * 
 * @author baipeng {baipeng8608@gmail.com}
 *
 *  Apr 30, 2009
 */
public class BuildFileModel extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private int recid;
	public BuildFileModel() {

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			SqlMapClient client = null;
			ServletContext context = getServletContext();// 使用context来得到实际的file
															// path.
			DataInputStream input = new DataInputStream(context
					.getResourceAsStream("SqlMapConfig.xml"));
			client = Utils.getSqlMapClient(input);
			
			FileModel model = null;
			recid = Integer.valueOf(req.getParameter("recid"));
			model= (FileModel) client.queryForObject("getModel",recid);
			if(model != null){
				List<DrawModel> draws = client.queryForList("getDraws",recid);
				model.setDraws(draws);
			}					
			
			
			ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
			oos.writeObject(model);
			oos.flush();
			oos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
