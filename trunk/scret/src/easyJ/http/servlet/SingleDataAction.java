package easyJ.http.servlet;

import java.sql.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.common.validate.ValidateErrors;
import easyJ.database.dao.OrderDirection;
import easyJ.http.Globals;
import easyJ.system.data.Property;
import easyJ.system.data.SysUserCache;
import easyJ.system.data.SystemDataCache;
import easyJ.system.data.UserPropertyRight;
import easyJ.system.service.HtmlClientComponentService;

public class SingleDataAction extends ServletAction {

    public SingleDataAction() {}

    public void process(HttpServletRequest request,
            HttpServletResponse response, ServletContext application)
            throws EasyJException {
        className = (String) request.getParameter(Globals.CLASS_NAME);
        try {
            dp = (SingleDataProxy) BeanUtil.getEmptyObject(className + "Proxy");
        } catch (Exception e) {
            dp = SingleDataProxy.getInstance();
        }
        super.process(request, response, application);
    }

    public boolean encapsulateObject() throws EasyJException {
        errors = new ValidateErrors();
        // 从客户端传来的处理数据
        object = BeanUtil.getObject(className, properties, errors);

        // 只有是在用户进行更新操作的时候才进行校验，在进行查询操作的时候，可以不进行校验
        if (errors.getSize() != 0) {
            if (Globals.UPDATE_ACTION.equals(action)
                    || Globals.NEW_ACTION.equals(action)) {
                request.setAttribute(Globals.OBJECT, object);
                request.setAttribute(Globals.VALIDATE_ERRORS, errors);
                return false;
            }
        }
        return true;
    }

    public void multiSelect() throws EasyJException {
        String sourceClass = request.getParameter("sourceClass");
        String destClass = request.getParameter("destClass");
        Object sourceObject = BeanUtil.getEmptyObject(sourceClass);
        Object destObject = BeanUtil.getEmptyObject(destClass);
        // 客户端传数据的时候需要注意，只传送需要的数据，也就是可以在data中传，不要通过form来传。
        BeanUtil.getObject(sourceObject, properties, errors);
        BeanUtil.getObject(destObject, properties, errors);
        String property = request.getParameter("property");
        returnMessage = HtmlClientComponentService.getMultiSelect(sourceObject,
                destObject, property, null, null, null).toString();
    }

    /**
     * 将用户选择的数据加入到数据库。参见MultiSelect.js文件中的confirm方法。
     * 
     * @throws EasyJException
     */
    public void multiSelectConfirm() throws EasyJException {
        String selectedValue = request.getParameter("selectedValue");
        // 得到需要将所选择的值放入到哪个字段中
        String property = request.getParameter("property");
        // 首先要删除之前的数据，在场景的角色例子中就是需要scenarioId,以及roleType等信息，在传过来的数据中已经有了。
        // 一般情况下是一定要有条件数据的。
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        sdp.deleteBatch(object);

        // 将数据插入，因为最后有一个‘,’，所以最后一个不要。
        String[] values = selectedValue.split(",");
        for (int i = 0; i < values.length; i++) {
            // 因为是Id类型的，所以需要转换为Long类型。
            BeanUtil.setFieldValue(object, property, new Long(values[i]));
            BeanUtil.setFieldValue(object, "sequence", new Integer(i));
            sdp.create(object);
        }
    }

    // 用来显示调整字段的界面
    public void adjustProperty() throws EasyJException {
        UserPropertyRight userPropertyRight = new UserPropertyRight();
        userPropertyRight.setUserId(userId);
        userPropertyRight.setClassName(className);
        Property property = new Property();
        BeanUtil.transferObject(userPropertyRight, property, true, false);
        // 用来指示当前显示的是查询的字段，还是显示的字段，还是编辑的字段。
        String propertyType = request.getParameter("propertyType");
        if (GenericValidator.isBlankOrNull(propertyType))
            propertyType = "whetherDisplay";
        BeanUtil.setFieldValue(userPropertyRight, propertyType, "Y");

        String orderByProperty = null;
        if ("whetherDisplay".equals(propertyType)) {
            orderByProperty = "displaySequence";
        }
        if ("whetherEdit".equals(propertyType)) {
            orderByProperty = "editSequence";
        }
        if ("whetherQuery".equals(propertyType)) {
            orderByProperty = "querySequence";
        }

        returnMessage = HtmlClientComponentService.getPropertyMultiSelect(
                property, userPropertyRight, "propertyId", propertyType,
                orderByProperty, OrderDirection.ASC);
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
        object = BeanUtil.getObject(object, properties, errors);
        try {
        	BeanUtil.setFieldValue(object, "updateTime", new Date(System.currentTimeMillis()));
        } catch (Exception e) {
        	
        }
        dp.update(object);
        request.setAttribute(Globals.OBJECT, object);
        returnMessage = "保存成功";
        // if (GenericValidator.isBlankOrNull(returnPath))
        // returnPath=Globals.SINGLE_DATA_SAVE_RETURN_PARTH;
    }

    public void edit() throws EasyJException {
        super.edit();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.SINGLE_DATA_EDIT_RETURN_PARTH;
    }

    public void delete() throws EasyJException {
        super.delete();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.SINGLE_DATA_DELETE_RETURN_PARTH;
    }

    public void newObject() throws EasyJException {
        super.newObject();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.SINGLE_DATA_SAVE_RETURN_PARTH;
    }

    public void query() throws EasyJException {
        super.query();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.SINGLE_DATA_QUERY_RETURN_PARTH;
    }
    
    public void queryEdit() throws EasyJException {
        super.query();
        if (GenericValidator.isBlankOrNull(returnPath))
            returnPath = Globals.SINGLE_DATA_QUERY_EDIT_RETURN_PARTH;
    }
    /**
     * 此方法用来刷新缓存的数据，比如当对字段进行了修改或新增的时候，
     * 对功能进行了修改或新增的时候
     * @throws EasyJException
     */
    public void refresh() throws EasyJException {
    	SystemDataCache.getPropertyHT(true);
    	userCache.getPropertiesFromCache(userId, true);
    	userCache.getPageFunctionsFromCache(true);
    }
}
