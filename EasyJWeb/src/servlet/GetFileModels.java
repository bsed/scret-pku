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

import model.FileModel;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * 
 * @author baipeng {baipeng8608@gmail.com}
 * 
 * Apr 30, 2009
 * @see FileModelService
 */
public class GetFileModels extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetFileModels() {

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
			
			List<FileModel> models= null;			
			models = client.queryForList("getModels");
			
			ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
			oos.writeObject(models);
			oos.flush();
			oos.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
