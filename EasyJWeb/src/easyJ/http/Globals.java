/*
 * $Header: /EasyJ/EasyJ/src/easyJ/http/Globals.java,v 1.3 2008/05/12 15:26:06
 * cvsadmin Exp $ $Revision: 1.3 $ $Date: 2008/05/12 15:26:06 $
 * ==================================================================== The
 * Apache Software License, Version 1.1 Copyright (c) 1999-2002 The Apache
 * Software Foundation. All rights reserved. Redistribution and use in source
 * and binary forms, with or without modification, are permitted provided that
 * the following conditions are met: 1. Redistributions of source code must
 * retain the above copyright notice, this list of conditions and the following
 * disclaimer. 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution. 3. The
 * end-user documentation included with the redistribution, if any, must include
 * the following acknowlegement: "This product includes software developed by
 * the Apache Software Foundation (http://www.apache.org/)." Alternately, this
 * acknowlegement may appear in the software itself, if and wherever such
 * third-party acknowlegements normally appear. 4. The names "The Jakarta
 * Project", "Struts", and "Apache Software Foundation" must not be used to
 * endorse or promote products derived from this software without prior written
 * permission. For written permission, please contact apache@apache.org. 5.
 * Products derived from this software may not be called "Apache" nor may
 * "Apache" appear in their names without prior written permission of the Apache
 * Group. THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR ITS CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ==================================================================== This
 * software consists of voluntary contributions made by many individuals on
 * behalf of the Apache Software Foundation. For more information on the Apache
 * Software Foundation, please see <http://www.apache.org/>.
 */

package easyJ.http;

import java.io.Serializable;

import cn.edu.pku.dr.requirement.elicitation.action.LoginAction;

/**
 * <p>
 * Global manifest constants for the entire Struts Framework.
 * </p>
 * <p>
 * Many of these constants were initially defined in <code>Action</code>, but
 * were moved here so that they could be referenced without referencing the
 * <code>Action</code> class itself. For backwards compatibility, constant
 * references there point at this class, and the constant values themselves have
 * not changed.
 * </p>
 * 
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2008/05/12 15:26:06 $
 */

public class Globals implements Serializable {

    // ----------------------------------------------------- Manifest Constants

    /**
     * The context attributes key under which our <code>ActionServlet</code>
     * instance will be stored.
     * 
     * @since Struts 1.1
     */
    public static final String DYNAMIC_SERVLET_KEY = "easyJ.http.Globals.DYNAMIC_SERVLET";

    /**
     * <p>
     * The base of the context attributes key under which our
     * <code>ModuleConfig</code> data structure will be stored. This will be
     * suffixed with the actual module prefix (including the leading "/"
     * character) to form the actual attributes key.
     * </p>
     * <p>
     * For each request processed by the controller servlet, the
     * <code>ModuleConfig</code> object for the module selected by the request
     * URI currently being processed will also be exposed under this key as a
     * request attribute.
     * </p>
     * 
     * @since Struts 1.1
     * @deprecated Use MODULE_KEY
     */
    public static final String APPLICATION_KEY = "easyJ.http.Globals.MODULE";

    /**
     * <p>
     * The request attributes key under which a boolean <code>true</code>
     * value should be stored if this request was cancelled.
     * </p>
     * 
     * @since Struts 1.1
     */
    public static final String CANCEL_KEY = "easyJ.http.Globals.CANCEL";

    /**
     * <p>
     * The base of the context attributes key under which our
     * <code>ModuleConfig</code> data structure will be stored. This will be
     * suffixed with the actual module prefix (including the leading "/"
     * character) to form the actual attributes key.
     * </p>
     * <p>
     * For each request processed by the controller servlet, the
     * <code>ModuleConfig</code> object for the module selected by the request
     * URI currently being processed will also be exposed under this key as a
     * request attribute.
     * </p>
     * 
     * @since Struts 1.1
     */
    public static final String MODULE_KEY = "easyJ.http.Globals.MODULE";

    /**
     * The context attributes key under which our <strong>default</strong>
     * configured data source (which must implement
     * <code>javax.sql.DataSource</code>) is stored, if one is configured for
     * this module.
     */
    public static final String DATA_SOURCE_KEY = "easyJ.http.Globals.DATA_SOURCE";

    /**
     * The request attributes key under which your action should store an
     * <code>easyJ.http.Globals.ActionErrors</code> object, if you are using
     * the corresponding custom tag library elements.
     */
    public static final String ERROR_KEY = "easyJ.http.Globals.ERROR";

    /**
     * The request attributes key under which Struts custom tags might store a
     * <code>Throwable</code> that caused them to report a JspException at
     * runtime. This value can be used on an error page to provide more detailed
     * information about what really went wrong.
     */
    public static final String EXCEPTION_KEY = "easyJ.http.Globals.EXCEPTION";

    /**
     * The context attributes key under which our
     * <code>easyJ.http.Globals.ActionFormBeans</code> collection is normally
     * stored, unless overridden when initializing our ActionServlet.
     * 
     * @deprecated Replaced by collection in ModuleConfig
     */
    public static final String FORM_BEANS_KEY = "easyJ.http.Globals.FORM_BEANS";

    /**
     * The context attributes key under which our
     * <code>easyJ.http.Globals.ActionForwards</code> collection is normally
     * stored, unless overridden when initializing our ActionServlet.
     * 
     * @deprecated Replaced by collection in ModuleConfig.
     */
    public static final String FORWARDS_KEY = "easyJ.http.Globals.FORWARDS";

    /**
     * The session attributes key under which the user's selected
     * <code>java.util.Locale</code> is stored, if any. If no such attribute
     * is found, the system default locale will be used when retrieving
     * internationalized messages. If used, this attribute is typically set
     * during user login processing.
     */
    public static final String LOCALE_KEY = "easyJ.http.Globals.LOCALE";

    /**
     * The request attributes key under which our <code>easyJ.httpMapping</code>
     * instance is passed.
     */
    public static final String MAPPING_KEY = "easyJ.http.Globals.mapping.instance";

    /**
     * The context attributes key under which our
     * <code>easyJ.http.Globals.ActionMappings</code> collection is normally
     * stored, unless overridden when initializing our ActionServlet.
     * 
     * @deprecated Replaced by collection in ModuleConfig
     */
    public static final String MAPPINGS_KEY = "easyJ.http.Globals.MAPPINGS";

    /**
     * The request attributes key under which your action should store an
     * <code>easyJ.http.Globals.ActionMessages</code> object, if you are using
     * the corresponding custom tag library elements.
     * 
     * @since Struts 1.1
     */
    public static final String MESSAGE_KEY = "easyJ.http.Globals.ACTION_MESSAGE";

    /**
     * <p>
     * The base of the context attributes key under which our module
     * <code>MessageResources</code> will be stored. This will be suffixed
     * with the actual module prefix (including the leading "/" character) to
     * form the actual resources key.
     * </p>
     * <p>
     * For each request processed by the controller servlet, the
     * <code>MessageResources</code> object for the module selected by the
     * request URI currently being processed will also be exposed under this key
     * as a request attribute.
     * </p>
     */
    public static final String MESSAGES_KEY = "easyJ.http.Globals.MESSAGE";

    /**
     * The request attributes key under which our multipart class is stored.
     */
    public static final String MULTIPART_KEY = "easyJ.http.Globals.mapping.multipartclass";

    /**
     * <p>
     * The base of the context attributes key under which an array of
     * <code>PlugIn</code> instances will be stored. This will be suffixed
     * with the actual module prefix (including the leading "/" character) to
     * form the actual attributes key.
     * </p>
     * 
     * @since Struts 1.1
     */
    public static final String PLUG_INS_KEY = "easyJ.http.Globals.PLUG_INS";

    /**
     * <p>
     * The base of the context attributes key under which our
     * <code>RequestProcessor</code> instance will be stored. This will be
     * suffixed with the actual module prefix (including the leading "/"
     * character) to form the actual attributes key.
     * </p>
     * 
     * @since Struts 1.1
     */
    public static final String REQUEST_PROCESSOR_KEY = "easyJ.http.Globals.REQUEST_PROCESSOR";

    /**
     * The context attributes key under which we store the mapping defined for
     * our controller serlet, which will be either a path-mapped pattern (<code>/action/*</code>)
     * or an extension mapped pattern (<code>*.do</code>).
     */
    public static final String SERVLET_KEY = "easyJ.http.Globals.SERVLET_MAPPING";

    /**
     * The session attributes key under which our transaction token is stored,
     * if it is used.
     */
    public static final String TRANSACTION_TOKEN_KEY = "easyJ.http.Globals.TOKEN";

    /**
     * The page attributes key under which xhtml status is stored. This may be
     * "true" or "false". When set to true, the html tags output xhtml.
     * 
     * @since Struts 1.1
     */
    public static final String XHTML_KEY = "easyJ.http.Globals.XHTML";

    public static final String ACTION = "ACTION";

    /**
     * DATA是用来表示当前用户这个请求是要处理哪个数据。ACTION用来表示用户当前的动作，当前的动作也决定了
     * 在进行数据封装的时候需要封装成1个还是2个VO。 例如：用户的请求为 servlet?DATA=Student&ACTION=QUERY.
     * UPPER和LOWER分别代表在执行查询操作的时候得到的查询上下界
     * OBJECT代表在进行其它操作的时候得到的界面数据，得到的结果全部放入request当中
     */
    public static final String CLASS_NAME = "easyJ.http.Globals.CLASS_NAME";

    public static final String ACTION_NAME = "easyJ.http.Globals.ACTION_NAME";

    public static final String UPPER = "easyJ.http.Globals.UPPER";

    public static final String LOWER = "easyJ.http.Globals.LOWER";

    public static final String OBJECT = "easyJ.http.Globals.OBJECT";

    public static final String QUERY_PROPERTIES = "easyJ.http.Globals.QUERY_PROPERTIES";

    public static final String EDIT_PROPERTIES = "easyJ.http.Globals.EDIT_PROPERTIES";

    public static final String DISPLAY_PROPERTIES = "easyJ.http.Globals.DISPLAY_PROPERTIES";

    public static final String VALIDATE_ERRORS = "easyJ.http.Globals.VALIDATE_ERRORS";

    public static final String RESULT_LIST = "easyJ.http.Globals.RESULT_LIST";

    public static final String PAGE = "easyJ.http.Globals.PAGE";

    public static final String PAGENO = "PAGENO";

    public static final String ORDER_BY_COLUMN = "ORDER_BY_COLUMN";

    public static final String ORDER_DIRECTION = "ORDER_DIRECTION";

    public static final String TEMPLATE = "easyJ.http.Globals.TEMPLATE";

    public static final String REQUEST_PROPERTIES = "easyJ.http.Globals.REQUEST_PROPERTIES";

    /* 下面的action代表在servletAction中执行的方法的名字，见SingleDataAction */
    public static final String QUERY_ACTION = "query";

    public static final String NEW_ACTION = "newObject";

    public static final String UPDATE_ACTION = "update";

    public static final String EDIT_ACTION = "edit";

    public static final String DELETE_ACTION = "delete";

    public static final String RETURN_PATH = "easyJ.http.Globals.RETURN_PATH";
    /**
     * @see  LoginAction#login() 
     */
    public static final String SYS_USER_CACHE = "easyJ.http.Globals.SYS_USER_CACHE";

    public static final String SINGLE_DATA_QUERY_RETURN_PARTH = "/WEB-INF/template/AjaxSingleDataQuery.jsp";
    
    public static final String SINGLE_DATA_QUERY_EDIT_RETURN_PARTH = "/WEB-INF/template/AjaxSingleDataQueryEdit.jsp";

    public static final String SINGLE_DATA_DELETE_RETURN_PARTH = "/WEB-INF/template/AjaxSingleDataQuery.jsp";

    public static final String SINGLE_DATA_EDIT_RETURN_PARTH = "/WEB-INF/template/AjaxSingleDataEdit.jsp";
    
    public static final String SINGLE_DATA_ADD_RETURN_PATH = "/WEB-INF/template/AjaxSingleDataADD.jsp"; 

    public static final String SINGLE_DATA_SAVE_RETURN_PARTH = "/WEB-INF/template/AjaxSingleDataEdit.jsp";

    public static final String COMPOSITE_DATA_QUERY_RETURN_PARTH = "/WEB-INF/template/AjaxCompositeDataQuery.jsp";

    public static final String COMPOSITE_DATA_DELETE_RETURN_PARTH = "/WEB-INF/template/AjaxCompositeDataQuery.jsp";

    public static final String COMPOSITE_DATA_EDIT_RETURN_PARTH = "/WEB-INF/template/AjaxCompositeDataEdit.jsp";

    public static final String COMPOSITE_DATA_SAVE_RETURN_PARTH = "/WEB-INF/template/AjaxCompositeDataEdit.jsp";

    /* 下面是function所在位置在数据字典表Dictionary中的值 */
    public static final String FUNCTION_QUERY = "1";

    public static final String FUNCTION_DISPLAY = "2";

    public static final String FUNCTION_EDIT = "3";

    public static final String FUNCTION_LIST = "4";
    
    public static final String FUNCTION_LIST_EDIT = "5";
    

}
