package easyJ.business.proxy;

import java.util.ArrayList;

import easyJ.business.facade.DictionaryFacade;
import easyJ.common.EasyJException;
import easyJ.system.data.Dictionary;

public class DictionaryProxy extends SingleDataProxy {
    private static SingleDataProxy sdp = SingleDataProxy.getInstance();

    public DictionaryProxy() {}

    public static StringBuffer getHtmlSelect(String propertyName,
            String propertyValueTable, Object propertyValue, Object displayValue)
            throws EasyJException {
        return DictionaryFacade.getHtmlSelect(propertyName, propertyValueTable,
                propertyValue, displayValue);
    }

    /**
     * 此方法通过relatedValue以及类型来获得对应的dictionary ID。
     * 
     * @param type
     * @param relatedValue
     * @return
     */
    public static Long getIdByRelatedValue(String type, Long relatedValue)
            throws EasyJException {
        Dictionary dict = new Dictionary();
        dict.setDicType(type);
        dict.setRelatedValue(relatedValue);
        ArrayList list = sdp.query(dict);
        if (list.size() == 0) {
            return null;
        } else {
            dict = (Dictionary) list.get(0);
            return dict.getDicValueId();
        }
    }
}
