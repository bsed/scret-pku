package easyJ.http.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.edu.pku.dr.requirement.elicitation.system.Context;
import cn.edu.pku.dr.requirement.elicitation.system.DataContextFilter;
import easyJ.common.BeanUtil;
import easyJ.common.Const;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.http.Globals;
import easyJ.http.tags.TreeConditionTag.Condition;
import easyJ.system.data.SysUserCache;
import easyJ.system.service.Tree;
import easyJ.system.service.TreeService;
import easyJ.system.service.TreeServiceFactory;

public class TreeTag extends TagSupport {
    private String className; // 指明要对哪个类建立树结构

    private String condition; // 用来指定在建立树的时候的条件，也就是说支持对部分表内容建立树。

    private String selectMode; // 选择模式。包括只能选择叶节点，还是所有节点都可以选择，具体参见Const类。

    private String functionUrl; // 如果点击每个树节点所执行url都一样，则在这里指定。否则在数据库中设置，不在这里指定

    private List<Condition> conditions = new ArrayList<Condition>();

    public TreeTag() {}

    public int doStartTag() throws JspTagException {
        conditions.clear();
        return this.EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            /* 当rootId不为空的时候，只显示rootId的子树内容，用在SingleDataEdit.jsp中选择数据时用 */
            String rootId = request.getParameter("rootId");
            int selectModeInt = Const.TREE_SELECT_SPREAD;
            TreeService ts = TreeServiceFactory.getTreeService();
            /* 得到用户拥有权限的数据，如果这些数据不为空则用这些数据构造树结构，否则用整个className对应的表来构造树结构 */
            SysUserCache userCache = (SysUserCache) request.getSession()
                    .getAttribute(Globals.SYS_USER_CACHE);
            ArrayList list = (ArrayList) userCache.getCacheData(className);
            list = filterTreeList(list);
            
            DataContextFilter filter= DataContextFilter.getInstance();
            filter.setContext(userCache.getContext());
            list=filter.filter(list);
            
            Tree tree = ts.getTree(className, list);
            StringBuffer buffer = new StringBuffer();
            if (!GenericValidator.isBlankOrNull(selectMode))
                selectModeInt = Integer.parseInt(selectMode);
            if (rootId == null)
                ts.generateWholeTree(tree, buffer, selectModeInt, functionUrl);
            else {
                Object root = BeanUtil.getEmptyObject(className);
                Class clazz = BeanUtil.getClass(className);
                String primaryKeyName = BeanUtil.getPrimaryKeyName(clazz);
                BeanUtil.setFieldValue(root, primaryKeyName, new Long(rootId));
                Object emptyNode = BeanUtil.getEmptyObject(root.getClass());
                BeanUtil.setFieldValue(emptyNode, tree.getDisplayProperty(),
                        "空");
                if (selectModeInt == Const.TREE_SELECT_LEAF_RETURN
                        || selectModeInt == Const.TREE_SELECT_NODE_RETURN) {
                    ts.generateSingleNode(emptyNode, tree, 2, 1, buffer,
                            selectModeInt, functionUrl);
                    buffer.append("</div>\n");
                }

                ts.generateWholeSubTree(root, tree, 1, 1, buffer,
                        selectModeInt, functionUrl);
            }
            out.println(buffer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (easyJ.common.EasyJException ee) {
            ee.printStackTrace();
        }
        return (EVAL_PAGE);
    }

    /**
     * 这里是从内存中按照条件过滤，如果条件过多，而且数据量较大的时候，可以选择直接从数据库当中查询。
     * 现在是因为数据量较少，而且条件也少，所以使用这种方法。
     * 
     * @param list
     * @return
     * @throws EasyJException
     */
    private ArrayList filterTreeList(List list) throws EasyJException {
        ArrayList result = new ArrayList();
        for (Object value: list) {
            boolean qualified = true;
            for (Condition condition: conditions) {
                String property = condition.getProperty();
                Class propertyClass = BeanUtil.getFieldType(value.getClass(),
                        property);
                // 如果值当中没有包含用户设置的条件则说明不满足，处理字符串类型的，不需要完全相等。
                if (propertyClass.equals(String.class)) {
                    String propertyValue = (String) BeanUtil.getFieldValue(
                            value, property);
                    if (propertyValue.indexOf(condition.getValue()) < 0) {
                        qualified = false;
                        break;
                    }
                } else {
                    Object propertyValue = BeanUtil.getFieldValue(value,
                            property);
                    if (!propertyValue.equals(condition.getValue())) {
                        qualified = false;
                        break;
                    }
                }
            }
            if (qualified)
                result.add(value);
        }
        return result;
    }

    public void addCondisiton(Condition arg) {
        conditions.add(arg);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(String selectMode) {
        this.selectMode = selectMode;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

}
