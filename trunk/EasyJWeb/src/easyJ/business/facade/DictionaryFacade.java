package easyJ.business.facade;

import easyJ.system.data.Dictionary;
import java.util.List;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.OrderDirection;
import easyJ.common.Format;

public class DictionaryFacade extends SingleDataFacade {
    private static CommonFacade sdf = SingleDataFacade.getInstance();

    /*
     * 根据属性名propertyName以及数据字典类型propertyValueData来获得显示HTML内容,
     * propertyValue用来指示哪个option被选中,displayValue
     * 用来当使用tree来选择的时候，显示数据用的。
     */
    public static StringBuffer getHtmlSelect(String propertyName,
            String propertyValueTable, Object propertyValue, Object displayValue)
            throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        Dictionary dic = new Dictionary();
        dic.setDicType(propertyValueTable);        
        dic.setUseState("Y");
        OrderRule orderRule = new OrderRule();
        orderRule.setOrderColumn("dicSequence");
        orderRule.setOrderDirection(OrderDirection.ASC);
        OrderRule[] orderRules = {
            orderRule
        };
        List valueDatas = sdf.query(dic, orderRules);
        String showType = null;
        Object rootId = null;
        if (valueDatas.size() > 0) {
            dic = (Dictionary) valueDatas.get(0);
            showType = dic.getShowType();
            rootId = dic.getDicValueId();
        }
        if ("select".equals(showType)) {
            buffer.append("<select name=\"" + propertyName + "\">\n");
            buffer.append("<option value=\"\"></option>\n");
            for (int i = 1; i < valueDatas.size(); i++) {
                dic = (Dictionary) valueDatas.get(i);
                if (dic.getDicValueId().equals(propertyValue))
                    buffer.append("<option value=\"" + dic.getDicValueId()
                            + "\" selected>" + dic.getDicValueName()
                            + "</option>\n");
                else
                    buffer.append("<option value=\"" + dic.getDicValueId()
                            + "\">" + dic.getDicValueName() + "</option>\n");
            }
            buffer.append("</select>\n");
        } else {
            buffer
                    .append("<input type=\"text\" onclick=\"PopUpWindow.showTree('"
                            + propertyName
                            + "Value','"
                            + propertyName
                            + "','"
                            + rootId
                            + "')\" readonly name=\""
                            + propertyName
                            + "Value\" value=\""
                            + Format.format(displayValue)
                            + "\"/>");
            buffer.append("<input type=\"hidden\" name=\"" + propertyName
                    + "\" value=\"" + Format.format(propertyValue) + "\"/>");
        }

        return buffer;
    }
}
