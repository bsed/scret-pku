package elicitation.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.spi.CharsetProvider;
import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.mysql.jdbc.StringUtils;

public class StringTypeHandlerCallback implements TypeHandlerCallback {

	public Object getResult(ResultGetter arg0) throws SQLException {
		System.out.println("Getter="+arg0.getString());
		try {
			 
			String tmp = new String(arg0.getString().getBytes("latin-1"),"UTF-8");
			System.out.println("TMP="+tmp);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arg0.getString();
	}

	public void setParameter(ParameterSetter setter, Object arg1)
			throws SQLException {
		System.out.println("parameter="+arg1);
		if (arg1 instanceof String){
			String tmp = (String)arg1;
			try {
				
				tmp = new String(tmp.getBytes("UTF-8"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("paraTmp="+tmp);
			setter.setString(tmp);
		}
		
	}

	public Object valueOf(String arg0) {
		System.out.println("valueOf="+arg0);
		return arg0;
	}

}
