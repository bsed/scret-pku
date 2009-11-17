package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import easyJ.system.service.HtmlClientComponentService;

public class QueryTag extends TagSupport {
    private String columnsPerLine = "3";

    public QueryTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            out.print(HtmlClientComponentService.getQuery(request,
                    columnsPerLine));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (easyJ.common.EasyJException ee) {
            ee.printStackTrace();
        }
        return this.SKIP_BODY; // 当标签包括的内容为空时返回SKIP_BODY，表示标签功能已执行完成
    }

    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

    public String getColumnsPerLine() {
        return columnsPerLine;
    }

    public void setColumnsPerLine(String columnsPerLine) {
        this.columnsPerLine = columnsPerLine;
    }

}
