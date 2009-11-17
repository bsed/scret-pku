package easyJ.http.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import easyJ.common.EasyJException;
import easyJ.http.Globals;
import easyJ.common.BeanUtil;
import easyJ.common.validate.ValidateErrors;
import easyJ.business.proxy.CompositeDataProxy;
import easyJ.common.validate.GenericValidator;
import javax.servlet.ServletContext;

public class CompositeDataAction extends ServletAction {
    public CompositeDataAction() {}

    public void process(HttpServletRequest request,
            HttpServletResponse response, ServletContext application)
            throws EasyJException {
        className = (String) request.getParameter(Globals.CLASS_NAME);
        // after get the action, we shall invoke the method in the proxy,
        // actually
        // one class shall have a proxy , while if this the action on this class
        // is just common
        // action, then this class don't need to have its own proxy, and we can
        // use template Proxy
        // instead of using the class's specific proxy
        try {
            dp = (CompositeDataProxy) BeanUtil.getEmptyObject(className
                    + "Proxy");
        } catch (Exception e) {
            dp = CompositeDataProxy.getInstance();
        }
        super.process(request, response, application);
        return;
    }

    public void newObject() throws EasyJException {
        super.newObject();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.COMPOSITE_DATA_SAVE_RETURN_PARTH;
    }

    public boolean encapsulateObject() throws EasyJException {
        errors = new ValidateErrors();
        // 从客户端传来的处理数据
        object = BeanUtil.getCompositObject(className, properties, errors);

        if (errors.getSize() != 0)
            if (Globals.UPDATE_ACTION.equals(action)
                    || Globals.NEW_ACTION.equals(action)) {
                request.setAttribute(Globals.OBJECT, object);
                request.setAttribute(Globals.VALIDATE_ERRORS, errors);
                return false;
            }
        return true;
    }

    public void update() throws EasyJException {
        // 先从数据库里面得到，然后再将客户端的数据设入对象，再进行更新。
        // 因为可能因为权限的原因，用户并不能看到全部的数据，所以在用户提交更新的时候，那些看不到的数据
        // 并不能够提交到服务器来，而如果这些数据没有值的话，会造成更新失误，所以必须要先从数据库中取得数据
        // 因为是更新，所以从数据库中取数据的时候应该只根据主键来取。这样处理效率有点低。
        Object o = BeanUtil.getEmptyObject(className);
        Object primaryKey = BeanUtil.getPrimaryKeyValue(object);
        String primaryKeyName = BeanUtil.getPrimaryKeyName(o.getClass());
        BeanUtil.setFieldValue(o, primaryKeyName, primaryKey);

        object = dp.get(o);
        object = BeanUtil.getCompositObject(object, properties, errors);
        // 因为子表也有useState字段，所以如果子表的useState字段为N，则主表也会得到N，但是这里只是对子表的更新，所以无论怎样需要将useState改为Y
        BeanUtil.setFieldValue(object, "useState", "Y");
        dp.update(object);
        request.setAttribute(Globals.OBJECT, object);
        returnMessage = "保存成功";
        // if(GenericValidator.isBlankOrNull(returnPath))
        // returnPath=Globals.COMPOSITE_DATA_SAVE_RETURN_PARTH;
    }

    public void edit() throws EasyJException {
        super.edit();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.COMPOSITE_DATA_EDIT_RETURN_PARTH;
    }

    public void delete() throws EasyJException {
        super.delete();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.COMPOSITE_DATA_DELETE_RETURN_PARTH;
    }

    public void query() throws EasyJException {
        super.query();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.COMPOSITE_DATA_QUERY_RETURN_PARTH;
    }

}
