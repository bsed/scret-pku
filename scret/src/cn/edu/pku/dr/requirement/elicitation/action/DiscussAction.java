package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.common.BeanUtil;
import cn.edu.pku.dr.requirement.elicitation.data.Discuss;
import java.util.ArrayList;
import easyJ.system.service.UserColumnService;
import easyJ.system.data.Property;
import easyJ.system.data.UserPropertyRight;
import easyJ.common.StringUtil;
import easyJ.system.service.HtmlClientComponentService;

public class DiscussAction extends easyJ.http.servlet.SingleDataAction {
    public DiscussAction() {}

    public void expand() throws EasyJException {
        String queryByTopic = request.getParameter("queryByTopic");
        Discuss discuss = (Discuss) dp.get(object);
        if ("true".equals(queryByTopic)) {
            StringBuffer buffer = new StringBuffer();
            Discuss discussTopic = new Discuss();
            discussTopic.setDiscussTopicId(discuss.getDiscussId());
            ArrayList list = dp.query(discussTopic);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                buffer.append(HtmlClientComponentService.getForumExpand(list
                        .get(i), request, queryByTopic));
            }
            returnMessage = buffer.toString();
        } else {
            returnMessage = HtmlClientComponentService.getForumExpand(discuss,
                    request, queryByTopic).toString();
        }
    }

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxForumQuery.jsp?queryByTopic=false";
    }

    /* 同主题阅读 */
    public void queryByTopic() throws EasyJException {
        Discuss discuss = (Discuss) object;
        discuss.setDiscussParentId(new Long(-1));
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxForumQuery.jsp?queryByTopic=true";
    }

    public void newObject() throws EasyJException {
        Discuss discuss = (Discuss) object;
        discuss.setCreatorId(userId);
        discuss.setDiscussParentId(new Long(-1));
        super.newObject();
        discuss.setDiscussTopicId(discuss.getDiscussId());
        dp.update(discuss);
    }

    public void showSolution() throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        Discuss discuss = (Discuss) object;
        discuss.setDiscussType("solution");
        ArrayList discussList = (ArrayList) dp.query(discuss);
        buffer
                .append("<tr style=\"display:block\" id=\"solution"
                        + discuss.getDiscussSourceId()
                        + "\"><td width=\"100%\" colspan=\"2\"><table class=\"border\" width=\"100%\">");
        int size = discussList.size();
        for (int i = 0; i < size; i++) {
            discuss = (Discuss) discussList.get(i);
            buffer.append("<tr><td>");
            buffer.append(discuss.getDiscussContent());
            buffer.append("</td></tr>");
        }
        buffer.append("</table></td></tr>");
        returnMessage = buffer.toString();
    }

    public void saveReply() throws EasyJException {
        Discuss discuss = (Discuss) object;
        Long discussParentId = discuss.getDiscussParentId();
        String content = request.getParameter("discussContent"
                + discussParentId);
        String title = request.getParameter("discussTitle" + discussParentId);
        discuss.setDiscussContent(content);
        discuss.setDiscussType("problem");
        discuss.setDiscussTitle(title);
        discuss.setCreatorId(userId);
        dp.create(discuss);
        /* 下面返回给客户端相应字段的属性，为了在页面上加一行用。 */
        ArrayList displayProperties = (ArrayList) UserColumnService
                .getDisplayProperties(userId, className);
        StringBuffer buffer = new StringBuffer();
        int propertySize = displayProperties.size();
        for (int i = 0; i < propertySize; i++) {
            UserPropertyRight property = (UserPropertyRight) displayProperties
                    .get(i);
            buffer.append(property.getPropertyName());
            buffer.append("=");
            Object value = null;
            if (property.getShowProperty() != null) {
                value = BeanUtil.getFieldValue(discuss, property
                        .getShowProperty());
            } else {
                value = BeanUtil.getFieldValue(discuss, property
                        .getPropertyName());
            }
            if (value == null) {
                buffer.append("asdf");
            } else {
                buffer.append(value);
            }
            if (i != propertySize - 1) {
                buffer.append("&");
            }
        }
        returnMessage = buffer.toString() + "<message>";
    }

}
