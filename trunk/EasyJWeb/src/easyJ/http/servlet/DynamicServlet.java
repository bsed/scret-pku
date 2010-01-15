package easyJ.http.servlet;

import easyJ.http.config.ModuleConfig;
import easyJ.http.config.ControllerConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import easyJ.http.Globals;
import easyJ.logging.EasyJLogger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import easyJ.common.*;
import javax.servlet.ServletContext;

public class DynamicServlet extends HttpServlet {
	public final static Logger LOG = EasyJLogger.getLogger(null);

	public DynamicServlet() {
	}

	public void init() {
		String prefix = "";
		ModuleConfig moduleConfig = new ModuleConfig(prefix);
		defaultControllerConfig(moduleConfig);
		getServletContext().setAttribute(Globals.MODULE_KEY + prefix,
				moduleConfig);
	}

	protected ModuleConfig getModuleConfig(HttpServletRequest request) {

		ModuleConfig config = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);
		if (config == null) {
			config = (ModuleConfig) getServletContext().getAttribute(
					Globals.MODULE_KEY);
		}
		return (config);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		process(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		process(request, response);

	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		/**
		 * 应该包括以下内容： 将客户端的数据封装入VALUE_OBJECT，装入之前要进行数据校验工作
		 * 抽取出用户希望访问的服务，并执行相应的服务，（执行服务的时候需要验证权限）
		 * 将得到的更新之后的数据缓存，并且得到和数据相关的操作，也进行缓存。 得到访问完之后应该跳转到的页面（从用户提交的请求得到），并进行跳转
		 */
		// Wrap multipart requests with a special wrapper
		try {
			ServletContext application = getServletContext();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			RequestProcessor.process(request, response, application);
		} catch (EasyJException ex) {
			StringWriter sw = new StringWriter();
			PrintWriter writer = new PrintWriter(sw);
			Throwable root = null;

			if (ex.getOriginalException() != null)
				root = ex.getOriginalException();
			else
				root = ex;

			root.printStackTrace();
			root.printStackTrace(writer);
			LOG.info(ex.getLogMessage());
			LOG.severe(sw.toString());
		} catch (ServletException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void defaultControllerConfig(ModuleConfig config) {

		String value = null;
		ControllerConfig cc = config.getControllerConfig();

		value = getServletConfig().getInitParameter("bufferSize");
		if (value != null) {
			cc.setBufferSize(Integer.parseInt(value));
		}

		value = getServletConfig().getInitParameter("content");
		if (value != null) {
			cc.setContentType(value);
		}

		value = getServletConfig().getInitParameter("locale");
		// must check for null here
		if (value != null) {
			if ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)) {
				cc.setLocale(true);
			} else {
				cc.setLocale(false);
			}
		}

		value = getServletConfig().getInitParameter("maxFileSize");
		if (value != null) {
			cc.setMaxFileSize(value);
		}

		value = getServletConfig().getInitParameter("nocache");
		if (value != null) {
			if ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)) {
				cc.setNocache(true);
			} else {
				cc.setNocache(false);
			}
		}

		value = getServletConfig().getInitParameter("multipartClass");
		if (value != null) {
			cc.setMultipartClass(value);
		}

		value = getServletConfig().getInitParameter("tempDir");
		if (value != null) {
			cc.setTempDir(value);
		}

	}

}
