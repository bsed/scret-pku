package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import easyJ.system.service.HtmlClientComponentService;

public class EditTag extends TagSupport {
    private String columnsPerLine = "3";

    private String start; // 用来表示从第几个property开始显示

    private String size; // 用来表示总共显示多少个property

    private String hint = "N"; // 用来表示是否在每个字段后面显示填写内容的说明。默认不显示

    private String hintType = "";// 用来表示用什么样的方式来显示提示。可以是鼠标移动提示，可以是直接提示。

    public EditTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            int istart = 0;
            int isize = -1;
            if (start != null)
                istart = Integer.parseInt(start);
            if (size != null)
                isize = Integer.parseInt(size);
            out.println(HtmlClientComponentService.getEdit(request,
                    columnsPerLine, istart, isize, hint, hintType).toString());
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

    public void setColumnsPerLine(String columnsPerLine) {
        this.columnsPerLine = columnsPerLine;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setHintType(String hintType) {
        this.hintType = hintType;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
