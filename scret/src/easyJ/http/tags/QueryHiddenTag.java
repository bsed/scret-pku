package easyJ.http.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import easyJ.system.service.HtmlClientComponentService;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.jsp.JspException;

public class QueryHiddenTag extends TagSupport {
    public QueryHiddenTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            out.print(HtmlClientComponentService.getQueryHidden(request));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return this.SKIP_BODY; // 当标签包括的内容为空时返回SKIP_BODY，表示标签功能已执行完成
    }

    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

}
