package easyJ.http.tags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderDirection;
import easyJ.database.dao.OrderRule;
import easyJ.system.service.HtmlClientComponentService;

/**
 * 这个标签用来以tab的方式来显示对象，而且用户可以指定需要显示的属性（例如：名称，图片等，可以参考京东的页面www.360buy.com）
 * 
 * @author liufeng
 */
public class ObjectListTag extends TagSupport {
    private String className; // 指明要显示哪个类的数据

    private String actionName; // 指定当点击数据的时候执行哪个action，默认的方法是view。

    private String tabProperty; // 用来指定在在tab标签上显示哪个字段的内容。

    private String showProperty; // 如果指定具体显示的时候显示那个字段

    private String imgProperty; // 如果指定具体显示的时候如果需要显示图片，指定保存图片地址字段

    private String rows = "4"; // 指定每个tab显示多少行，如果超过则显示more

    public void setClassName(String className) {
        this.className = className;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setTabProperty(String tabProperty) {
        this.tabProperty = tabProperty;
    }

    public void setShowProperty(String showProperty) {
        this.showProperty = showProperty;
    }

    public void setImgProperty(String imgProperty) {
        this.imgProperty = imgProperty;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            Object obj = BeanUtil.getEmptyObject(className);
            SingleDataProxy sdp = SingleDataProxy.getInstance();
            sdp.setContext(null);
            OrderRule rule = new OrderRule();
            rule.setOrderColumn(tabProperty);
            rule.setOrderDirection(OrderDirection.ASC);
            OrderRule orderRules[] = {
                rule
            };
            ArrayList list = sdp.query(obj, orderRules);
            out.print(HtmlClientComponentService.getObjectListHtml(list,
                    actionName, tabProperty, showProperty, imgProperty,
                    Integer.parseInt(rows)).toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (EasyJException ee) {
            ee.printStackTrace();
        }

        return this.SKIP_BODY; // 当标签包括的内容为空时返回SKIP_BODY，表示标签功能已执行完成
    }

    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }
}
