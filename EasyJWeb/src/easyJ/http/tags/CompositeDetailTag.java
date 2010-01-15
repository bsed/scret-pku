package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import easyJ.http.Globals;
import java.util.ArrayList;
import easyJ.system.data.UserPropertyRight;
import easyJ.common.Format;
import easyJ.common.BeanUtil;
import easyJ.system.service.UserColumnService;
import easyJ.common.validate.Validate;
import easyJ.common.validate.ValidateErrors;
import easyJ.business.proxy.DictionaryProxy;
import easyJ.system.service.HtmlClientComponentService;
import easyJ.common.validate.GenericValidator;
import easyJ.system.data.SysUserCache;

public class CompositeDetailTag extends TagSupport {
    public CompositeDetailTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {

            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            StringBuffer buffer = HtmlClientComponentService
                    .getCompositeDetail(request);
            out.print(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.SKIP_BODY;
    }
}
