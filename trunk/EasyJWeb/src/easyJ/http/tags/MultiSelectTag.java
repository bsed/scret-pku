package easyJ.http.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import easyJ.common.BeanUtil;
import easyJ.system.service.HtmlClientComponentService;

public class MultiSelectTag extends TagSupport {
    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.SKIP_BODY;
    }
}
