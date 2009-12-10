package cn.edu.pku.dr.requirement.elicitation.system;

import java.util.ArrayList;

import easyJ.common.BeanUtil;
import easyJ.common.Const;
import easyJ.common.EasyJException;

/**
 * 这个类用来对数据进行context下的过滤。 例如在project context下面，需要对owner, group, other进行过滤
 * 
 * @author liuf
 */
public class DataContextFilter {
    private static Context context;

    private DataContextFilter() {

    }

    public static DataContextFilter instance = new DataContextFilter();

    public static DataContextFilter getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static ArrayList filter(ArrayList list) throws EasyJException {
        ArrayList result = new ArrayList();
        String contextTypeProperty = "";
        int contextRole = DictionaryConstant.OTHER;
        if (context != null)
            contextRole = context.getProjectRole();
        if (list.size() > 0) {
            try {
                contextTypeProperty = (String) BeanUtil.getPubStaticFieldValue(
                        list.get(0).getClass(), Const.CONTEXT_ROLE_PROPERTY);
            } catch (EasyJException e) {
                // 说明没有相应的过滤属性，直接返回
                return list;
            }
        }
        for (Object obj: list) {
            int contextRoleType = Integer.parseInt(BeanUtil.getFieldValue(obj,
                    contextTypeProperty).toString());
            /* 如果用户是owner角色，而contextRoleType要求是other，则可以返回，这里暂时用的是<=，将来可以具体定义是等于还是其他比较运算符 */
            if (contextRole <= contextRoleType)
                result.add(obj);
        }
        return result;
    }
}
