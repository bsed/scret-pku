package easyJ.http.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.http.Globals;
import easyJ.http.config.ModuleConfig;
import easyJ.http.upload.CommonsMultipartRequestHandler;
import easyJ.http.upload.MultipartRequestHandler;
import easyJ.http.upload.MultipartRequestWrapper;

public class RequestProcessor {
    public RequestProcessor() {

    }

    protected static ModuleConfig moduleConfig = null;

    protected static void process(HttpServletRequest request,
            HttpServletResponse response, ServletContext application)
            throws IOException, ServletException, EasyJException {

        // Wrap multipart requests with a special wrapper 对传输文件的封装
        request = processMultipart(request);
        // Set the content type and no-caching headers if requested

        rightValidate(request, response);

        processPopulate(request, response);

        processAction(request, response, application);
    }

    public static ServletAction getAction(HttpServletRequest request,
            HttpServletResponse response) throws EasyJException {
        String URI = request.getRequestURI();
        String context = request.getContextPath() + "/";
        String actionName = URI.substring(context.length(), URI.indexOf(".do"));
        ServletAction action = null;
        action = (ServletAction) BeanUtil.getEmptyObject(actionName);
        return action;
    }

    public static void processAction(HttpServletRequest request,
            HttpServletResponse response, ServletContext application)
            throws IOException, ServletException, EasyJException {
        ServletAction action = getAction(request, response);
        action.process(request, response, application);
    }

    /**
     * check whether this request is legal.
     * 
     * @param request
     *                HttpServletRequest
     * @param response
     *                HttpServletResponse
     * @throws EasyJException
     */
    public static void rightValidate(HttpServletRequest request,
            HttpServletResponse response) throws EasyJException {

    }

    /**
     * this method get the action the client want to run first, then judge
     * whether this request need further deal if need then send the request to a
     * class just like action in struts,and in this class the programmer can
     * deal with the request, and invoke the method manually.
     * 
     * @param request
     *                HttpServletRequest
     */
    protected static void functionInvoke(HttpServletRequest request,
            HttpServletResponse response) throws EasyJException {
        String templateName = (String) request.getParameter(Globals.TEMPLATE);
    }

    protected static void forward(HttpServletRequest request,
            HttpServletResponse response, String returnPath)
            throws EasyJException {
        RequestDispatcher rd = request.getRequestDispatcher(returnPath);
        try {
            rd.forward(request, response);
        } catch (IOException ex2) {
            ex2.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.http.servlet.RequestProcessor.forward(HttpServletRequest request,HttpServletResponse response)";
            String logMessage = "转向目标" + returnPath + " 抛出IOException";
            EasyJException ee = new EasyJException(ex2, location, logMessage,
                    clientMessage);
            throw ee;
        } catch (ServletException ex2) {
            ex2.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.http.servlet.RequestProcessor.forward(HttpServletRequest request,HttpServletResponse response)";
            String logMessage = "转向目标" + returnPath + " 抛出ServletException";
            EasyJException ee = new EasyJException(ex2, location, logMessage,
                    clientMessage);
            throw ee;
        }

    }

    protected static void processPopulate(HttpServletRequest request,
            HttpServletResponse response) throws EasyJException,
            ServletException {
        HashMap properties = new HashMap();
        // Iterator of parameter names
        Enumeration names = null;
        // Map for multipart parameters
        Map multipartParameters = null;

        String contentType = request.getContentType();
        String method = request.getMethod();
        boolean isMultipart = false;

        if ((contentType != null)
                && (contentType.startsWith("multipart/form-data"))
                && (method.equalsIgnoreCase("POST"))) {

            // Obtain a MultipartRequestHandler
            MultipartRequestHandler multipartHandler = new CommonsMultipartRequestHandler();

            if (multipartHandler != null) {
                isMultipart = true;
                multipartHandler.handleRequest(request);
                // stop here if the maximum length has been exceeded
                Boolean maxLengthExceeded = (Boolean) request
                        .getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
                if ((maxLengthExceeded != null)
                        && (maxLengthExceeded.booleanValue())) {
                    return;
                }
                // retrive form values and put into properties
                multipartParameters = getAllParametersForMultipartRequest(
                        request, multipartHandler);
                names = Collections.enumeration(multipartParameters.keySet());
            }
        }

        if (!isMultipart) {
            names = request.getParameterNames();
        }

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String stripped = name;
            if (isMultipart) {
                properties.put(stripped, multipartParameters.get(name));
            } else {
                properties.put(stripped, request.getParameterValues(name));
            }
        }

        request.setAttribute(Globals.REQUEST_PROPERTIES, properties);
    }

    /**
     * Create a map containing all of the parameters supplied for a multipart
     * request, keyed by parameter name. In addition to text and file elements
     * from the multipart body, query string parameters are included as well.
     * 
     * @param request
     *                The (wrapped) HTTP request whose parameters are to be
     *                added to the map.
     * @param multipartHandler
     *                The multipart handler used to parse the request.
     * @return the map containing all parameters for this multipart request.
     */
    private static Map getAllParametersForMultipartRequest(
            HttpServletRequest request, MultipartRequestHandler multipartHandler) {
        Map parameters = new HashMap();
        Enumeration enum1;

        Hashtable elements = multipartHandler.getAllElements();
        enum1 = elements.keys();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            parameters.put(key, elements.get(key));
        }
        /* 取得通过get方式传过来的数据，也就是query的数据 */
        if (request instanceof MultipartRequestWrapper) {
            request = ((MultipartRequestWrapper) request).getRequest();
            enum1 = request.getParameterNames();
            while (enum1.hasMoreElements()) {
                String key = (String) enum1.nextElement();
                parameters.put(key, request.getParameterValues(key));
            }
        }

        return parameters;
    }

    protected static HttpServletRequest processMultipart(
            HttpServletRequest request) {

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return (request);
        }

        String contentType = request.getContentType();
        if ((contentType != null)
                && contentType.startsWith("multipart/form-data")) {
            return (new MultipartRequestWrapper(request));
        } else {
            return (request);
        }

    }

    protected static void processContent(HttpServletRequest request,
            HttpServletResponse response) {

        String contentType = moduleConfig.getControllerConfig()
                .getContentType();
        if (contentType != null) {
            response.setContentType(contentType);
        }

    }

    protected static void processNoCache(HttpServletRequest request,
            HttpServletResponse response) {

        if (moduleConfig.getControllerConfig().getNocache()) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 1);
        }

    }

}
