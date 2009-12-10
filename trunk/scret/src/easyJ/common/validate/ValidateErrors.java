package easyJ.common.validate;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ValidateErrors implements java.io.Serializable {
    protected HashMap messages = new HashMap();

    private int size = 0;

    public void addErrorMsg(String property, String errorMsg) {
        List list = (List) messages.get(property);

        if (list == null) {
            list = new ArrayList();
            list.add(errorMsg);
        } else {
            list.add(errorMsg);
        }
        messages.put(property, list);
        size++;
    }

    public StringBuffer getErrorMsg(String property) {
        List list = (List) messages.get(property);
        if (list == null)
            return null;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i) + ";");
        }
        return buffer;
    }

    public int getSize() {
        return size;
    }
}
