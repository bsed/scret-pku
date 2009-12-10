package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import easyJ.system.service.HtmlClientComponentService;

public class DisplayTag extends TagSupport {
	private String editable;
	
	public void setEditable(String editable) {
		this.editable = editable;
	}
	
    public DisplayTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            boolean editable = false;
            if(this.editable != null )
            	editable = Boolean.parseBoolean(this.editable);
            out.print(HtmlClientComponentService.getDisplay(request, editable));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (easyJ.common.EasyJException ee) {
            ee.printStackTrace();
        }
        return this.SKIP_BODY;
    }
}
