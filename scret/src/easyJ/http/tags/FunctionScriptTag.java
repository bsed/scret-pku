package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import easyJ.system.service.HtmlClientComponentService;

public class FunctionScriptTag extends TagSupport {
    /* 如果没有指定className，则说明要适合所有类的功能 */
    private String className;

    private String pageName;

    public FunctionScriptTag() {}

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            out.println(HtmlClientComponentService.getFunctionScript(request,
                    pageName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (easyJ.common.EasyJException ee) {

        }
        return this.SKIP_BODY; // 当标签包括的内容为空时返回SKIP_BODY，表示标签功能已执行完成
    }

    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

}
