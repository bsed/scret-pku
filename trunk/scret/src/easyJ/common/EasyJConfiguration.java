package easyJ.common;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


/**
 * 用来得到配置文件的设置，在这里暂时只支持xml格式的，可以支持properties格式的。
 * @author liufeng
 *
 */

public class EasyJConfiguration {
	public static final String CONF_FOLDER = "conf";
	private static  CompositeConfiguration config =  new CompositeConfiguration();
	private static  String root =  null;
	
	public static String getRoot() {
		return EasyJConfiguration.root;
	}
	
	/**
	 * 设置此web应用的根目录
	 * @param root
	 */
	public static void setRoot(String root) {
		EasyJConfiguration.root = root;
	}

	/**
	 * 默认加载root下的xml文件
	 * @throws EasyJException
	 */
	public static void load(final String postFix) throws EasyJException{
		File rootFile = new File(root);
		if (rootFile.isDirectory()) {
			File[] files = rootFile.listFiles( new FileFilter() {

				public boolean accept(File pathname) {
					if (pathname.getName().endsWith(postFix))
						return true;
					return false;
				}});

			for (File file : files) {
				load(file);
			}
		}
	}
	
	/**
	 * 默认加载root下conf目录下的xml文件
	 * @throws EasyJException
	 */
	public static void loadConf(final String postFix) throws EasyJException{
		File confFile = new File(cat(root, CONF_FOLDER));
		if (confFile.isDirectory()) {
			File[] files = confFile.listFiles( new FileFilter() {

				public boolean accept(File pathname) {
					if (pathname.getName().endsWith(postFix))
						return true;
					return false;
				}});

			for (File file : files) {
				load(file);
			}
		}
	}
	
	public static void reload(final String postFix) throws EasyJException {
		config.clear();
		load(postFix);
		loadConf(postFix);
	}
	
	/**
	 * 此方法将一些路径上的文件夹组成一个路径
	 * @param paths
	 * @return
	 */
	public static String cat (String... paths) {
		StringBuffer result = new StringBuffer();
		int i = 0;
		int len = paths.length;
		for (String path : paths) {
			result.append(path);
			if (i != (len - 1)) result.append(SystemEnviroment.fileSeparator);
			i++;
		}
		return result.toString();
	}
	
//	/**
//	 * 此方法加载制定的fileName，应该是绝对路径
//	 * @param fileName     需要加载的文件
//	 * @throws EasyJException
//	 */
//	public static void load (String fileName) throws EasyJException{
//		try {
//			File file = new File(fileName);
//			if (!file.exists()) {
//				EasyJException ee = new EasyJException(null, "",
//						"the configure file " + fileName + 
//						" does not exist, please check it", "服务器忙");
//				throw ee;
//			}
//			Configuration conf = new XMLConfiguration(fileName);
//			config.addConfiguration(conf);
//		} catch (ConfigurationException e) {
//			EasyJException ee = new EasyJException(e, "",
//					"the configure file " + fileName + 
//					" are malformed, please check it", "服务器忙");
//			throw ee;
//		}
//	}
	
	/**
	 * 加载一个文件，作为配置文件
	 * @param file
	 * @throws EasyJException
	 */
	public static void load (File file) throws EasyJException{
		try {
			Configuration conf = new XMLConfiguration(file);
			config.addConfiguration(conf);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			EasyJException ee = new EasyJException(e, "",
					"the configure file " + file.getAbsolutePath() + 
					" are malformed, please check it", "服务器忙");
			throw ee;
		}
	}
	
	/**
	 * 此方法得到此web应用配置文件的所有内容
	 * @return
	 */
	public static Configuration getConfig() {
		return config;
	}
}
