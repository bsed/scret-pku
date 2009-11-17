package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import easyJ.system.service.HtmlClientComponentService;

public class DisplayForumTag extends TagSupport {
    public DisplayForumTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            out.print(HtmlClientComponentService.getForumDisplay(request));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (easyJ.common.EasyJException ee) {

        }
        return this.SKIP_BODY;
    }
}
