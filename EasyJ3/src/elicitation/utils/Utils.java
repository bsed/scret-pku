package elicitation.utils;

import java.io.Reader;
import java.nio.charset.Charset;



import elicitation.model.user.SysUser;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author baipeng
 * Mar 6, 2009
 * <p> 
 * _PATH
 */
public class Utils {
	public static String ApplicationContext_PATH = "applicationContext.xml";
	public static String SQL_MAP_CONFIG_PATH = "sqlMapConfig.xml";
	public static String STRUTS_PATH = "struts.xml";
	
	
	public static String USER_PATH = "ibatis_user.xml";
	public static String ACCOUNT_PATH = "ibatis_account.xml";
	public static String PRODUCT_PATH = "ibatis_product.xml";
	public static String SHOPPING_PATH ="ibatis_shopping.xml";
	public static String ACTION_SUC= "success";  // for the struts-xxx.xml result ="".
	
	public static int MAX_LEN_ABSTRACTS_VIEW = 100;
	//Index-Info
	public static String INDEX_DIR ="F:\\IndexTest"; //TODO 指定索引目录
	//Search-Type
	public static String SEARCH_SQL ="sqlsearch";
	public static String SEARCH_FULLTEXT ="fulltextsearch";
	public static int    MAX_HITS = 100;
	
	public static String REDIRECT_ACTION = "redirctaction";
	
	private static SqlMapClient sqlMapClient = null;
	private static SqlMapClient localClient = null;
	
	
	public static SqlMapClient getMapClient(){
		if(sqlMapClient != null) return sqlMapClient;		
		try {
			Resources.setCharset(Charset.forName("UTF-8"));
	        Reader reader = Resources.getResourceAsReader(SQL_MAP_CONFIG_PATH);	    
	        
	        sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
	        
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return sqlMapClient;
	}
	public static SysUser getSessionUser(){
		return (SysUser) ActionContext.getContext().getSession().get("user");
	}
	/**
	 * baipeng 
	 *       just for testing in my local computer.
	 * @return
	 * @deprecated
	 */
	public static SqlMapClient getLocalMapClient(){
		if(localClient !=null) return localClient;
		try {
	        Reader reader = Resources.getResourceAsReader("localMapConfig.xml");
	        localClient = SqlMapClientBuilder.buildSqlMapClient(reader);	        
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return localClient;
	}

}

