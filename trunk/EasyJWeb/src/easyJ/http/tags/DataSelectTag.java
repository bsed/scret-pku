package easyJ.http.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import easyJ.http.Globals;
import java.util.ArrayList;
import easyJ.system.data.UserPropertyRight;
import easyJ.common.Format;
import easyJ.common.BeanUtil;
import easyJ.database.dao.Page;
import easyJ.common.validate.GenericValidator;
import easyJ.system.service.UserColumnService;
import easyJ.system.data.SysUserCache;

public class DataSelectTag extends TagSupport {
    public DataSelectTag() {}

    public int doStartTag() throws JspTagException { // 程序的执行起始点
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            String className = request.getParameter(Globals.CLASS_NAME);
            String primaryKeyName = BeanUtil.getPrimaryKeyName(className);
            /* 将来要进行替换 用户ID */
            SysUserCache userCache = (SysUserCache) request.getSession()
                    .getAttribute(Globals.SYS_USER_CACHE);
            Long userId = userCache.getUser().getUserId();
            /* 这里是从request当中取到用户拥有权限并选择的字段，将来应该是从缓存当中得到，这是需要修改的地方 */
            ArrayList displayProperties = userCache
                    .getDisplayProperties(className);
            if (displayProperties == null)
                return this.SKIP_BODY;
            int propertySize = displayProperties.size();
            StringBuffer buffer = new StringBuffer();
            buffer
                    .append("<table width=\"100%\" border=\"1\" id=\"selectDetails\" >\n");
            buffer.append("<tr  class=\"listTableHead\" id=\"title\">\n");
            /* 下面是输出题头栏的checkbox */

            buffer.append("<td>选择</td>");
            /* 下面输入用来浏览的数据字段 */
            /* 为了进行选择，需要将id也放到表格里面，但是就不用显示 */
            /* 这里是处理题头 */
            buffer
                    .append("<td nowrap class=\"trTitle\" align=\"center\" style=\"display:none\" ></td>\n");
            for (int i = 0; i < propertySize; i++) {
                UserPropertyRight property = (UserPropertyRight) displayProperties
                        .get(i);
                /* 其他的一些不用显示的id应该是用不到，但是也还是放过去吧 */
                if (!GenericValidator.isBlankOrNull(property.getShowProperty()))
                    buffer
                            .append("<td nowrap class=\"trTitle\" align=\"center\" style=\"display:none\" ></td>\n");
                buffer
                        .append("<td nowrap class=\"trTitle\" align=\"center\">\n");
                /* 此处 */
                buffer.append("<a href=\"javascript:PopUpWindow.orderBy('"
                        + property.getPropertyName() + "')\">"
                        + property.getPropertyChiName() + "</a>\n");
                buffer.append("</td>\n");
            }
            buffer.append("</tr>\n");
            if (request.getAttribute(Globals.PAGE) != null) {
                Page page = (Page) request.getAttribute(Globals.PAGE);
                ArrayList dataList = page.getPageData();
                int dataSize = dataList.size();
                ArrayList propertyNameList = new ArrayList();
                /* 用来标识隐藏的属性是否已经添加到属性字段properties中，因为是多行显示，只要添加一行就可以了，所以在这里做一个标记。 */
                boolean getProperty = false;
                for (int i = 0; i < dataSize; i++) {
                    buffer.append("<tr>\n");
                    Object object = dataList.get(i);
                    buffer
                            .append("<td class=\"trContent\"><input type=\"button\" class=\"button\" id=\"button"
                                    + i
                                    + "\" value=\"选择\" onclick=\"PopUpWindow.select(this)\"/> </td>\n");
                    buffer
                            .append("<td class=\"trContent\" style=\"display: none;\">"
                                    + BeanUtil.getPrimaryKeyValue(object)
                                    + "</td>\n");
                    for (int j = 0; j < propertySize; j++) {
                        UserPropertyRight property = (UserPropertyRight) displayProperties
                                .get(j);

                        String propertyName = property.getPropertyName();
                        String value = "";
                        if (GenericValidator.isBlankOrNull(property
                                .getShowProperty())) {
                            value = Format.formatDisplay(BeanUtil
                                    .getFieldValue(object, propertyName));
                            if (!getProperty)
                                propertyNameList.add(propertyName);
                            buffer.append("<td class=\"trContent\" nowrap>"
                                    + value + "</td>\n");
                        } else {
                            String idValue = Format.formatDisplay(BeanUtil
                                    .getFieldValue(object, propertyName));
                            value = Format.formatDisplay(BeanUtil
                                    .getFieldValue(object, property
                                            .getShowProperty()));
                            /* 将id属性和要显示的属性都加到页面上面去 */
                            if (!getProperty) {
                                propertyNameList.add(propertyName);
                                propertyNameList
                                        .add(property.getShowProperty());
                            }
                            buffer
                                    .append("<td class=\"trContent\" style=\"display:none\">"
                                            + idValue + "</td>\n");
                            buffer.append("<td class=\"trContent\" nowrap>"
                                    + value + "</td>\n");
                        }
                    }
                    getProperty = true;
                    buffer.append("</tr>\n");
                }
                /* 这个字段用来告诉客户端有什么样的属性在客户端进行了显示，其中包括一些id */
                String propertiesStr = primaryKeyName;
                for (int i = 0; i < propertyNameList.size(); i++)
                    propertiesStr = propertiesStr + ","
                            + propertyNameList.get(i);

                // propertiesStr=propertiesStr.replaceFirst(",","");
                buffer
                        .append("<input type=\"hidden\" id=\"properties\" value=\""
                                + propertiesStr + "\"/>\n");
            }
            buffer.append("</table>\n");
            out.println(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (easyJ.common.EasyJException ee) {
            ee.printStackTrace();
        }
        return this.SKIP_BODY;
    }
}
