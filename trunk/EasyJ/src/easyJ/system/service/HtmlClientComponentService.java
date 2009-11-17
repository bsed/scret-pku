package easyJ.system.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import cn.edu.pku.dr.requirement.elicitation.system.Context;
import cn.edu.pku.dr.requirement.elicitation.system.DataContextFilter;
import cn.edu.pku.dr.requirement.elicitation.system.DictionaryConstant;
import cn.edu.pku.dr.requirement.elicitation.system.FunctionCondition;
import easyJ.business.proxy.DictionaryProxy;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.BeanUtil;
import easyJ.common.Const;
import easyJ.common.EasyJException;
import easyJ.common.Format;
import easyJ.common.validate.GenericValidator;
import easyJ.common.validate.Validate;
import easyJ.common.validate.ValidateErrors;
import easyJ.database.dao.OrderDirection;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Page;
import easyJ.http.Globals;
import easyJ.system.data.Dictionary;
import easyJ.system.data.Interest;
import easyJ.system.data.PageFunction;
import easyJ.system.data.SysUserCache;
import easyJ.system.data.UserPropertyRight;

public class HtmlClientComponentService {
    private static SingleDataProxy sdp = SingleDataProxy.getInstance();

    public HtmlClientComponentService() {}

    /**
     * @param className
     *                String 需要生成下拉列表的类名
     * @param value
     *                Object 当前下拉列表的值
     * @throws EasyJException
     * @return StringBuffer
     */
    public static StringBuffer getSelect(String className, Object value)
            throws EasyJException {
        Object o = BeanUtil.getEmptyObject(className);
        ArrayList<Object> list = sdp.query(o);
        String idProperty = (String) BeanUtil.getPubStaticFieldValue(o
                .getClass(), Const.ID_PROPERTY);
        String displayProperty = (String) BeanUtil.getPubStaticFieldValue(o
                .getClass(), Const.DISPLAY_PROPERTY);

        return getSelect(list, idProperty, idProperty, displayProperty, value,
                false);
    }

    /* i代表是第几行的明细，是用来设定id用的，使用场景：在新增入库单的时候，选择入库物料 */
    public static StringBuffer getDataSelect(String className,
            String propertyName, Object propertyValue, String displayValueName,
            Object displayValue, int i, boolean isComposite) throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        /* PopUpWindow.showTable()的最后一个参数用来指明是否是批量选择数据，此处针对单行选择数据不使用多选，所以参数是false */
        buffer.append("<input type=\"text\" onclick=\"PopUpWindow.showTable('"
                + className + "'" + "," + i
                + ",PopUpWindow.singleSelect,null)\" readonly name=\""
                + displayValueName + "\" id=\"" + displayValueName + i
                + "\" value=\"" + Format.format(displayValue) + "\"/>");
        buffer.append("<input type=\"hidden\" name=\"" + propertyName);
        if(!isComposite)
        	buffer.append(i);
        buffer.append("\" id=\"" + propertyName + i + "\" value=\""
                + Format.format(propertyValue) + "\"/>");
        return buffer;
    }

    /**
     * 用来生成多选的选择界面
     * 
     * @param sourceObject
     *                原类的查询条件。
     * @param destObject
     *                目标类的查询条件
     * @param property
     *                要两边进行比较的字段是哪个字段，例如如果是选择数据，则两边比较的dataId
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getMultiSelect(Object sourceObject,
            Object destObject, String property, String orderByProperty,
            OrderDirection direction, String[] accurateProperties)
            throws EasyJException {
        // Object sourceObject = BeanUtil.getEmptyObject(sourceClass);
        // Object destObject = BeanUtil.getEmptyObject(destClass);
        // projectContext.fillContextInfo(sourceObject);
        // projectContext.fillContextInfo(destObject);
        ArrayList<Object> destList = null;

        ArrayList<Object> sourceList = sdp.query(sourceObject);
        if (!GenericValidator.isBlankOrNull(orderByProperty)
                && direction != null) {
            OrderRule[] orderRules = {
                new OrderRule(orderByProperty, direction)
            };
            sdp.setAccurateProperties(accurateProperties);
            destList = sdp.query(destObject, orderRules);
        } else {
            sdp.setAccurateProperties(accurateProperties);
            destList = sdp.query(destObject);
        }
        return getMultiSelect(sourceList,destList,property,sourceObject.getClass());
    }
    
    public static StringBuffer getMultiSelect(ArrayList sourceList,ArrayList destList,String property,Class sourceClass) throws EasyJException{

        // 因为destList是已经选择的，所以需要将destList中的内容从sourceList中去除。去除的依据是property的值是否相同。
        int destLen = destList.size();

        Object sourceObject=null;
        Object destObject=null;
        for (int j = destLen - 1; j >= 0; j--) {
            destObject = destList.get(j);
            Object destValue = BeanUtil.getFieldValue(destObject, property);
            if (destValue == null) {
                destList.remove(j);
                break;
            }
            int sourceLen = sourceList.size();
            for (int i = sourceLen - 1; i >= 0; i--) {
                sourceObject = sourceList.get(i);
                Object sourceValue = BeanUtil.getFieldValue(sourceObject,
                        property);
                if (sourceValue == null) {
                    sourceList.remove(i);
                    break;
                }
                if (destValue != null && destValue.equals(sourceValue)) {
                    sourceList.remove(i);
                    break;
                }
            }
        }

        StringBuffer buffer = new StringBuffer();

        // source的可以知道哪些，而dest却没有，需要从source得到来显示displayProperty，以及记录idProperty
        String sourceSelectName = "sourceSelect";
        // +(String) BeanUtil.getPubStaticFieldValue(sourceObject
        // .getClass(), Const.ID_PROPERTY);
        String idProperty = (String) BeanUtil.getPubStaticFieldValue(
                sourceClass, Const.ID_PROPERTY);
        String displayProperty = (String) BeanUtil.getPubStaticFieldValue(
                sourceClass, Const.DISPLAY_PROPERTY);

        String destSelectName = "destSelect";// +sourceSelectName;
        // 设置选择的值需要在数据库中存储的字段
        buffer
                .append("<input type=\"hidden\" id=\"multiSelectProperty\" value=\""
                        + idProperty + "\"/>\n");

        buffer.append("<table width=\"100%;\">\n");
        buffer.append("<tr><td colspan=\"4\"><input type=\"text\" value=\"\" " 
                        +"style=\"width:38%\" id=\"selectQuery\" onInput=\"MultiSelect.filter(this.value);" 
                        +"\" onPropertyChange=\"MultiSelect.filter(this.value);\"/></td></tr>\n");
        buffer.append("<tr>");
        // 左边一列，列出所有的数据
        buffer.append("<td width=\"40%;\">");
        buffer.append("<div>");
        buffer.append(getSelect(sourceList, sourceSelectName, idProperty,
                displayProperty, null, true));
        buffer.append("</div>");
        buffer.append("</td>");

        // 中间一列的数据选择操作。
        buffer.append("<td width=\"10%;\">");
        buffer.append("<table align=\"center\">");
        buffer
                .append("<tr><td align=\"center\"><input type=\"button\" class=\"button\" value=\"增加\" onclick=\"MultiSelect.select();\"/></td></tr>");
        buffer
                .append("<tr><td align=\"center\"><input type=\"button\" class=\"button\" value=\"去除\" onclick=\"MultiSelect.deSelect();\"/></td></tr>");
        // buffer.append("<tr><td><input type=\"button\" class=\"button\"
        // value=\"添加全部\" onclick=\"\"/></td></tr>");
        // buffer.append("<tr><td><input type=\"button\" class=\"button\"
        // value=\"去除全部\" onclick=\"\"/></td></tr>");
        buffer.append("</table>");
        buffer.append("</td>");

        // 右边一列，列出所有的被选择的数据
        buffer.append("<td width=\"40%;\">");
        buffer.append(getSelect(destList, destSelectName, idProperty,
                displayProperty, null, true));
        buffer.append("</td>");

        // 最右边一列对被选择的数据的操作。
        buffer.append("<td width=\"10%\">");
        buffer.append("<table align=\"center\">");
        buffer
                .append("<tr><td align=\"center\"><input type=\"button\" class=\"button\" value=\"上移\" onclick=\"MultiSelect.move('up');\"/></td></tr>");
        buffer
                .append("<tr><td align=\"center\"><input type=\"button\" class=\"button\" value=\"下移\" onclick=\"MultiSelect.move('down');\"/></td></tr>");
        buffer.append("</table>");
        buffer.append("</td>");

        buffer.append("</tr>\n");
        // 确认和取消按钮
        buffer.append("<tr>\n");
        buffer
                .append("<tr><td colspan=\"4\" align=\"center\"><input type=\"button\" class=\"button\" value=\"确定\" onclick=\"MultiSelect.confirm();\"/>"
                        + "<input type=\"button\" class=\"button\" value=\"取消\" onclick=\"MultiSelect.cancel();\"/></td></tr>\n");
        buffer.append("</tr>\n");
        buffer.append("</table>\n");

        return buffer;
    }

    public static String getPropertyMultiSelect(Object sourceObject,
            Object destObject, String property, String propertyType,
            String orderByProperty, OrderDirection direction)
            throws EasyJException {
        String[] accurateProperties = {
            "className"
        };
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table>\n");
        buffer.append("<tr>\n");
        buffer.append("<td>\n");
        buffer
                .append("<select style=\"width:40%\" name=\"propertyType\" id=\"multiSelectPropertyType\" onchange=\"Data.adjustProperty()\">\n");
        if ("whetherDisplay".equals(propertyType))
            buffer
                    .append("<option value=\"whetherDisplay\" selected>显示</option>\n");
        else
            buffer.append("<option value=\"whetherDisplay\">显示</option>\n");
        if ("whetherQuery".equals(propertyType))
            buffer
                    .append("<option value=\"whetherQuery\" selected>查询</option>\n");
        else
            buffer.append("<option value=\"whetherQuery\">查询</option>\n");
        if ("whetherEdit".equals(propertyType))
            buffer
                    .append("<option value=\"whetherEdit\" selected>编辑</option>\n");
        else
            buffer.append("<option value=\"whetherEdit\">编辑</option>\n");
        buffer.append("</select>\n");
        buffer.append("</td>\n");
        buffer.append("</td>\n");
        buffer.append("</tr>\n");
        buffer.append("<table/>\n");

        buffer.append(getMultiSelect(sourceObject, destObject, property,
                orderByProperty, direction, accurateProperties));
        return buffer.toString();
    }

    /**
     * 对传进来的List建立select。暂时定死select的高度为10，将来可以通过参数设定
     * 
     * @param list
     *                传进来的数据
     * @param selectName
     *                select需要的名字
     * @param multiple
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getSelect(ArrayList<Object> list,
            String selectName, String idProperty, String displayProperty,
            Object value, boolean multiple) throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        if (list == null || list.size() == 0) {
            if (multiple) {
                buffer
                        .append("<select size=\"10\" multiple=\"multiple\" name=\""
                                + selectName
                                + "\" id=\""
                                + selectName
                                + "\" style=\"width:100%\">");
                buffer.append("</select>");
            } else {
                buffer.append("<select size=\"10\" name=\"" + selectName
                        + "\" id=\"" + selectName + "\" >");
                buffer.append("</select>");
            }
            return buffer;
        }

        Object o = list.get(0);

        if (multiple) {
            buffer.append("<select size=\"10\" multiple=\"multiple\" name=\""
                    + selectName + "\" id=\"" + selectName
                    + "\" style=\"width:100%\">");
        } else {
            buffer.append("<select width=\"100%\" name=\"" + selectName
                    + "\" id=\"" + selectName + "\">");
            buffer.append("<option value=\"\"></>\n");
        }
        for (int i = 0; i < list.size(); i++) {
            o = list.get(i);
            Object idValue = BeanUtil.getFieldValue(o, idProperty);
            Object displayValue = BeanUtil.getFieldValue(o, displayProperty);
            if (idValue.equals(value)) {
                buffer.append("<option value=\"" + idValue + "\" selected>"
                        + displayValue + "</>\n");
            } else {
                buffer.append("<option value=\"" + idValue + "\">"
                        + displayValue + "</>\n");
            }
        }

        buffer.append("</select>");

        return buffer;
    }

    /**
     * @param className //
     *                指明要显示哪个类的数据
     * @param actionName //
     *                指明当用户点击数据的时候，要提交给哪个action。
     * @param tabProperty //
     *                用来指定在在tab标签上显示哪个字段的内容。
     * @param showProperty //
     *                如果指定具体显示的时候显示那个字段
     * @param imgProperty //
     *                如果指定具体显示的时候如果需要显示图片，指定保存图片地址字段
     * @param rows //
     *                指定每个tab显示多少行，如果超过则显示more
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getObjectListHtml(ArrayList list,
            String actionName, String tabProperty, String showProperty,
            String imgProperty, int rows) throws EasyJException {
        // 首先得到所有的数据，按照tabProperty排序。

        StringBuffer buffer = new StringBuffer();
        // 如果将来用户设置了所关心的数值之后，则需要将设定的数值作为过滤条件
        // 这些设置应该在用户登陆的时候加入到对应的className+propertyName 对应的数组当中。

        // 在这里只是得到存在的数据。例如：如果用户关心化工类project，但如果化工类的project还没有数据，则暂时显示不出来。
        // 如果将来支持用户的关切，那么就应该加入进去，需要修改代码。

        int typeStartPos = 0; // 用来指示当前的type是从哪个地方开始的。
        int typeEndPosition = 1; // 用来指示在当前的type在哪个位置结束。
        // typeEndPostion最终停止的位置是下一个的开始
        int size = list.size();
        if (size == 0)
            return null;

        String currentTabPropertyValue = BeanUtil.getFieldValue(list.get(0),
                tabProperty).toString(); // 用来指示在下面的循环中当前所关心的propertyValue是什么。例如当前访问到化工类。
        boolean sameTypeFoward = false;
        for (int i = 1; i < list.size(); i++) {
            Object object = list.get(i);
            String tabPropertyValue = BeanUtil.getFieldValue(object,
                    tabProperty).toString();
            // 如果是属于同一段的
            if (currentTabPropertyValue.equals(tabPropertyValue)) {
                sameTypeFoward = true;
                typeEndPosition++;
            } else {
                sameTypeFoward = false;
                getSingleObjectList(buffer, list, typeStartPos,
                        typeEndPosition - 1, rows, currentTabPropertyValue,
                        showProperty, actionName);
                typeStartPos = typeEndPosition;
                typeEndPosition++;
                currentTabPropertyValue = tabPropertyValue;
            }
        }
        // if (sameTypeFoward)
        typeEndPosition--;
        // 用来得到最后的一段。
        getSingleObjectList(buffer, list, typeStartPos, typeEndPosition, rows,
                currentTabPropertyValue, showProperty, actionName);
        return buffer;
    }

    /**
     * 用在生成登陆页面之后用户感兴趣的领域项目展示。
     * 
     * @param buffer
     *                用来得到最总结果
     * @param list
     *                用来从中取得数据的。
     * @param begin
     *                用来指示从list的第几条开始读
     * @param end
     *                用来指示读到第几条。
     * @param rows
     *                用来指示生成的多少行的数据。
     * @param currentTabPropertyValue
     *                用来指示当前生成的title。
     */
    public static void getSingleObjectList(StringBuffer buffer, ArrayList list,
            int begin, int end, int rows, String currentTabPropertyValue,
            String showProperty, String actionName) throws EasyJException {

        int columnPerLine = 4; // 用来定义每行显示多少个数据，应该根据用户的显示器的分辨率来决定，暂时随便定了一个4.
        buffer
                .append("<table class=\"border\"><tr><td width=\"100%\" colspan=\""
                        + columnPerLine + "\">\n");
        buffer
                .append("<div  class=\"NewsTitleRight\" style=\"WIDTH: 30%\"><div  class=\"NewsTitleLeft\">\n");
        buffer.append(currentTabPropertyValue);
        buffer.append("</div></div></td></tr>");
        String className = list.get(begin).getClass().getName();
        String primaryKey = BeanUtil.getPrimaryKeyName(className);
        // 如果总的数据超过了能显示的数据，则只显示能显示的数据
        if (rows * columnPerLine < end - begin + 1)
            end = begin + rows * columnPerLine;
        int j = 0;
        for (int i = begin; i <= end; i++) {
            Object obj = list.get(i);
            // 新的一行的起点
            if (j % columnPerLine == 0) {
                buffer.append("<tr>");
            }
            // 执行了td之后就不是start了。
            buffer.append("<td><a href=");
            Object primaryKeyValue = BeanUtil.getFieldValue(obj, primaryKey);
            /* 这里先按照只显示一个column的内容来做，将来如果需要显示多个column在这里改一下就好了。 */
            Object displayValue = BeanUtil.getFieldValue(obj, showProperty);
            // 注意这里的view可能和edit不同，如果相同的话，只需要将edit的代码拷贝过去就行了。这里默认了Action的类名
            buffer.append("\"" + actionName
                    + ".do?ACTION=view&easyJ.http.Globals.CLASS_NAME="
                    + className + "&ajax=true&" + primaryKey + "="
                    + primaryKeyValue + "\" >");
            buffer.append(displayValue);
            buffer.append("</a></td> ");
            j++;
            // 一行的终点。
            if (j % columnPerLine == 0)
                buffer.append("</tr>\n");
        }
        /* 如果最后一行没有够每行要求的td数，那么就需要在下面补齐 */
        while ((j % columnPerLine) != 0) {
            j++;
            buffer.append("<td></td>");
        }
        buffer.append("</tr>\n");
        buffer.append("</table>\n");
    }

    public static StringBuffer getInterestHtml(Interest interest)
            throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table class=\"border\"><tr><td width=\"100%\" >\n");
        buffer
                .append("<div  class=\"NewsTitleRight\" ><div  class=\"NewsTitleLeft\">\n");
        buffer.append(interest.getTitle());
        buffer.append("</div></div></td></tr>");
        /*
         * 如果method不为空，则应该直接调用method，得到数据，并且进行输出。否则根据condition从className中进行查询
         */
        if (GenericValidator.isBlankOrNull(interest.getMethod())) {
            String conditions = interest.getCondition();
            String className = interest.getClassName();
            Class clazz = BeanUtil.getClass(className);
            String orderbyClauses = interest.getOrderbyColumns();
            int size = interest.getDataRows().intValue();
            Page page = sdp.query(className, conditions, orderbyClauses, 1);
            ArrayList list = page.getPageData();
            if (list.size() < size) {
                size = list.size();
            }
            String primaryKey = (String) BeanUtil.getPubStaticFieldValue(clazz,
                    Const.PRIMARY_KEY);
            for (int i = 0; i < size; i++) {
                Object obj = list.get(i);
                buffer.append("<tr><td><a href=");
                Object primaryKeyValue = BeanUtil
                        .getFieldValue(obj, primaryKey);
                /* 这里先按照只显示一个column的内容来做，将来如果需要显示多个column在这里改一下就好了。 */
                Object displayValue = BeanUtil.getFieldValue(obj, interest
                        .getColumns());
                buffer.append("\"" + interest.getFunctionAddress() + "&"
                        + primaryKey + "=" + primaryKeyValue + "\" />");
                buffer.append(displayValue);
                buffer.append("</a></td> </tr>\n");
            }
            buffer.append("</table>\n");
        } else {

        }

        return buffer;
    }

    /**
     * 如果某个属性的值是来源于一个表，而且要做成checkbox组的形式。
     * className传进来此property对应的checkbox要从哪个表中取
     * @param className          此property所需要的checkbox数据从哪个表中来
     * @param propertyName       此property所对应的名称
     * @param propertyValue      此property的值，形式是 ,1,2,这种形式
     * @param line 代表第几行，如果为-1，则表示在编辑页面，名字不需要加上line，否则是在查询编辑页面，需要加上line作为名字
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getCheckboxs(String className,
            String propertyName, Object propertyValue, int line) throws EasyJException {
        Object obj = BeanUtil.getEmptyObject(className);
        ArrayList list = sdp.query(obj);
        return getCheckboxs(list, propertyName, propertyValue, line);
    }

    /**
     * 从数据字典中根据dicType得到checkbox的html控件
     * @param dicType         对应的数据字典类型，也就是数据字典中不同的数据
     * @param propertyName    此property的名称
     * @param propertyValue   这种情况下，此属性所对应的值应该是用逗号隔开的id，形式是,1,2, 
     * @param line            此checkbox所在的行数，-1代表在编辑页面，其他代表在查询编辑
     * 						  页面，代表所处的行数。
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getCheckBoxsFromDic(String dicType, 
    		String propertyName, Object propertyValue, int line) throws EasyJException {
    	Dictionary lower = new Dictionary();
    	//这样，parentId大于等于2，就不会把表名称选进来
    	lower.setDicType(dicType);
    	lower.setParentId(new Long(2));
    	lower.setUseState("Y");
    	
    	Dictionary upper = (Dictionary)BeanUtil.cloneObject(lower);
    	upper.setParentId(null);
    	
        OrderRule orderRule = new OrderRule();
        orderRule.setOrderColumn("dicSequence");
        orderRule.setOrderDirection(OrderDirection.ASC);
        OrderRule[] orderRules = {
            orderRule
        };
    	ArrayList list = sdp.query(lower, upper, orderRules);
    	return getRadios(list, propertyName, propertyValue, line);
    }

    
    /**
     * 如果某个属性的值是来源于一个表，而且要做成checkbox组的形式。
     * className传进来此property对应的checkbox要从哪个表中取
     * @param className          此property所需要的checkbox数据从哪个表中来
     * @param propertyName       此property所对应的名称
     * @param propertyValue      此property的值，形式是 ,1,2,这种形式
     * @param line 代表第几行，如果为-1，则表示在编辑页面，名字不需要加上line，否则是在查询编辑页面，需要加上line作为名字
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getRadios(String className,
            String propertyName, Object propertyValue, int line) throws EasyJException {
        Object obj = BeanUtil.getEmptyObject(className);
        ArrayList list = sdp.query(obj);
        return getCheckboxs(list, propertyName, propertyValue, line);
    }

    /**
     * 从数据字典中根据dicType得到checkbox的html控件
     * @param dicType         对应的数据字典类型，也就是数据字典中不同的数据
     * @param propertyName    此property的名称
     * @param propertyValue   这种情况下，此属性所对应的值应该是用逗号隔开的id，形式是,1,2, 
     * @param line            此checkbox所在的行数，-1代表在编辑页面，其他代表在查询编辑
     * 						  页面，代表所处的行数。
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getRadiosFromDic(String dicType, 
    		String propertyName, Object propertyValue, int line) throws EasyJException {
    	Dictionary lower = new Dictionary();
    	//这样，parentId大于等于2，就不会把表名称选进来
    	lower.setDicType(dicType);
    	lower.setParentId(new Long(2));
    	lower.setUseState("Y");
    	
    	Dictionary upper = (Dictionary)BeanUtil.cloneObject(lower);
    	upper.setParentId(null);
    	
        OrderRule orderRule = new OrderRule();
        orderRule.setOrderColumn("dicSequence");
        orderRule.setOrderDirection(OrderDirection.ASC);
        OrderRule[] orderRules = {
            orderRule
        };
        ArrayList list = sdp.query(lower, upper, orderRules);
    	return getRadios(list, propertyName, propertyValue, line);
    }
    
    /**
     * 此方法用于如果某个属性的值是来源于一个表，而且要做成checkbox组的形式。
     * className传进来此property对应的checkbox要从哪个表中取。
     * @param list            需要生成checkbox的数据，比直接制定表名或数据字典类型更加灵活。
     * @param propertyName    此property的名称
     * @param propertyValue   这种情况下，此属性所对应的值应该是用逗号隔开的id，形式是,1,2, 
     * @param line            此checkbox所在的行数，-1代表在编辑页面，其他代表在查询编辑
     * 						  页面，代表所处的行数。
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getCheckboxs(ArrayList list,
            String propertyName, Object propertyValue, int line) throws EasyJException {
    	 StringBuffer buffer = new StringBuffer();
    	if (list == null || list.size() == 0)
    		return buffer;
    	
    	Object obj = list.get(0);
    	String idProperty = (String) BeanUtil.getPubStaticFieldValue(obj
                .getClass(), Const.ID_PROPERTY);
        String displayProperty = (String) BeanUtil.getPubStaticFieldValue(
                obj.getClass(), Const.DISPLAY_PROPERTY);
        
        /* 这种情况下，此属性所对应的值应该是用逗号隔开的id，形式是,1,2, */
        String propertyValueStr = (String) propertyValue;
        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            Object idPropertyValue = BeanUtil.getFieldValue(obj, idProperty);
            Object displayPropertyValue = BeanUtil.getFieldValue(obj,
                    displayProperty);
            String checked = "";
            if (!GenericValidator.isBlankOrNull(propertyValueStr)
                    && propertyValueStr.indexOf("," + idPropertyValue + ",") >= 0) {
                checked = "checked";
            }
            if (line == -1)
            	buffer.append("<input type=\"checkbox\" name=\"" + propertyName
                    + "\" value=\"" + Format.format(idPropertyValue) + ","
                    + displayPropertyValue + "\"" + checked + "/>"
                    + displayPropertyValue);
            else 
            	buffer.append("<input type=\"checkbox\" name=\"" + propertyName
                        + line + "\" value=\"" + Format.format(idPropertyValue) + ","
                        + displayPropertyValue + "\"" + checked + "/>"
                        + displayPropertyValue);
        }
        return buffer;
    }
    
    
    /**
     * 此方法用于如果某个属性的值是来源于一个表，而且要做成checkbox组的形式。
     * className传进来此property对应的checkbox要从哪个表中取。
     * @param list            需要生成checkbox的数据，比直接制定表名或数据字典类型更加灵活。
     * @param propertyName    此property的名称
     * @param propertyValue   property所对应的值 
     * @param line            此checkbox所在的行数，-1代表在编辑页面，其他代表在查询编辑
     * 						  页面，代表所处的行数。
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getRadios(ArrayList list,
            String propertyName, Object propertyValue, int line) throws EasyJException {
    	 StringBuffer buffer = new StringBuffer();
    	if (list == null || list.size() == 0)
    		return buffer;
    	
    	Object obj = list.get(0);
    	String idProperty = (String) BeanUtil.getPubStaticFieldValue(obj
                .getClass(), Const.ID_PROPERTY);
        String displayProperty = (String) BeanUtil.getPubStaticFieldValue(
                obj.getClass(), Const.DISPLAY_PROPERTY);
        
        for (int i = 0; i < list.size(); i++) {
            obj = list.get(i);
            Object idPropertyValue = BeanUtil.getFieldValue(obj, idProperty);
            Object displayPropertyValue = BeanUtil.getFieldValue(obj,
                    displayProperty);
            String checked = "";
            if (propertyValue != null 
                    && propertyValue.equals(idPropertyValue)) {
                checked = "checked";
            }
            if (line == -1)
            	buffer.append("<input type=\"radio\" name=\"" + propertyName
                    + "\" value=\"" + Format.format(idPropertyValue)+ "\"" + checked + "/>"
                    + displayPropertyValue);
            else 
            	buffer.append("<input type=\"radio\" name=\"" + propertyName
                        + line + "\" value=\"" + Format.format(idPropertyValue) 
                        + "\"" + checked + "/>"
                        + displayPropertyValue);
        }
        return buffer;
    }
    
    
    /**
     * 此方法为当前的对象生成显示条目
     * 注意，这里返回的是一行的一部分，所以没有tr部分，调用者需要提供tr以及其他和每行相关的内容
     * @param object                需要生成相应条目的对象
     * @param displayProperties     需要显示的属性
     * @param i                     当前数据所在的行号
     * @return 相应的html代码
     */
    public static StringBuffer getDisplayList(Object object, 
    		ArrayList displayProperties, int i) throws EasyJException{
    	StringBuffer buffer = new StringBuffer();
        String primaryKey = (String) BeanUtil.getPubStaticFieldValue(
                object.getClass(), Const.PRIMARY_KEY);
        Object primaryKeyValue = BeanUtil.getFieldValue(object,
                primaryKey);

        int propertySize = displayProperties.size();
        for (int j = 0; j < propertySize; j++) {
            UserPropertyRight property = (UserPropertyRight) displayProperties
                    .get(j);
            buffer.append("<td abbr=\"asdf\" onclick=\"Data.edit('"
                    + primaryKey + "'" + ",'" + primaryKeyValue
                    + "')\">\n");
            String propertyName = property.getPropertyName();
            if (GenericValidator.isBlankOrNull(property
                    .getShowProperty())) {
                buffer.append(Format.formatDisplay(BeanUtil
                        .getFieldValue(object, propertyName)));
                /* 下面在属性propertyName要加上Value是从数据字典中取出数据时显示用的。在创建试图的时候的规定 */
            } else {
                buffer.append(Format.formatDisplay(BeanUtil
                        .getFieldValue(object, property
                                .getShowProperty())));
            }
            buffer.append("</td>\n");
        }

    	return buffer;
    }
    
    
    /**
     * 此方法为当前的对象生成显示编辑条目，就是在查询页面可以直接对数据进行编辑，
     * 注意，这里返回的是一行的一部分，所以没有tr部分，调用者需要提供tr以及其他和每行相关的内容
     * @param object                需要生成相应条目的对象
     * @param displayProperties     需要显示的属性
     * @param errors                用户提交数据时校验的结果，如果有错误需要显示
     * @param i                     当前数据所在的行号
     * @param isComposite           用来指明是主子表还是单表的查询编辑，true主子表
     * @return 相应的html代码
     */
    public static StringBuffer getDisplayEdit(Object object, 
    		ArrayList displayProperties, ValidateErrors errors, int i,
    		boolean isComposite) throws EasyJException{
    	StringBuffer buffer = new StringBuffer();
    	int propertySize = displayProperties.size();
    	for (int k = 0; k < propertySize; k++) {
            UserPropertyRight property = (UserPropertyRight) displayProperties
                    .get(k);
            Integer width = property.getShowWidth();
            if (width != null)
                buffer.append("<td class=\"trContent\" width='"
                        + width + "%'nowrap>\n");
            else
                buffer.append("<td class=\"trContent\" nowrap>\n");
            String propertyName = property.getPropertyName();
            /* 如果不是从数据字典表或者其他表中选取，则显示输入框 */
            if (GenericValidator.isBlankOrNull(property
                    .getPropertyValueTable())) {
                if ("textarea".equals(property.getType())) {
                    buffer.append("<textarea width=\"100%\" name=\""
                           + property.getPropertyName() );
                    //如果不是主子表，则对名称加上主键号，否则在查询的时候会有问题。
                    if(!isComposite)
                    	buffer.append(i);
                    buffer.append("\" id=\""
                           + property.getPropertyName() + i
                           + "\" onblur=\"" + Format.format(Validate
                                .getClientValidate(object.getClass().getName()
                                                            + propertyName))
                                    + "\">"
                                    + Format.format(BeanUtil
                                            .getFieldValue(object,
                                                    propertyName))
                                    + "</textarea>\n");
                }else {
                	if ("webAddress".equals(property.getShowType())) {
                		Object fieldValue = Format.format(BeanUtil.getFieldValue(
                                object, propertyName));
                		buffer.append("<a href = \"http://"+fieldValue+
                				"\" target=\"_blank\"\">" +
                				fieldValue + "</a>");
                	}else {
                	
	                    buffer.append("<input type=\"text\" name=\""
	                            + property.getPropertyName());
		                if(!isComposite)
		                	buffer.append(i);
		                buffer.append("\" id=\""
	                            + property.getPropertyName()
	                            + i
	                            + "\" value=\""
	                            + Format.format(BeanUtil.getFieldValue(
	                                    object, propertyName))
	                            + "\" onblur=\""
	                            + Format.format(Validate
	                                    .getClientValidate(object
	                                            .getClass().getName()
	                                            + propertyName))
	                            + "\" style=\"width:95%\"/>\n");
	                }
                }
                if (errors != null
                        && errors.getErrorMsg(propertyName) != null) {
                    buffer.append(errors.getErrorMsg(propertyName));
                }
            } else {
                /* 如果是从数据字典表或者其他表中选取，则显示下拉列表或者弹出框 */
                String propertyValueTable = property
                        .getPropertyValueTable();
                if (property.getFromDictionary() != null) {
	            	 if ("checkboxs".equals(property.getType())) {
	                 	StringBuffer cheboxBuffer = null;
	                 	if (!property.getFromDictionary()) {
	                 		cheboxBuffer = getCheckboxs(property
	                             .getPropertyValueTable(), property.getPropertyName(),
	                             BeanUtil.getFieldValue(object, property.getPropertyName()), i);
	                 	} else {
	                 		cheboxBuffer = getCheckBoxsFromDic(property
	                             .getPropertyValueTable(), property.getPropertyName(),
	                             BeanUtil.getFieldValue(object, property.getPropertyName()), i);
	                 	}
	                 	buffer.append(cheboxBuffer);
	                 } else if ("radio".equals(property.getType())) {
		                 	StringBuffer radioBuffer = null;
		                 	if (!property.getFromDictionary()) {
		                 		radioBuffer = getRadios(property
		                             .getPropertyValueTable(), property.getPropertyName(),
		                             BeanUtil.getFieldValue(object, property.getPropertyName()), i);
		                 	} else {
		                 		radioBuffer = getRadiosFromDic(property
		                             .getPropertyValueTable(), property.getPropertyName(),
		                             BeanUtil.getFieldValue(object, property.getPropertyName()), i);
		                 	}
		                 	buffer.append(radioBuffer);
	                 } else {
	                	 if (property.getFromDictionary().booleanValue()) {
	                
	                    	String selectName = propertyName ;
	                    	if (!isComposite)
	                    		selectName += i;
	                        StringBuffer selectBuffer = DictionaryProxy
	                                .getHtmlSelect(selectName, propertyValueTable,
	                                        BeanUtil.getFieldValue(
	                                                object,
	                                                propertyName),
	                                        BeanUtil.getFieldValue(
	                                                object,
	                                                property.getShowProperty()));
	                        buffer.append(selectBuffer.toString());
	                    } else {
	                        Object propertyValue = BeanUtil
	                                .getFieldValue(object, propertyName);
	                        String diplayName = property
	                                .getShowProperty();
	                        Object displayValue = BeanUtil
	                                .getFieldValue(object, diplayName);
	                        StringBuffer selectBuffer = HtmlClientComponentService
	                                .getDataSelect(propertyValueTable,
	                                        propertyName,
	                                        propertyValue, diplayName,
	                                        displayValue, i, isComposite);
	                        buffer.append(selectBuffer.toString());
	                    } 
	                }
                }

            }
            buffer.append("</td>\n");

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
            /* 这里是从request当中取到用户拥有权限并选择的字段，将来应该是从缓存当中得到，这是需要修改的地方 */

            /* 对所有的子表属性进行循环 */
            for (int j = 0; j < subClassProperties.length; j++) {
                String subClassProperty = subClassProperties[j];
                String subClassName = BeanUtil.getSubClass(obj.getClass(),
                        subClassProperties[j]);
                ArrayList displayProperties = userCache
                        .getDisplayProperties(subClassName);
                if (displayProperties == null) {
                    return null;
                }
                //用户提交数据校验之后的结果需要显示给用户
                ValidateErrors errors = (ValidateErrors) request
                        .getAttribute(Globals.VALIDATE_ERRORS);
                int propertySize = displayProperties.size();
                buffer.append("<table width=\"100%\" id=\"details\">\n");
                buffer.append("<tr>\n");
                /* 下面是输出题头栏的checkbox */

                buffer.append("<td class=\"trTitle\">操作</td>\n");
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
                            .getPropertyValueTable())) {
                        classes.append(",");
                    } else {
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
                if (dataList != null) {
                    dataSize = dataList.size();
                }
                if (dataSize > 0) {
                    subPrimaryKeyName = (String) BeanUtil
                            .getPrimaryKeyName(dataList.get(0).getClass());
                }
                String propertyValueTable = "";
                for (int i = 0; i < dataSize; i++) {
                    buffer.append("<div><tr id=\"tr" + i + "\">\n");
                    Object object = dataList.get(i);
                    buffer.append("<td class=\"trContent\"><image src=\"image\\delete.gif\" id=\"check"
	                        + i
	                        + "\" onclick=\"Data.hiddenDetail(this)\" /><image src=\"image\\up.gif\" id=\"insertBefore"
	                        + i
	                        + "\" onclick=\"Data.insert(this,'up')\" /><image src=\"image\\down.gif\" id=\"insertAfter"
	                        + i
	                        + "\" onclick=\"Data.insert(this,'down')\" /><input type=\"hidden\" name=\""
	                        + subPrimaryKeyName
	                        + "\" value=\""
	                        + BeanUtil.getFieldValue(object,
	                                subPrimaryKeyName) + "\"/></td>\n");
                    buffer.append(getDisplayEdit(object, displayProperties, 
                    		errors, i, true));
                    buffer
                            .append("<input type=\"hidden\" name=\"detailPropertyClass\" id=\"detailPropertyClass\" value=\""
                                    + propertyValueTable + "\"/>\n");
                    buffer
                            .append("<input type=\"hidden\" name=\"useState\" id=\"useState"
                                    + i + "\" value=\"Y\"/>\n");
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
            return null;
        }
    }
    
    /**
     * 
     * @param request             需要从中取得数据
     * @param columnsPerLine      每行显示多少个column
     * @param start               用来表示从第几个property开始显示
     * @param propertySize        用来表示从start开始共显示多少个property -1 表示没有限制，有多少显示多少。
     * @param hint                用来表示是否在每个字段后面显示填写内容的说明。默认不显示
     * @param hintType            用来表示用什么样的方式来显示提示。可以是鼠标移动提示，
     *                                                    可以是直接提示。
     * @return                    返回页面表示的数据
     * @throws EasyJException
     */
    public static StringBuffer getEdit(HttpServletRequest request, Object object,
            ArrayList editProperties,
            String columnsPerLine, int start, int propertySize, String hint,
            String hintType) throws EasyJException {
        Class clazz = object.getClass();
        String primaryKey = (String) BeanUtil.getPubStaticFieldValue(clazz,
                Const.PRIMARY_KEY);

        ValidateErrors errors = (ValidateErrors) request
                .getAttribute(Globals.VALIDATE_ERRORS);
        
        if (object == null || editProperties == null) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();

        buffer.append("<input type=\"hidden\"  id=\"id\" name=\"" + primaryKey
                + "\" value=\""
                + Format.format(BeanUtil.getFieldValue(object, primaryKey))
                + "\"/>");
        buffer.append("<table width=\"100%\" class=\"query\" >\n");
        ArrayList textareaProperties = new ArrayList();
        ArrayList checkboxsProperties = new ArrayList();
        int size = propertySize;
        if (size == -1) {
            size = editProperties.size();
            /* sizePerLin指每一行有多少个字段 */
        }
        int sizePerLine = Integer.parseInt(columnsPerLine);
        int j = 0;
        int tdWidth = 100;
        if ("Y".equals(hint))
            tdWidth = 70 / sizePerLine;
        else
            tdWidth = 90 / sizePerLine;

        for (int i = start; i < size; i++) {
            UserPropertyRight property = (UserPropertyRight) editProperties
                    .get(i);
            if (j % sizePerLine == 0) {
                buffer.append("<tr>\n");
            }
            String propertyName = property.getPropertyName();
            /* 因为如果是textarea的话，就会放到下面显示，所以这些只有当不是textarea的时候才显示 */
            String displayChineseName = "<td width=\"15%\" align=\"left\" style="
                        +"'text-align:justify;text-justify:distribute-all-lines'>"
                    + property.getPropertyChiName()
                    + ": </td> <td align=\"left\" width=\"" + tdWidth + "%\">";
            if ("checkboxs".equals(property.getType())) {
                checkboxsProperties.add(property);
                continue;
            }
            if ("textarea".equals(property.getType())) {
                textareaProperties.add(property);
                continue;
            }

            if (GenericValidator
                    .isBlankOrNull(property.getPropertyValueTable())) {
                /* 如果不是从数据字典表或者其他表中选取，则显示输入框 */

                // 如果是隐藏字段，则输入hidden
                if (GenericValidator.isBlankOrNull(property.getHidden())) {
                    buffer.append(displayChineseName + "\n");
                    buffer.append("<input type=\"text\" name=\""
                            + property.getPropertyName()
                            + "\" value=\""
                            + Format.format(BeanUtil.getFieldValue(object,
                                    propertyName))
                            + "\" onblur=\""
                            + Format.format(Validate.getClientValidate(object
                                    .getClass().getName()
                                    + propertyName))
                            + "\" style=\"width:97%;\"  />\n");
                    if (errors != null
                            && errors.getErrorMsg(propertyName) != null) {
                        buffer.append(errors.getErrorMsg(propertyName));
                    }
                } else {
                    buffer.append("<input type=\"hidden\" name=\""
                            + property.getPropertyName()
                            + "\" value=\""
                            + Format.format(BeanUtil.getFieldValue(object,
                                    propertyName)) + "\"/>\n");
                }
            } else {
                buffer.append(displayChineseName + "\n");
                // buffer.append(displayChineseName);
                /* 如果是从数据字典表或者其他表中选取，则显示下拉列表或者弹出框 */
                String propertyValueTable = property.getPropertyValueTable();
                if (property.getFromDictionary() != null) {
                    if (property.getFromDictionary().booleanValue()) {
                        StringBuffer selectBuffer = DictionaryProxy
                                .getHtmlSelect(propertyName,
                                        propertyValueTable, BeanUtil
                                                .getFieldValue(object,
                                                        propertyName), BeanUtil
                                                .getFieldValue(object,
                                                        propertyName + "Value"));
                        buffer.append(selectBuffer.toString());
                    } else {
                        StringBuffer selectBuffer = HtmlClientComponentService
                                .getSelect(propertyValueTable, BeanUtil
                                        .getFieldValue(object, propertyName));
                        buffer.append(selectBuffer.toString());
                    }
                }

            }
            buffer.append("</td>\n");
            if ("Y".equals(hint)) {
                if ("cursor".equals(hintType))
                    buffer.append("<td><img src=\"/image/ask.gif\" alt=\""
                            + Format.format(property.getDescription())
                            + "\"></img></td>");
                else
                    buffer.append("<td>"
                            + Format.format(property.getDescription())
                            + "</td>");
            }

            if (!"textarea".equals(property.getType())
                    || "checkboxs".equals(property.getType())) {
                j++;
            }
            if (j % sizePerLine == 0) {
                buffer.append("</tr>");
            }
        }
        /* 如果最后一行没有够每行要求的td数，那么就需要在下面补齐 */
        while ((j % sizePerLine) != 0) {
            j++;
            buffer.append("<td></td>");
        }
        buffer.append("</tr>\n");
        buffer.append("</table>\n");
        for (int i = 0; i < textareaProperties.size(); i++) {
            UserPropertyRight property = (UserPropertyRight) textareaProperties
                    .get(i);
            buffer.append("<table><tr ><td width=\"15%\" align=\"left\">"
                    + property.getPropertyChiName()
                    + "</td><td align=\"left\">\n");
            /* 大小应该有屏幕大小决定的，将来需要改 */
            buffer.append("<textarea style=\"width:97%\" rows=\"5\" name=\""
                    + property.getPropertyName()
                    + "\">"
                    + Format.format(BeanUtil.getFieldValue(object, property
                            .getPropertyName())) + "</textarea>");
            buffer.append("</td></tr></table>\n");
        }
        for (int i = 0; i < checkboxsProperties.size(); i++) {
            UserPropertyRight property = (UserPropertyRight) checkboxsProperties
                    .get(i);
            buffer.append("<table><tr><td width=\"15%\">"
                    + property.getPropertyChiName()
                    + "</td><td align=\"left\">\n");
            /* 大小应该有屏幕大小决定的，将来需要改 */
            StringBuffer cheboxBuffer = getCheckboxs(property
                    .getPropertyValueTable(), property.getPropertyName(),
                    BeanUtil.getFieldValue(object, property.getPropertyName()), -1);
            buffer.append(cheboxBuffer.toString() + "\n");
            buffer.append("</td></tr></table>\n");
        }
        return buffer;
    }
    
    /**
     * 
     * @param request             需要从中取得数据
     * @param columnsPerLine      每行显示多少个column
     * @param start               用来表示从第几个property开始显示
     * @param propertySize        用来表示从start开始共显示多少个property -1 表示没有限制，有多少显示多少。
     * @param hint                用来表示是否在每个字段后面显示填写内容的说明。默认不显示
     * @param hintType            用来表示用什么样的方式来显示提示。可以是鼠标移动提示，
     * 							  可以是直接提示。
     * @return                    返回页面表示的数据
     * @throws EasyJException
     */
    public static StringBuffer getEdit(HttpServletRequest request,
            String columnsPerLine, int start, int propertySize, String hint,
            String hintType) throws EasyJException {
        Object object = request.getAttribute(Globals.OBJECT);
        String className = request.getParameter(Globals.CLASS_NAME);
        Class clazz = BeanUtil.getClass(className);


        /* 将来要进行替换 用户ID */
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        ArrayList editProperties = userCache.getEditProperties(className);

        return getEdit(request, object, editProperties, 
                columnsPerLine,start,propertySize,hint,hintType);
    }

    public static StringBuffer getForumDisplay(HttpServletRequest request)
            throws EasyJException {
        String queryByTopic = request.getParameter("queryByTopic");
        String className = request.getParameter(Globals.CLASS_NAME);
        /* 将来要进行替换 用户ID */
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        StringBuffer buffer = new StringBuffer();
        /* 这里是从request当中取到用户拥有权限并选择的字段，将来应该是从缓存当中得到，这是需要修改的地方 */
        ArrayList displayProperties = userCache.getDisplayProperties(className);
        StringBuffer propertyBuffer = new StringBuffer();
        if (displayProperties == null || displayProperties.size() == 0) {
            return null;
        }
        int propertySize = displayProperties.size();
        buffer
                .append("<table width=\"100%\" id=\"details\" class=\"details\">\n");
        buffer.append("<tr  class=\"listTableHead\">\n");
        /* 下面是输出题头栏的checkbox */

        buffer
                .append("<td align=\"center\" class=\"trTitle\" width=\"5%\"><input type=\"checkbox\" name=\"checkAll\" onclick=\"Data.checkAll()\"/>\n");
        buffer.append("</td>\n");
        /* 下面输入用来浏览的数据字段 */
        for (int i = 0; i < propertySize; i++) {
            UserPropertyRight property = (UserPropertyRight) displayProperties
                    .get(i);
            propertyBuffer.append(property.getPropertyName());
            if (i != propertySize - 1) {
                propertyBuffer.append(",");
            }
            buffer.append("<td nowrap class=\"trTitle\" align=\"center\">\n");
            buffer
                    .append("<a style=\"text-decoration:none\" href=\"javascript:Data.orderBy('"
                            + property.getPropertyName()
                            + "')\">"
                            + property.getPropertyChiName() + "</a>\n");
            buffer.append("</td>\n");
        }
        buffer.append("</tr>\n");
        buffer.append("<input type=\"hidden\"  name=\"properties\" value=\""
                + propertyBuffer.toString() + "\"/>");
        if (request.getAttribute(Globals.PAGE) != null) {
            Page page = (Page) request.getAttribute(Globals.PAGE);
            ArrayList dataList = page.getPageData();
            int dataSize = dataList.size();
            for (int i = 0; i < dataSize; i++) {
                Object object = dataList.get(i);
                String primaryKey = (String) BeanUtil.getPubStaticFieldValue(
                        object.getClass(), Const.PRIMARY_KEY);
                Object primaryKeyValue = BeanUtil.getFieldValue(object,
                        primaryKey);
                buffer.append("<tr id=\"tr" + primaryKeyValue + "\">\n");
                buffer
                        .append("<td class=\"trContent\"  align=\"center\" width=\"5%\"><input type=\"checkbox\" name=\"check\" value=\""
                                + primaryKeyValue + "\"/> </td>\n");
                for (int j = 0; j < propertySize; j++) {
                    UserPropertyRight property = (UserPropertyRight) displayProperties
                            .get(j);
                    buffer
                            .append("<td class=\"trContent\"abbr=\"asdf\" align=\"center\" onclick=\"Discuss.expandContent('"
                                    + primaryKey
                                    + "'"
                                    + ",'"
                                    + primaryKeyValue
                                    + "','"
                                    + queryByTopic
                                    + "','"
                                    + primaryKeyValue + "')\" nowrap>\n");
                    String propertyName = property.getPropertyName();
                    if (GenericValidator.isBlankOrNull(property
                            .getShowProperty())) {
                        buffer.append(Format.formatDisplay(BeanUtil
                                .getFieldValue(object, propertyName)));
                        /* 下面在属性propertyName要加上Value是从数据字典中取出数据时显示用的。在创建试图的时候的规定 */
                    } else {
                        buffer.append(Format.formatDisplay(BeanUtil
                                .getFieldValue(object, property
                                        .getShowProperty())));
                    }
                    buffer.append("</td>\n");
                }
                buffer.append("</tr>\n");
            }
        }
        buffer.append("</table>\n");
        return buffer;
    }

    public static StringBuffer getForumExpand(Object object,
            HttpServletRequest request, String queryByTopic)
            throws EasyJException {
        String className = request.getParameter(Globals.CLASS_NAME);
        /* 将来要进行替换 用户ID */
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        ArrayList displayProperties = userCache.getDisplayProperties(className);
        Long discussSourceId = (Long) BeanUtil.getFieldValue(object,
                "discussSourceId");
        Long discussTopicId = (Long) BeanUtil.getFieldValue(object,
                "discussTopicId");
        String discussTitle = (String) BeanUtil.getFieldValue(object,
                "discussTitle");
        int propertySize = displayProperties.size();
        String primaryKey = (String) BeanUtil.getPubStaticFieldValue(object
                .getClass(), Const.PRIMARY_KEY);
        String contentProperty = (String) BeanUtil.getPubStaticFieldValue(
                object.getClass(), "EXPAND_PROPERTY");
        String content = (String) BeanUtil.getFieldValue(object,
                contentProperty);
        Object primaryKeyValue = BeanUtil.getFieldValue(object, primaryKey);
        StringBuffer buffer = new StringBuffer();
        Long contentId = null;
        if ("true".equals(queryByTopic)) {
            contentId = discussTopicId;
        } else {
            contentId = (Long) primaryKeyValue;
        }
        buffer
                .append("<tr id=\"content"
                        + primaryKeyValue
                        + "\" class=\"totalContent"
                        + contentId
                        + "\" style=\"display:block\" ><td width=\"100%\" colSpan=\""
                        + (propertySize + 1)
                        + "\" nowrap>"
                        + "<table class=\"border\">"
                        + "<tr align=\"left\"><td id=\"td"
                        + primaryKeyValue
                        + "\">"
                        + content
                        + "</td></tr>"
                        + "<tr align=\"center\"><td><input type=\"button\" class=\"button\" value=\"回复\" onclick=\"Discuss.reply("
                        + primaryKeyValue
                        + ")\"/>"
                        + "<input type=\"button\" class=\"button\" value=\"写信给作者\" onclick=\"sendMessage("
                        + primaryKeyValue + ")\"/></td></tr>");
        buffer
                .append("<tr  style=\"display:none\" id=\"title"
                        + primaryKeyValue
                        + "\"><td colSpan=\""
                        + (propertySize + 1)
                        + "\">标题：<input type=\"text\" style=\"WIDTH:80%;\" name=\"discussTitle"
                        + primaryKeyValue
                        + "\" value=\"回复："
                        + discussTitle
                        + "\"/></td></tr>"
                        + "<tr id=\"reply"
                        + primaryKeyValue
                        + "\"  style=\"display:none\" ><td width=\"100%\" colSpan=\""
                        + (propertySize + 1)
                        + "\" >"
                        + "<table class=\"border\"><tr align=\"center\"><td><textarea style=\"WIDTH:100%;\" rows=\"5\" name=\"discussContent"
                        + primaryKeyValue
                        + "\"></textarea></td></tr>"
                        + "<tr align=\"center\"><td><input type=\"button\" class=\"button\" value=\"保存\" onclick=\"Discuss.saveReply("
                        + primaryKeyValue + "," + discussSourceId + ","
                        + discussTopicId + "," + queryByTopic + ")\"/>"
                        + "</td></tr></table></td></tr></table></td></tr>\n");
        return buffer;
    }

    /**
     * 此方法用来获得分页显示的数据部分。这里可以显示两种，一种是纯粹查看的，
     * 还有一种是可以直接编辑的。由参数editable来确定
     * @param request            其中保存了需要显示的数据
     * @param editable           用来指明当前生成的display是否可以编辑
     * @return
     * @throws EasyJException
     */
    public static StringBuffer getDisplay(HttpServletRequest request, 
    		boolean editable)
            throws EasyJException {
        String className = request.getParameter(Globals.CLASS_NAME);
        /* 将来要进行替换 用户ID */
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        StringBuffer buffer = new StringBuffer();
        /* 这里是从request当中取到用户拥有权限并选择的字段，将来应该是从缓存当中得到，这是需要修改的地方 */
        ArrayList displayProperties = userCache.getDisplayProperties(className);
        if (displayProperties == null || displayProperties.size() == 0) {
            return null;
        }
        int propertySize = displayProperties.size();
        if (editable)
        	buffer.append("<table width=\"100%\" " 
        			+"id=\"details\" class=\"editDetails\">\n");
        else
        	buffer.append("<table width=\"100%\" " 
        			+"id=\"details\" class=\"details\">\n");
        buffer.append("<tr  class=\"listTableHead\">\n");
        /* 下面是输出题头栏的checkbox */

        buffer
                .append("<th align=\"center\" class=\"trTitle\" width=\"5%\"><input type=\"checkbox\" name=\"checkAll\" onclick=\"Data.checkAll()\"/>\n");
        buffer.append("</th>\n");
        /* 下面输入用来浏览的数据字段 */
        for (int i = 0; i < propertySize; i++) {
            UserPropertyRight property = (UserPropertyRight) displayProperties
                    .get(i);
            buffer.append("<th class=\"order\" onclick=\"Data.orderBy('"
                    + property.getPropertyName() + "', "+editable+")\">"
                    + property.getPropertyChiName() + "</a>\n");
            buffer.append("</th>\n");
        }

        // 判断是否需要显示操作栏

        PageFunction pf = new PageFunction();
        if (editable)
        	pf.setFunctionPositionRelatedValue(
        			new Long(Globals.FUNCTION_LIST_EDIT));
        else
        	pf.setFunctionPositionRelatedValue(new Long(Globals.FUNCTION_LIST));

        // 得到所有在list显示的功能
        ArrayList pfList = sdp.query(pf);

        // 过滤掉不符合的，符合的包括function_class为空的，或者function_class和 class_name 一致的。
        // 不能加上function_class为查询条件，因为可能会过滤掉function_class为空的。
        ArrayList effectList = new ArrayList();

        for (int i = 0; i < pfList.size(); i++) {
            pf = (PageFunction) pfList.get(i);
            if (pf.getFunctionClass() == null
                    || className.equals(pf.getFunctionClass()))
                effectList.add(pf);
        }

        if (effectList.size() != 0)
            buffer.append("<th>操作</th>");

        buffer.append("</tr>\n");
        if (request.getAttribute(Globals.PAGE) != null) {
            Page page = (Page) request.getAttribute(Globals.PAGE);
            ArrayList dataList = page.getPageData();
            int dataSize = dataList.size();
            for (int i = 0; i < dataSize; i++) {
                Object object = dataList.get(i);


                String primaryKey = (String) BeanUtil.getPubStaticFieldValue(
                        object.getClass(), Const.PRIMARY_KEY);
                Object primaryKeyValue = BeanUtil.getFieldValue(object,
                        primaryKey);
                
                buffer.append("<tr id = \"tr"+primaryKeyValue+"\">\n");
                buffer.append("<td><input type=\"checkbox\" name=\"check\" id=\"check"
                        + i + "\"  value=\"" + primaryKeyValue
                        + "\"/> </td>\n");
                
                //根据用户是需要什么样的显示页面来确定是否需要编辑
                if (editable) {
                	buffer.append(getDisplayEdit(object, displayProperties, 
                			null, i, false));
                } else {
                	buffer.append(getDisplayList(object, displayProperties, i));
                }
                
                // 输出每列的功能按钮
                if (effectList.size() != 0) {
                    buffer.append("<td>");
                    // 下面将主键值加入到functionName当中(见表Page_Function)，需要functionName带有括号，例如Data.apply('cn.edu.pku.dr.requirement.elicitation.action.ProjectAction')，
                    // 这里是要在括号内加入主键名以及主键值作为参数，例如加入后可能变为apply('cn.edu.pku.dr.requirement.elicitation.action.ProjectAction','projectId',1);
                    for (int k = 0; k < effectList.size(); k++) {
                        pf = (PageFunction) effectList.get(k);
                        // 如果功能的出现需要条件，则要进行判断
                        Integer result = null;
                        // 这里0，1，-1的含义见FunctionCondition
                        if (pf.getFunctionCondition() != null) {
                            result = (Integer) BeanUtil.invokeStaticMethod(
                                    FunctionCondition.class, pf.getFunctionCondition(),
                                    new Object[] {object, userId });
                            // 如果值不为真，则继续。
                            if (result.intValue() == -1)
                                continue;
                        }
                        String functionName = pf.getFunctionName();
                        
                        //这里i代表行号，可能会被用到，也可能没有用到。看具体的方法
                        functionName = functionName.trim().substring(0,
                                functionName.length() - 1)
                                + ",'" + primaryKey + "',"
                                + primaryKeyValue + "," + i +")";
                        if (result == null || result.intValue() == 1)
                            buffer.append("<input type=\"button\" value=\""
                                    + pf.getFunctionDisplayValue()
                                    + "\" onclick=\"" + functionName + "\"/>");
                        else
                            buffer
                                    .append("<input type=\"button\" disabled value=\""
                                            + pf.getFunctionDisplayValue()
                                            + "\" onclick=\""
                                            + functionName
                                            + "\"/>");
                    }

                    buffer.append("</td>");
                }
                buffer.append("</tr>\n");
            }
        }
        buffer.append("</table>\n");
        return buffer;
    }

    public static StringBuffer getQuery(HttpServletRequest request,
            String columnsPerLine) throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        Object lower = request.getAttribute(Globals.LOWER);
        Object upper = request.getAttribute(Globals.UPPER);
        String className = request.getParameter(Globals.CLASS_NAME);
        /* 将来要进行替换 用户ID */
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        /* 这里是从request当中取到用户拥有权限并选择的字段，将来应该是从缓存当中得到，这是需要修改的地方 */
        ArrayList queryProperties = userCache.getQueryProperties(className);

        if (lower == null || upper == null || queryProperties == null
                || queryProperties.size() == 0) {
            return null;
        }
        buffer.append("<table width=\"100%\" class=\"query\">\n");
        int size = queryProperties.size();
        /* sizePerLin指每一行有多少个字段 */
        int sizePerLine = Integer.parseInt(columnsPerLine);
        int j = 0;
        for (int i = 0; i < size; i++) {
            UserPropertyRight property = (UserPropertyRight) queryProperties
                    .get(i);
            if (j % sizePerLine == 0) {
                buffer.append("<tr>\n");
            }
            String propertyName = property.getPropertyName();
            if ("Y".equals(property.getScopeQuery())) {
                /* 如果范围查询 */
                buffer.append("<td nowrap>" + property.getPropertyChiName()
                        + "</td>\n");
                buffer.append("<td>\n");
                buffer.append("<input type=\"text\" name=\"lower"
                        + property.getPropertyName()
                        + "\" value=\""
                        + Format.format(BeanUtil.getFieldValue(lower,
                                propertyName)) + "\"/>--\n");
                buffer.append("<input type=\"text\" name=\"upper"
                        + property.getPropertyName()
                        + "\" value=\""
                        + Format.format(BeanUtil.getFieldValue(upper,
                                propertyName)) + "\"/>\n");
                buffer.append("</td>\n");
            } else {
                /* 如果不是hidden字段 */
                if (GenericValidator.isBlankOrNull(property.getHidden())) {
                    buffer.append("<td>" + property.getPropertyChiName()
                            + "</td>\n");
                }
                propertyName = property.getPropertyName();
                buffer.append("<td>\n");
                /* 如果不是范围查询 */
                if (GenericValidator.isBlankOrNull(property
                        .getPropertyValueTable())) {
                    /* 如果不是从数据字典表或者其他表中选取，则显示输入框 */
                    if (GenericValidator.isBlankOrNull(property.getHidden())) {
                        buffer.append("<input type=\"text\" name=\""
                                + property.getPropertyName()
                                + "\" value=\""
                                + Format.format(BeanUtil.getFieldValue(lower,
                                        propertyName)) + "\"/>\n");
                    } else {
                        buffer.append("<input type=\"hidden\" name=\""
                                + property.getPropertyName()
                                + "\" value=\""
                                + Format.format(BeanUtil.getFieldValue(lower,
                                        propertyName)) + "\"/>\n");

                    }
                } else {
                    /* 如果是从数据字典表或者其他表中选取，则显示下拉列表或者弹出框 */
                    String propertyValueTable = property
                            .getPropertyValueTable();
                    if (property.getFromDictionary() != null) {
                        if (property.getFromDictionary().booleanValue()) {
                            StringBuffer selectBuffer = DictionaryProxy
                                    .getHtmlSelect(propertyName,
                                            propertyValueTable, BeanUtil
                                                    .getFieldValue(lower,
                                                            propertyName),
                                            BeanUtil.getFieldValue(lower,
                                                    property.getShowProperty()));
                            buffer.append(selectBuffer);
                        } else {
                            if ("select".equalsIgnoreCase(property
                                    .getShowType())) {
                                StringBuffer selectBuffer = HtmlClientComponentService
                                        .getSelect(propertyValueTable, BeanUtil
                                                .getFieldValue(lower,
                                                        propertyName));
                                buffer.append(selectBuffer);
                            } else {
                                buffer.append("<input type=\"text\" name=\""
                                        + property.getShowProperty()
                                        + "\" value=\""
                                        + Format.format(BeanUtil.getFieldValue(
                                                lower, property
                                                        .getShowProperty()))
                                        + "\"/>");
                            }
                        }
                    }
                }
                buffer.append("</td>");
            }
            j++;
            if (j % sizePerLine == 0) {
                buffer.append("</tr>");
            }
        }
        /* 如果最后一行没有够每行要求的td数，那么就需要在下面补齐 */
        while ((j % 3) != 0) {
            j++;
            buffer.append("<td></td>");
        }
        buffer.append("</tr>");
        buffer.append("</table>");
        return buffer;
    }

    /**
     * 此方法得到用户在此操作页面上的功能。 需要注意两个地方：一个功能是否在页面上显示需要三个量来决定， 一个就是用户是否被赋予了相应功能的权限。
     * 一个就是此功能的类型，只要有了权限就能执行，还是只有是创建者才能执行，或者是同组可以执行，所以每个功能都有一个属性U,G,A分别代表只用户，组和所有人情况。
     * 对于数据来说也是，也分为U,G,A，那么对于此数据功能的权限规则应该如下： 如果数据的权限小于功能的权限，则应该执行数据的权限。
     * 如果数据的权限大于功能的权限就应该执行功能的权限
     * 
     * @param request
     *                HttpServletRequest
     * @param className
     *                String
     * @param pageName
     *                String
     * @param position
     *                String
     * @throws EasyJException
     * @return StringBuffer
     */
    public static StringBuffer getFunction(HttpServletRequest request,
            String className, String pageName, String position)
            throws EasyJException {
        // 得到当前的上下文
        Context context = sdp.getContext();

        // 定义当前登陆的人在context中的角色，在不同的环境下角色的区分是不同的。
        // 在当前这个项目里面context主要是指Project Context，而不同的项目context可能会不同，角色也可能会不同。
        // 所以在将来这里应该用某种规则或者配置文件的形式来表达，而不应该写程序在这里。
        // 但一般情况下角色大部分分为owner, group, other，所以这里就暂时直接设定为
        int role = DictionaryConstant.OTHER;
        if (context != null) {
            role = context.getProjectRole();
        }

        /* 获得功能列表 */
        StringBuffer buffer = new StringBuffer();
        PageFunction function = new PageFunction();
        // function.setFunctionClass(className);
        function.setFunctionPage(pageName);
        // function.setFunctionPosition(new Long(position));
        function.setFunctionPositionRelatedValue(new Long(position));
        /* 现在为了调试方便，将来function要从cache当中取 */
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ArrayList functionList = (ArrayList) sdp.query(function);

        buffer.append("<table  width=\"100%\" class=\"function\"><tr><td>\n");

        /* 对数据进行环境过滤 */
        DataContextFilter filter = DataContextFilter.getInstance();
        filter.setContext(context);
        functionList = filter.filter(functionList);
        int size = functionList.size();

        for (int i = 0; i < size; i++) {
            function = (PageFunction) functionList.get(i);
            String functionName = function.getFunctionName(); // 功能的javascript
            // 名称
            String functionDisplayValue = function.getFunctionDisplayValue();
            buffer.append("<input type=\"button\" class=\"button\" value=\""
                    + functionDisplayValue + "\" onclick=\"" + functionName
                    + "\"/>\n");
        }
        buffer.append("</td></tr></table>\n");
        return buffer;
    }

    public static StringBuffer getFunctionScript(HttpServletRequest request,
            String pageName) throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        /* 获得功能列表 */
        PageFunction function = new PageFunction();
        function.setFunctionPage(pageName);
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ArrayList functionList = (ArrayList) sdp.query(function);
        buffer.append("<script language=\"javascript\">\n");
        int size = functionList.size();
        for (int i = 0; i < size; i++) {
            function = (PageFunction) functionList.get(i);
            if (!GenericValidator.isBlankOrNull(function.getFunctionContent())) {
                buffer.append(function.getFunctionContent());
            }
        }
        buffer.append("</script>\n");
        return buffer;
    }

    /* position 代表是执行哪个部分的查询，包括主查询和弹出窗口查询，对应两个js文件，Data.js和PopUpWindow.js */
    public static StringBuffer getPage(HttpServletRequest request,
            String postion, boolean editable) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table><tr><td>\n");
        Page aPage = (Page) request.getAttribute(Globals.PAGE);
        Long totalPage = null;
        Long totalNum = null;
        if (aPage != null) {
            totalPage = aPage.getTotalPage();
            totalNum = aPage.getTotalNum();
        }
        buffer.append("总条数：" + totalNum + " 共" + totalPage + "页 </td>");
        buffer.append("<td align=\"right\">");

        String actionName = request.getParameter("actionName");
        String pageNo = request.getParameter(Globals.PAGENO);
        int currentPage = 0;
        if (!GenericValidator.isBlankOrNull(pageNo)) {
            currentPage = Integer.parseInt(pageNo);
        }
        int pageNums = (currentPage - 1) / 10;
        if (currentPage <= 10) {
            buffer.append(" << ");
        } else {
            buffer.append("<a href=\"javascript:" + postion
                    + ".submitThisPage('" + (pageNums * 10) + "','" + totalPage
                    + "," + editable + "')\"> << </a>");
        }
        int pages = 10;
        if ((pageNums * 10 + 10) > totalPage.intValue()) {
            pages = totalPage.intValue() - pageNums * 10;
        }
        for (int i = 1; i <= pages; i++) {
            if (pageNums * 10 + i == currentPage) {
                buffer.append((pageNums * 10 + i));
                buffer.append("  ");
            } else {
                buffer.append("<a href='javascript:" + postion
                        + ".submitThisPage(" + (pageNums * 10 + i) + ","
                        + totalPage + "," + 
                        editable +")'>" + (pageNums * 10 + i) + "</a>");
                buffer.append("  ");
            }
        }
        if ((pageNums * 10 + 10) < totalPage.intValue()) {
            buffer.append("<a href='javascript:" + postion + ".submitThisPage("
                    + (pageNums * 10 + 11) + "','" + totalPage + "," + 
                    editable + ")'> >> </a>");
        } else {
            buffer.append(" >> ");
        }
        buffer
                .append("跳转到：<input type=\"text\" size=\"4\" "
                		+"class=\"inputButton\" value=\"\" "
                		+"onkeydown=\"javascript:if(window.event.keyCode==13) "
                        + postion
                        + ".submitThisPage(this.value,"
                        + totalPage + "," + editable 
                        + ")\">");
        buffer.append("</td></tr></table>\n");
        return buffer;
    }

    public static StringBuffer getQueryHidden(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"hidden\" name=\""
                + Globals.ORDER_BY_COLUMN + "\" id=\""
                + Globals.ORDER_BY_COLUMN + "\" value=\""
                + Format.format(request.getParameter(Globals.ORDER_BY_COLUMN))
                + "\"/>\n");
        buffer.append("<input type=\"hidden\" name=\""
                + Globals.ORDER_DIRECTION + "\" id=\""
                + Globals.ORDER_DIRECTION + "\" value=\""
                + Format.format(request.getParameter(Globals.ORDER_DIRECTION))
                + "\"/>\n");
        buffer.append("<input type=\"hidden\" name=\"" + Globals.CLASS_NAME
                + "\" id=\"" + Globals.CLASS_NAME + "\" value=\""
                + request.getParameter(Globals.CLASS_NAME) + "\"/>\n");
        String pageNo = "";
        if (request.getParameter(Globals.PAGENO) == null) {
            pageNo = "1";
        } else {
            pageNo = request.getParameter(Globals.PAGENO);
        }
        buffer.append("<input type=\"hidden\" id=\"" + Globals.ACTION_NAME
                + "\" value=\"" + request.getAttribute(Globals.ACTION_NAME)
                + "\"/>\n");
        buffer
                .append(" <input type=\"hidden\" name=\"PAGENO\" id=\"PAGENO\" value=\"\"/>");
        
        //下面将查询字段记录到properties这个input当中
        SysUserCache userCache = (SysUserCache) request.getSession()
        .getAttribute(Globals.SYS_USER_CACHE);
        String className = request.getParameter(Globals.CLASS_NAME);
		ArrayList displayProperties = userCache.getDisplayProperties(className);
		StringBuffer properties = new StringBuffer();
		for (int i = 0; i< displayProperties.size(); i++) {
			UserPropertyRight property = (UserPropertyRight)displayProperties.
											get(i);
			properties.append(property.getPropertyName());
			if (i != displayProperties.size()-1)
				properties.append(",");
		}
		buffer.append("<input type=\"hidden\" id = \"properties\" name = \"properties\" " 
				+ " value = \"" + properties + "\"");
        return buffer;
    }
    
    public static String getHistory() {
        return "<table ><tr>"
        +"<td align=\"center\"><img alt=\"\" src=\"image/arrow_left.gif\"  class=\"hide_todo\" onclick=\"Ajax.loadHistory('pre')\"/>" 
        + "<img alt=\"\" src=\"image/arrow_right.gif\"  class=\"hide_todo\" onclick=\"Ajax.loadHistory('next')\"/></td>" 
        + "</tr></table>";
    }
}
