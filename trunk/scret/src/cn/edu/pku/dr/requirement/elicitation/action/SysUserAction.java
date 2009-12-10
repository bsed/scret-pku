package cn.edu.pku.dr.requirement.elicitation.action;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.pku.dr.requirement.elicitation.system.Context;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.database.dao.Page;
import easyJ.http.Globals;
import easyJ.http.servlet.SingleDataAction;
import easyJ.system.data.SysUser;

public class SysUserAction extends SingleDataAction{

    public void query() throws EasyJException {
        super.query();
        Page page = (Page)request.getAttribute(Globals.PAGE);
        ArrayList list = page.getPageData();
        int size =  list.size();
        for(int i=0; i<size; i++) {
            SysUser user = (SysUser)list.get(i);
            filterRoles(user,context, true);
        }
    }
    
    public void update() throws EasyJException {
        //首先得到数据库中用户所有的拥有的角色。
        Object o = BeanUtil.getEmptyObject(className);
        Object primaryKey = BeanUtil.getPrimaryKeyValue(object);
        String primaryKeyName = BeanUtil.getPrimaryKeyName(o.getClass());
        BeanUtil.setFieldValue(o, primaryKeyName, primaryKey);

        SysUser user = (SysUser)dp.get(o);
        
        //将此项目中的角色去掉，然后加上从客户端传来的角色。
        filterRoles(user,context, false);       
        
        String userIds = user.getRoleIds();
        String userNames = user.getRoleNames();
        
        SysUser newUser = (SysUser)BeanUtil.getObject(user, properties, errors);
        
        String newIds = newUser.getRoleIds();
        String newNames = newUser.getRoleNames();
        
        if(GenericValidator.isBlankOrNull(newIds)) {
            newUser.setRoleIds(userIds);
            newUser.setRoleNames(userNames);
        } else {
            newUser.setRoleIds(newIds.substring(0,newIds.length()-1)+userIds);
            if(GenericValidator.isBlankOrNull(userNames))
                newUser.setRoleNames(newNames);
            else
                newUser.setRoleNames(newNames + "、"+userNames);
        }
        dp.update(newUser);
        request.setAttribute(Globals.OBJECT, newUser);
        returnMessage = "保存成功<message>";
    }
    
    /**
     * 一个用户可以参与多个项目，可以拥有多个项目的角色，此方法是在一个项目中，过滤出用户属于此项目的角色。
     * @param user      需要被过滤的数据
     * @param context   用来过滤的上下文环境，其中含有此project所包含的所有的角色的编号
     * @param type      标明是需要包含，还是需要排除，true代表需要包括，false代表需要排除
     * @throws EasyJException
     */
    public static void  filterRoles(SysUser user, Context context, boolean type) throws EasyJException {
        
        if(user.getRoleIds() == null)
            return;
        
        String[] ids =  user.getRoleIds().split(",");
        String[] names = user.getRoleNames().split("、");
        
        StringBuffer idBf = new StringBuffer(",");
        StringBuffer nameBf = new StringBuffer("");
        
        if (context == null)
        	return;
        
        HashMap<String,String> roleIds = context.getRoles();
        
        boolean hasRoles = false;
        
        //因为id最前面有个逗号，而names没有，所以在取的时候需要取i-1。
        
        for(int i=1; i<ids.length; i++) {
            if(type?roleIds.containsKey(ids[i]):!roleIds.containsKey(ids[i])) {
                idBf.append(ids[i]);
                idBf.append(",");
                nameBf.append(names[i-1]);
                nameBf.append("、");
                hasRoles = true;
            }
        }
        //如果拥有此project的角色，则设置，否则置空
        if (hasRoles) {
            user.setRoleIds(idBf.toString());
            user.setRoleNames(nameBf.substring(0,nameBf.length()-1));
        } else {
            user.setRoleIds("");
            user.setRoleNames("");
        }
    }
}
