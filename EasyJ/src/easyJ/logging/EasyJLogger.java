package easyJ.logging;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import easyJ.common.EasyJConfiguration;
import easyJ.common.SystemEnviroment;

public class EasyJLogger {

	// Log文件的路径及格式：放到当前web目录下的log目录中 %g 代表自动编号
	private static final String LOG_DIR = EasyJConfiguration.getRoot()
			+ SystemEnviroment.fileSeparator + "log";
	private static final String LOG_FILE = LOG_DIR
			+ SystemEnviroment.fileSeparator + "EasyJ"
			+ System.currentTimeMillis() + "-%g.log";
	
//	private static final String logFile = "%hEasyJ"
//	+ System.currentTimeMillis() + "-%g.log";
	
//	private static final String logFile = "%h/topo%g.log";

	// 写入到任何一个文件的最大字节数
	private static final int limit = 1000 * 1024;
	// 要使用的文件数
	private static final int count = 20;
	// 指定 append 模式
	private static final boolean append = true;
	// 默认LOG级别
	private static final Level defaultLevel = Level.INFO;

	/**
	 * 从Logger存储器取得Logger对象。
	 * 
	 * @param strClassName
	 * @return
	 */
	public synchronized static Logger getLogger(String strClassName) {
		Logger logger = null;
		
		File logDir = new File(LOG_DIR);
		if (!logDir.exists()) {
			logDir.mkdir();
		}
		
		if (strClassName == null) { // 取当前Logger类默认的Logger对象
			String strDefaultClassName = EasyJLogger.class.getName();
			logger = Logger.getLogger(strDefaultClassName);
		} else {
			logger = Logger.getLogger(strClassName);
		}

		// 添加默认的文件处理句柄，如果不存在，添加默认的ConsoleHandler
		Handler handler = getFileHandler();
		if (handler == null) {
			handler = new ConsoleHandler();
		}
		logger.addHandler(handler);
		return logger;
	}

	/**
	 * 取默认的文件处理句柄。
	 * 
	 * @return
	 */
	private static FileHandler getFileHandler() {
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler(LOG_FILE, limit, count, append);
		} catch (SecurityException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		if (fileHandler != null) {
			fileHandler.setLevel(defaultLevel);
			fileHandler.setFormatter(new EasyJFormatter());
		}
		return fileHandler;
	}

	/**
	 * Logger格式化处理器。
	 * 
	 * @author xinwuhen
	 */
	final static class EasyJFormatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			StringBuffer strOutput = new StringBuffer();

			strOutput.append("[");
			strOutput.append(getFormattedTime(record.getMillis(),
					"yyyy-MM-dd HH:mm:SS, sss"));
			strOutput.append("], ");
			strOutput.append(record.getLevel());
			strOutput.append(", ");
			strOutput.append(record.getSourceClassName());
			strOutput.append(",");
			strOutput.append(record.getSourceMethodName());
			strOutput.append(" ");
			strOutput.append(record.getMessage());
			strOutput.append(":");
			strOutput.append(System.getProperty("line.separator"));
			Throwable throwAble = record.getThrown();
			if (throwAble != null) {
				strOutput.append(getThrowAbleInfo(throwAble));
			}

			return strOutput.toString();
		}

		/**
		 * 根据指定格式转换时间。
		 * 
		 * @param lngMillTimes
		 * @param strTimeFormat
		 * @return
		 */
		private String getFormattedTime(long lngMillTimes, String strTimeFormat) {
			Date lvDate = new Date();
			lvDate.setTime(lngMillTimes);
			SimpleDateFormat lvFormat = new SimpleDateFormat(strTimeFormat,
					Locale.CHINA);
			return lvFormat.format(lvDate);
		}

		/**
		 * 取例外对象的信息。
		 * 
		 * @param throwAble
		 * @return
		 */
		private String getThrowAbleInfo(Throwable throwAble) {
			String throwAbleInfo = "";
			StackTraceElement[] ste = throwAble.getStackTrace();
			if (ste != null) {
				final String strLineSeparator = System
						.getProperty("line.separator");
				StringBuffer sbStackTraceEle = new StringBuffer();
				for (StackTraceElement stackTraceElement : ste) {
					sbStackTraceEle.append("        at ");
					sbStackTraceEle.append(stackTraceElement.toString());
					sbStackTraceEle.append(strLineSeparator);
				}
				throwAbleInfo = throwAble.toString() + strLineSeparator
						+ sbStackTraceEle;
			}
			return throwAbleInfo;
		}
	}
}
