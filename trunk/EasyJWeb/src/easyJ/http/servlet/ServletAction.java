package easyJ.http.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.pku.dr.requirement.elicitation.action.LoginAction;
import cn.edu.pku.dr.requirement.elicitation.system.Context;
import easyJ.business.proxy.DataProxy;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.common.validate.ValidateErrors;
import easyJ.database.dao.OrderDirection;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Page;
import easyJ.http.Globals;
import easyJ.system.data.SysUser;
import easyJ.system.data.SysUserCache;

public abstract class ServletAction {
	/*存储当前用户要操作的类。*/
    protected String className; 

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected ServletContext application;

    /** 客户端传来的数据被封装在此。**/
    protected Object object; 

    protected Object lower; // 范围查询的时候范围低值存在这里。

    protected Object upper; // 范围查询的时候范围高值存在这里。

    protected String returnPath; // 存储返回的路径。

    protected String action; // 存储用户的动作。

    protected java.util.HashMap properties; // 从客户端传来的数据，放入一个HashMap中。

    protected Long userId; // 存储当前登陆用户的id

    protected SysUser user; // 存储当前登陆用户的信息

    protected ValidateErrors errors; // 存储对客户端传来数据校验之后的错误集合。

    protected DataProxy dp; // 用于对数据操作的DataProxy

    protected String returnMessage; // 如果是对页面的局部更新，则可能返回的是html代码片段。就通过returnMessage来返回。

    protected SysUserCache userCache; // 缓存和用户相关的信息。所拥有的类，字段，功能等数据。

    protected Context context; // 用来保存整个项目的上下文环境

    public void processReturn() throws EasyJException {
        /* 判断用户是否选择ajax方式来使用系统，集中处理用户的返回。 */
        if (!GenericValidator.isBlankOrNull(returnPath)) {
            RequestProcessor.forward(request, response, returnPath);
        } else {
            try {
                if (!GenericValidator.isBlankOrNull(returnMessage))
                    response.getWriter().print(returnMessage);
            } catch (IOException ex) {}
        }
    }

    public SysUser getUserFromCookie(HttpServletRequest request) {
        SysUser user = null;
        // 测试cookie，没有其他用处。
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        String namePassword = "";
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("__seforge_".equals(cookie.getName())) {
                user = new SysUser();
                namePassword = cookie.getValue();
                String t[] = namePassword.split(":");
                user.setUserName(t[0]);
                user.setPassword(t[1]);
            }
        }
        return user;
    }

    /**
     * 将所有的SingleDataAction 和
     * CompositeDataAction的共用变量进行设置.会在RequestProcessor中被调用
     * 
     * @param request
     *                HttpServletRequest
     * @param response
     *                HttpServletResponse
     * @throws EasyJException
     */
    public void process(HttpServletRequest request,
            HttpServletResponse response, ServletContext application)
            throws EasyJException {
        this.application = application;
        action = (String) request.getParameter(Globals.ACTION);
        System.out.println("action is:" + action);
        
        //如果没有设置web应用的根目录，则需要设置
//        if (WebConf.getRoot() == null) {
//        	String webRoot = request.getSession().getServletContext().getRealPath("");
//        	WebConf.setRoot(webRoot);
//        }
//        
       
        /* 在LoginAction中得到userCache，如果没有session则需要从cookie中读取，并将读取的内容去执行登陆操作 */
        userCache = (SysUserCache) request.getSession().getAttribute(
                Globals.SYS_USER_CACHE);
        if (userCache != null) {
            user = userCache.getUser();
            userId = user.getUserId();
            context = userCache.getContext();
            dp.setContext(context);
        } else {
            SysUser user = getUserFromCookie(request);
            // 说明cookie也没有，而且也不是注册相关的，则应该转向登陆界面。
            if (user == null) {
                // 如果是注册相关的则不做特殊处理，否则因为没有用户信息，所以要跳到登陆页面。这里是否注册相关需要再做修改，这种方法判断不好。
                if (action.toLowerCase().indexOf("register") < 0
                        && action.toLowerCase().indexOf("login") < 0) {
                    try {
                        String requestUrl = request.getRequestURL().toString();
                        response.sendRedirect(requestUrl.substring(0,
                                requestUrl.lastIndexOf("/"))
                                + "/Login.jsp");
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // 如果有cookie则执行登陆操作，将用户在本系统中的数据进行缓存。
                LoginAction lga = new LoginAction();
                lga.login(user);
            }
        }

        if (GenericValidator.isBlankOrNull(className)) {
            throw new EasyJException(
                    null,
                    "easyJ.http.servlet.RequestProcessor.processPopulate(HttpServletRequest request,HttpServletResponse response)",
                    "servlet没有能够得到要处理的数据，请查看生成客户端的代码", "服务器忙");
        }

        // returnPath应该是和function相关联的，每定义一个function的时候都要指定返回页面，也就是returnpath。
        returnPath = (String) request.getParameter(Globals.RETURN_PATH);

        /* 在进入此Action之前，ActionServlet已经对request做了处理，客户端数据都在REQUEST_PROPERTIES中 */
        properties = (HashMap) request.getAttribute(Globals.REQUEST_PROPERTIES);

        this.request = request;
        this.response = response;
        request.setAttribute(Globals.ACTION_NAME, this.getClass().getName());

        if (!encapsulateObject()) {
            return;
        }

        // 看看Obj是否存在，如果存在，则不用再次去通过getObject()来获得，这样效率稍微高一些
        if (object == null) {
            if (upper == null)
                upper = BeanUtil.getObject(className, properties, "upper",
                        errors);
            if (lower == null)
                lower = BeanUtil.getObject(className, properties, "lower",
                        errors);
        } else {
            if (upper == null)
                upper = BeanUtil.getObject(BeanUtil.cloneObject(object),
                        properties, "upper", errors);
            if (lower == null)
                lower = BeanUtil.getObject(BeanUtil.cloneObject(object),
                        properties, "lower", errors);
        }
        request.setAttribute(Globals.OBJECT, object);
        request.setAttribute(Globals.LOWER, lower);
        request.setAttribute(Globals.UPPER, upper);
        
    	BeanUtil.invokeMethod(this, action, null);
        // 处理返回
        processReturn();
        return;
    }

    public void newObject() throws EasyJException {
        // 设置当前登陆用户为创建人
        try {
            BeanUtil.setFieldValue(object, "creatorId", userId);
        } catch (Exception e) {

        }
        object = dp.create(object);
        request.setAttribute(Globals.OBJECT, object);
    }
    /**
     * baipeng 
     * for add new project and other objects.
     * @throws EasyJException
     */
    public void add()throws EasyJException {
    	System.out.println(object);
    	Class clazz = BeanUtil.getClass(className);
    	//dp.addObject(clazz,object);
    	dp.create(object);
    }
    public void edit() throws EasyJException {
    	/**
    	 * 如果是 新增project ，那么这里的object = Project,但是所有的field=null
    	 */
        Object primaryKey = BeanUtil.getPrimaryKeyValue(object);
        /* primaryKey为空则代表新增，否则代表编辑 */
        System.out.println("primaryKey is:" + primaryKey);
        if (primaryKey != null)
            object = dp.get(object);
        request.setAttribute(Globals.OBJECT, object);
    }

    public void delete() throws EasyJException {
        String[] primaryKeys = request.getParameterValues("check");
        /* 此时要进行主键的类型转换，因为从客户端传来的都是字符串类型的，而执行时应该按照主键原有的类型 */
        Class clazz = BeanUtil.getClass(className);
        dp.deleteBatch(clazz, primaryKeys);
        query();
    }

    public void query() throws EasyJException {
        String pageNo = request.getParameter(Globals.PAGENO);

        // 这里之所以要将主键设为空，是因为查询的时候用户是不知道主键的，所以不可能按照主键查询
        // 另外因为在执行完删除之后，还需要根据用户的条件进行查询，所以这个时候需要将删除时传过来的主键设空。
        // 否则查询结果会有问题。
        BeanUtil.setPrimaryKeyNull(lower);
        BeanUtil.setPrimaryKeyNull(upper);
        Integer pageNum = new Integer(1);
        if (!GenericValidator.isBlankOrNull(pageNo))
            pageNum = new Integer(pageNo);

        String orderByColumn = request.getParameter(Globals.ORDER_BY_COLUMN);
        String orderDirection = request.getParameter(Globals.ORDER_DIRECTION);

        // 设置查询的数据要把删除的数据过滤掉。
        try {
            BeanUtil.setFieldValue(lower, "useState", "Y");
            BeanUtil.setFieldValue(upper, "useState", "Y");
        } catch (Exception e) {

        }
        Page page = null;
        if (GenericValidator.isBlankOrNull(orderByColumn)) {
            page = dp.query(lower, upper, pageNum, null);
        } else {
            OrderRule or = new OrderRule(orderByColumn, OrderDirection
                    .parse(orderDirection));
            OrderRule[] ors = {
                or
            };
            page = dp.query(lower, upper, pageNum, ors);
        }

        request.setAttribute(Globals.PAGE, page);
    }

    public abstract boolean encapsulateObject() throws EasyJException;
}
