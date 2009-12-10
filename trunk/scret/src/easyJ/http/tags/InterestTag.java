package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import easyJ.system.data.Interest;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import easyJ.system.data.SysUserCache;
import easyJ.system.service.HtmlClientComponentService;
import easyJ.common.*;
import easyJ.http.Globals;

public class InterestTag extends TagSupport {
    private String patternPosition;

    public InterestTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();

            SysUserCache userCache = (SysUserCache) request.getSession()
                    .getAttribute(Globals.SYS_USER_CACHE);
            ArrayList list = (ArrayList) userCache.getInterests();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Interest interest = (Interest) list.get(i);
                if (patternPosition.equalsIgnoreCase(interest
                        .getPatternPosition()))
                    out.print(HtmlClientComponentService
                            .getInterestHtml(interest));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (EasyJException ex) {} finally {}
        return this.SKIP_BODY;

    }

    public void setPatternPosition(String patternPosition) {
        this.patternPosition = patternPosition;
    }
}
