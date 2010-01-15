package servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
 *  
 */
public class UpdateModel extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private int recid;
	public UpdateModel() {

	}	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			SqlMapClient client = null;
			ServletContext context = getServletContext();// 使用context来得到实际的file
															// path.
			DataInputStream input = new DataInputStream(context
					.getResourceAsStream("SqlMapConfig.xml"));
			client = Utils.getSqlMapClient(input);
			//TODO 如何从req中取得FileModel这样的参数呢？
			FileModel fileModel = null ; 
			ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
			fileModel = (FileModel)ois.readObject();
			ois.close();
			if(fileModel != null){
				System.out.println("UpdateModel#fileModel!=null");
			}else{
				System.out.println("UpdateModel#fileModel==null");
			}
			client.update("updateModel", fileModel);
			List<DrawModel> draws = fileModel.getDraws();
			for(int i = 0;i<draws.size();i++){
				client.update("updateDrawModel",draws.get(i));
			}					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
