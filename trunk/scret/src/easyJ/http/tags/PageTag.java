package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import easyJ.system.service.HtmlClientComponentService;
import javax.servlet.jsp.JspException;

public class PageTag extends TagSupport {

    private String position;
    private String editable;
    
    public void setPosition(String position) {
        this.position = position;
    }

    public void setEditable(String editable) {
    	this.editable = editable;
    }
    public PageTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            /*
             * position
             * 代表是执行哪个部分的查询，包括主查询和弹出窗口查询，对应两个js文件，Data.js和PopUpWindow.js
             */
            boolean editable = false;
            if (this.editable != null) {
            	editable = Boolean.parseBoolean(this.editable);
            }
            out.print(HtmlClientComponentService.getPage(request, position,editable));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return this.SKIP_BODY; // 当标签包括的内容为空时返回SKIP_BODY，表示标签功能已执行完成
    }

    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

}
