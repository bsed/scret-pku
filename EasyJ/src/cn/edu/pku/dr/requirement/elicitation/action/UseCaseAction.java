package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.http.servlet.CompositeDataAction;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.http.Globals;
import javax.servlet.http.HttpServletRequest;

import cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction;

import java.util.ArrayList;
import easyJ.common.BeanUtil;
import easyJ.common.validate.ValidateErrors;
import easyJ.system.service.UserColumnService;
import easyJ.system.data.Property;
import easyJ.system.data.SysUserCache;
import easyJ.system.data.SystemClass;
import easyJ.system.data.UserPropertyRight;
import easyJ.common.Format;
import easyJ.common.validate.Validate;
import easyJ.business.proxy.DataProxy;
import easyJ.business.proxy.DictionaryProxy;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.system.service.HtmlClientComponentService;

public class UseCaseAction extends CompositeDataAction {
    public UseCaseAction() {}

    public void edit() throws EasyJException {
        super.edit();
        returnPath = "/WEB-INF/AjaxUseCaseEdit.jsp";
    }

    public void delete() throws EasyJException {
        super.delete();

    }

    public void update() throws EasyJException {
        super.update();
        returnPath = "/WEB-INF/AjaxUseCaseEdit.jsp";
    }

    public void newObject() throws EasyJException {
        super.newObject();
        returnPath = "/WEB-INF/AjaxUseCaseEdit.jsp";
    }

    /**
     * 当用户选择不同的数据是，显示不同的property
     * @throws EasyJException
     */
    public void getProperty() throws EasyJException{
        String classId = request.getParameter("classId");
        String lineNum = request.getParameter("lineNum");
        StringBuffer buffer = getProperty(classId, lineNum, "");
        
        returnMessage = buffer.toString() + "<message>";
    }
    
    public static StringBuffer getProperty(String classId, String lineNum, String columnsValue) throws EasyJException{
        SystemClass sysClass = new SystemClass();
        sysClass.setClassId(new Long(classId));
        DataProxy dp = SingleDataProxy.getInstance();
        sysClass = (SystemClass)dp.get(sysClass);
        String className = sysClass.getClassName();
        Property property  = new Property();
        property.setClassName(className);
        ArrayList properties = dp.query(property);
        StringBuffer buffer = new StringBuffer();
        for (Object obj : properties) {
            Property userProperty = (Property)obj;
            String propertyName = userProperty.getPropertyName();
            String propertyChiName = userProperty.getPropertyChiName();
            boolean checked = false;
            if (columnsValue != null && columnsValue.indexOf(","+propertyName + ",") >= 0)
                checked = true;
            if (checked)
                buffer.append("<input type=\"checkbox\" name=\"columns" + lineNum 
                    + "\" value=\"" + propertyName + "\"  checked"
                    + ">" + propertyChiName 
                    + "</checkbox>");
            else
                buffer.append("<input type=\"checkbox\" name=\"columns" + lineNum 
                        + "\" value=\"" + propertyName +"\">" + propertyChiName 
                        + "</checkbox>");
        }
        return buffer;
    }
    
    public static StringBuffer getCompositeDetail(HttpServletRequest request)
            throws EasyJException {
        try {
            if (request.getAttribute(Globals.OBJECT) == null) {
                return null;
            }
            StringBuffer buffer = new StringBuffer();
            Object obj = request.getAttribute(Globals.OBJECT);
            String[] subClassProperties = BeanUtil.getSubClassProperties(obj
                    .getClass());
            SysUserCache userCache = (SysUserCache) request.getSession()
                    .getAttribute(Globals.SYS_USER_CACHE);
            Long userId = userCache.getUser().getUserId();

            /* 这里是从request当中取到用户拥有权限并选择的字段，将来应该是从缓存当中得到，这是需要修改的地方 */

            /* 对所有的子表属性进行循环 */
            for (int j = 0; j < subClassProperties.length; j++) {
                String subClassProperty = subClassProperties[j];
                String subClassName = BeanUtil.getSubClass(obj.getClass(),
                        subClassProperties[j]);
//                ArrayList displayProperties = (ArrayList) UserColumnService
//                        .getDisplayProperties(userId, subClassName);

              ArrayList displayProperties = (ArrayList) userCache.getDisplayProperties(subClassName);

                if (displayProperties == null) {
                    return null;
                }
                ValidateErrors errors = (ValidateErrors) request
                        .getAttribute(Globals.VALIDATE_ERRORS);
                int propertySize = displayProperties.size();
                buffer.append("<table width=\"100%\" id=\"details\">\n");
                buffer.append("<tr>\n");
                /* 下面是输出题头栏的checkbox */

                // buffer.append("<td class=\"trTitle\">操作</td>\n");
                /*
                 * properties用来保存明细显示字段，classes用来保存某字段需要从哪个class对应的表中选择数据，
                 * 并把properties, classes送到客户端，放到hidden当中，在选择给明细选择数据的时候用到
                 */
                StringBuffer properties = new StringBuffer();
                StringBuffer classes = new StringBuffer();
                StringBuffer propertyTypes = new StringBuffer();
                /* 下面输入用来浏览的数据字段 */
                for (int i = 0; i < propertySize; i++) {
                    UserPropertyRight property = (UserPropertyRight) displayProperties
                            .get(i);
                    Integer width = property.getShowWidth();
                    if (width != null)
                        buffer.append("<td nowrap class=\"trTitle\" width='" 
                                + width +"%'align=\"center\">\n");
                    else
                        buffer.append("<td nowrap class=\"trTitle\" align=\"center\">\n");
                    buffer.append(property.getPropertyChiName() + "\n");
                    buffer.append("</td>\n");
                    properties.append(property.getPropertyName()).append(",");
                    propertyTypes.append(property.getType()).append(",");
                    if (GenericValidator.isBlankOrNull(property
                            .getPropertyValueTable()))
                        classes.append(",");
                    else {
                        classes.append(property.getPropertyValueTable())
                                .append(",");
                        /* 将需要显示的字段放到id字段的后面 */
                        properties.append(property.getShowProperty()).append(
                                ",");
                        propertyTypes.append(property.getType()).append(",");
                    }
                }
                buffer.append("</tr>\n");

                ArrayList dataList = (ArrayList) BeanUtil.getFieldValue(obj,
                        subClassProperty);
                int dataSize = 0;
                String subPrimaryKeyName = "";
                if (dataList != null)
                    dataSize = dataList.size();
                subPrimaryKeyName = (String) BeanUtil
                        .getPrimaryKeyName(BeanUtil.getClass(subClassName));
//                buffer
//                        .append("<tr><td class=\"trContent\" colspan=\""
//                                + propertySize
//                                + "\" ><image src=\"image\\delete.gif\" id=\"check"
//                                + "\" onclick=\"UseCase.hiddenDetail(this)\" /><image  src=\"image\\up.gif\" id=\"insertBefore"
//                                + "\" onclick=\"UseCase.insert('up','user')\" /><image  src=\"image\\up.gif\" id=\"insertBeforeSys"
//                                + "\" onclick=\"UseCase.insert('up','sys')\" /><image src=\"image\\down.gif\" id=\"insertAfter"
//                                + "\" onclick=\"UseCase.insert('down','user')\" /><image src=\"image\\down.gif\" id=\"insertAfterSys"
//                                + "\" onclick=\"UseCase.insert('down','sys')\" /></td></tr>\n");

                buffer
                .append("<tr><td class=\"trContent\" colspan=\""
                        + propertySize
                        + "\" ><image src=\"image/delete.gif\" id=\"check"
                        + "\" onclick=\"UseCase.hiddenDetail(this)\" /><image  src=\"image/up.gif\" id=\"insertBefore"
                        + "\" onclick=\"UseCase.insert('up','user')\" /><image  src=\"image/up.gif\" id=\"insertBeforeSys"
                        + "\" onclick=\"UseCase.insert('up','sys')\" /><image src=\"image/down.gif\" id=\"insertAfter"
                        + "\" onclick=\"UseCase.insert('down','user')\" /><image src=\"image/down.gif\" id=\"insertAfterSys"
                        + "\" onclick=\"UseCase.insert('down','sys')\" /></td></tr>\n");
                
                String propertyValueTable = "";
                for (int i = 0; i < dataSize; i++) {
                    buffer.append("<div><tr id=\"tr" + i
                            + "\" class=\"trContent\">\n");
                    Object object = dataList.get(i);
                    UseCaseInteraction interaction = (UseCaseInteraction)object;
                    
//                     buffer.append( "<td class=\"trContent\" onMouseOver=\"showImg(this)\" onMouseLeave=\"hideImg(this)\"><image"+
//                     "style=\"display:none\" src=\"image\\delete.gif\""+
//                     "id=\"check" + i + "\" onclick=\"Data.hiddenDetail(this)\" /><image"+
//                     "style=\"display:none\" src=\"image\\up.gif\""+
//                     "id=\"insertBefore" + i + "\""+
//                     "onclick=\"UseCase.insert(this,'up','user')\" /><image"+
//                     "style=\"display:none\" src=\"image\\up.gif\""+
//                     "id=\"insertBeforeSys" + i + "\""+
//                     "onclick=\"UseCase.insert(this,'up','sys')\" /><br>"+
//                     "<image style=\"display:none\" src=\"image\\down.gif\""+
//                     "id=\"insertAfter" + i + "\""+
//                     "onclick=\"UseCase.insert(this,'down','user')\" /><image"+
//                     "style=\"display:none\" src=\"image\\down.gif\""+
//                     "id=\"insertAfterSys" + i + "\""+
//                     "onclick=\"UseCase.insert(this,'down','sys')\" /><input"+
//                     "type=\"hidden\" name=\""+subPrimaryKeyName+ "\""+
//                     "value=\""+BeanUtil.getFieldValue(object,subPrimaryKeyName)+"\"/></td>\n");

                    buffer.append("<input type=\"hidden\" name=\""
                            + subPrimaryKeyName + "\" value=\""
                            + BeanUtil.getFieldValue(object, subPrimaryKeyName)
                            + "\"/>");
                    for (int k = 0; k < propertySize; k++) {
                        UserPropertyRight property = (UserPropertyRight) displayProperties
                                .get(k);
                        
                        //特殊处理columns字段
                        if ("columns".equals(property.getPropertyName())) {
                            Long classId = interaction.getClassId();
                            if (classId != null) {
                                String columnsValue = interaction.getColumns();
                                StringBuffer columnBuffer = 
                                    getProperty(classId.toString(), new Integer(i).toString(), columnsValue);
                                buffer.append("<td>");
                                buffer.append(columnBuffer);
                                buffer.append("</td>");
                            }
                            continue;
                        }
                        
                        String propertyName = property.getPropertyName();
                        /* 如果不是从数据字典表或者其他表中选取，则显示输入框 */
                        if (GenericValidator.isBlankOrNull(property
                                .getPropertyValueTable())) {
                            if ("textarea".equals(property.getType())) {
                                Object value = Format.format(BeanUtil
                                        .getFieldValue(object, propertyName));
                                if (k == 0 && "^^sys$$".equals(value)) {
                                    Integer width = property.getShowWidth();
                                    if (width != null)
                                        buffer.append("<td nowrap width='" 
                                                + width +"%'\n");
                                    else
                                        buffer.append("<td nowrap>\n");
                                    buffer
                                            .append("<textarea  style=\"display:none\" name=\""
                                                    + property
                                                            .getPropertyName()
                                                    + "\" id=\""
                                                    + property
                                                            .getPropertyName()
                                                    + i
                                                    + "\" onblur=\""
                                                    + Format
                                                            .format(Validate
                                                                    .getClientValidate(object
                                                                            .getClass()
                                                                            .getName()
                                                                            + propertyName))
                                                    + "\"  onpropertychange=\"setHeight()\" onpaste=\"setHeight()\">"
                                                    + value + "</textarea>\n");
                                } else {
                                    Integer width = property.getShowWidth();
                                    if (width != null)
                                        buffer.append("<td nowrap class=\"trContent\" width='" 
                                                + width +"%'>\n");
                                    else
                                        buffer
                                            .append("<td class=\"trContent\" nowrap>\n");
                                    buffer
                                            .append("<textarea  style=\"overflow:auto;width:100%;height:100%\" name=\""
                                                    + property
                                                            .getPropertyName()
                                                    + "\" id=\""
                                                    + property
                                                            .getPropertyName()
                                                    + i
                                                    + "\" onblur=\""
                                                    + Format
                                                            .format(Validate
                                                                    .getClientValidate(object
                                                                            .getClass()
                                                                            .getName()
                                                                            + propertyName))
                                                    + "\" onpropertychange=\"setHeight()\" onpaste=\"setHeight()\" onfocus=\"UseCase.rememberFocus(this)\">"
                                                    + value + "</textarea>\n");
                                }
                            } else {
                                Integer width = property.getShowWidth();
                                if (width != null)
                                    buffer.append("<td width='" 
                                            + width +"%'>\n");
                                else
                                    buffer.append("<td>");
                                buffer
                                        .append("<input type=\"text\" name=\""
                                                + property.getPropertyName()
                                                + "\" id=\""
                                                + property.getPropertyName()
                                                + i
                                                + "\" onfocus=\"rememberFocus(this)\" value=\""
                                                + Format.format(BeanUtil
                                                        .getFieldValue(object,
                                                                propertyName))
                                                + "\" onblur=\""
                                                + Format
                                                        .format(Validate
                                                                .getClientValidate(object
                                                                        .getClass()
                                                                        .getName()
                                                                        + propertyName))
                                                + "\"/>\n");
                            }
                            if (errors != null
                                    && errors.getErrorMsg(propertyName) != null) {
                                buffer.append(errors.getErrorMsg(propertyName));
                            }
                        } else {
                            buffer.append("<td>");
                            /* 如果是从数据字典表或者其他表中选取，则显示下拉列表或者弹出框 */
                            propertyValueTable = property
                                    .getPropertyValueTable();
                            if (property.getFromDictionary() != null) {
                                if (property.getFromDictionary().booleanValue()) {
                                    StringBuffer selectBuffer = DictionaryProxy
                                            .getHtmlSelect(
                                                    propertyName,
                                                    propertyValueTable,
                                                    BeanUtil.getFieldValue(
                                                            object,
                                                            propertyName),
                                                    BeanUtil
                                                            .getFieldValue(
                                                                    object,
                                                                    property
                                                                            .getShowProperty()));
                                    buffer.append(selectBuffer.toString());
                                } else {
                                    Object propertyValue = BeanUtil
                                            .getFieldValue(object, propertyName);
                                    String diplayName = property
                                            .getShowProperty();
//                                    Object displayValue = BeanUtil
//                                            .getFieldValue(object, diplayName);
                                    StringBuffer selectBuffer = HtmlClientComponentService
                                            .getSelect(propertyValueTable, propertyValue);
                                    buffer.append(selectBuffer.toString());
                                }
                            }

                        }
                        buffer.append("</td>\n");

                    }
                    buffer
                            .append("<input type=\"hidden\" name=\"detailPropertyClass\" id=\"detailPropertyClass\" value=\""
                                    + propertyValueTable + "\"/>\n");
                    // 下面之所以i+2是因为除了一行标题之外，还有一行菜单，所以应该是第i+2行。
                    buffer
                            .append("<input type=\"hidden\" name=\"useState\" id=\"useState"
                                    + (i + 2) + "\" value=\"Y\"/>\n");
                    buffer.append("</tr></div>\n");
                }
                buffer
                        .append("<input type=\"hidden\" name=\"properties\" id=\"properties\" value=\""
                                + properties + "\"/>\n");
                buffer
                        .append("<input type=\"hidden\" name=\"propertyTypes\" id=\"propertyTypes\" value=\""
                                + propertyTypes + "\"/>\n");
                buffer
                        .append("<input type=\"hidden\" name=\"classes\" id=\"classes\" value=\""
                                + classes + "\"/>\n");
                buffer
                        .append("<input type=\"hidden\" name=\"subPrimaryKeyName\" id=\"subPrimaryKeyName\" value=\""
                                + subPrimaryKeyName + "\"/>\n");
                buffer.append("</table>\n");
            }
            return buffer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
