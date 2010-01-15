package easyJ.http.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import easyJ.common.EasyJConfiguration;
import easyJ.common.EasyJException;

/**
 * 这个类用来在web容器启动的时候加载配置文件用的。
 * @author liufeng
 *
 */
public class ConfigManager implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		String root = sce.getServletContext().getRealPath("");
		EasyJConfiguration.setRoot(root);
		try {
			EasyJConfiguration.load("xml");
			EasyJConfiguration.loadConf("xml");
		} catch (EasyJException e) {
			e.printStackTrace();
		}
	}

}
