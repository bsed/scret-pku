package easyJ.system.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.StringTokenizer;

import cn.edu.pku.dr.requirement.elicitation.system.Context;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;

public class SysUserCache implements java.io.Serializable {
    private Hashtable properties;// 用来缓存用户拥有权限的属性，类名+属性名作为键值。

    private Hashtable<String, ArrayList> displayProperties;// 用来存放用户在查询结果中显示权限字段。key是className，value是此class的字段

    private Hashtable editProperties;// 用来存放用户在编辑页面显示的字段。

    private Hashtable queryProperties;// 用来存放用户在查询条件中使用的字段。

    private SysUser user;

    private String ajax; // 用来判断用户是否选择使用ajax的方式来使用系统。

    private java.util.ArrayList modules;// 用来缓存用户拥有权限的模块

    private java.util.Hashtable pageFunctions;// 用来缓存用户拥有权限的功能，功能所属页面的名称作为键值,属于此功能页面的所有功能作为值。

    private java.util.ArrayList classes;// 用来缓存用户拥有权限的类

    private java.util.ArrayList dictionaries;// 用来缓存用户拥有权限的数据字典数据

    private java.util.ArrayList interests;// 用来缓存用户拥有权限的兴趣

    private Hashtable classValues;// 用来将具体的类对应到所缓存的数据。key是类名，值为各种缓冲的数据。用在树显示的时候，树显示的时候会显示多个class

    private Context context; // 用来保存用户进入某个项目之后的上下文

    public SysUserCache() {
        modules = new ArrayList();
        properties = new Hashtable();
        pageFunctions = new Hashtable();
        classes = new ArrayList();
        displayProperties = new Hashtable();
        editProperties = new Hashtable();
        queryProperties = new Hashtable();
        interests = new ArrayList();
        context = new Context();
    }

    /**
     * 此功能是从系统缓存SystemDataCache中得到用户所拥有的一些权限的数据，并且放入hashtable中。
     * 
     * @param user
     *                SysUser
     */
    public SysUserCache(SysUser user) throws EasyJException {
        modules = new ArrayList();
        properties = new Hashtable();
        pageFunctions = new Hashtable();
        classes = new ArrayList();
        displayProperties = new Hashtable();
        editProperties = new Hashtable();
        queryProperties = new Hashtable();
        interests = new ArrayList();
        classValues = new Hashtable();
        this.user = user;
        Long userId = user.getUserId();
        getPropertiesFromCache(userId, false);
        getPageFunctionsFromCache(false);
        getInterestsFromCache();
        getModulesFromCache();
        getDictionariesFromCache();
        classValues.put("easyJ.system.data.Module", modules);
        classValues.put("easyJ.system.data.Interest", interests);
        classValues.put("easyJ.system.data.UserPropertyRight", properties);
        classValues.put("easyJ.system.data.SystemClass", classes);
        classValues.put("easyJ.system.data.PageFunction", pageFunctions);
        classValues.put("easyJ.system.data.Dictionary", dictionaries);
    }

    /* 根据类名获得缓冲的有权限的数据 */
    public Object getCacheData(String className) {
        return classValues.get(className);
    }

    public void getPropertiesFromCache(Long userId, boolean refresh) throws EasyJException {
        // 用来记录已经访问过的property，因为要从用户的权限来得到，也要从用户组的权限来得到，所以就可能造成重复，这个是用来滤重用的。
        HashSet<String> visitedProperties = new HashSet<String>();
        ArrayList propertiesList = SystemDataCache.getProperties(refresh);
        
        if (refresh)  {
        	properties.clear();
        	displayProperties.clear();
        	editProperties.clear();
        	queryProperties.clear();
        }
        
        int propertySize = propertiesList.size();
        for (int i = 0; i < propertySize; i++) {
            UserPropertyRight property = (UserPropertyRight) propertiesList
                    .get(i);
            // 说明已经存在了
            if (userId.longValue() == property.getUserId().longValue()) {
                String className = property.getClassName();
                String propertyName = property.getPropertyName();
                if (!visitedProperties.add(className + propertyName))
                    continue;
                devideProperties(property);
                properties.put(className + propertyName, property);
            }
        }

        /* 将用户组所拥有的属性加进去 */
        ArrayList groupPropertiesList = SystemDataCache.getGroupProperties(refresh);
        int groupPropertySize = groupPropertiesList.size();
        for (int i = 0; i < groupPropertySize; i++) {
            GroupPropertyRight property = (GroupPropertyRight) groupPropertiesList
                    .get(i);
            if (user.getUserGroupIds().indexOf(
                    "," + property.getUserGroupId() + ",") >= 0) {
                UserPropertyRight userProperty = new UserPropertyRight();
                BeanUtil.transferObject(property, userProperty, true, false);
                userProperty.setUserId(user.getUserId());
                String className = property.getClassName();
                String propertyName = property.getPropertyName();
                // 说明已经存在了
                if (!visitedProperties.add(className + propertyName))
                    continue;
                devideProperties(userProperty);
                properties.put(className + propertyName, userProperty);
            }
        }
    }

    /**
     * 此方法将所有的property分为三类。
     * 
     * @param userProperty
     *                UserPropertyRight
     */
    private void devideProperties(UserPropertyRight userProperty) {
    	if (userProperty.getPropertyId().intValue() == 97 ||
    			userProperty.getPropertyId().intValue() == 98) {
    		int c = 0;
    	}
        if ("Y".equals(userProperty.getWhetherDisplay())) {
            ArrayList properties = (ArrayList) displayProperties
                    .get(userProperty.getClassName());
            if (properties == null) {
                properties = new ArrayList();
                properties.add(userProperty);
            } else {
                // 按照顺序进行排布
                int i = 0;
                int propertiesSize = properties.size();
                UserPropertyRight current = (UserPropertyRight) properties
                        .get(i);
                //i++;
                while (userProperty.getDisplaySequence() > current
                        .getDisplaySequence()) {
                	i++;
                    if ( i == propertiesSize )
                    	break;
                    current = (UserPropertyRight) properties.get(i);
                }
                properties.add(i, userProperty);
//                if (i > 0)
//                	if (i == propertiesSize)
//                		properties.add(i, userProperty);
//                	else
//                		properties.add(i, userProperty);
//                else
//                	properties.add(0, userProperty);
            }
            displayProperties.put(userProperty.getClassName(), properties);
        }
        if ("Y".equals(userProperty.getWhetherEdit())) {
            ArrayList properties = (ArrayList) editProperties.get(userProperty
                    .getClassName());
            if (properties == null) {
                properties = new ArrayList();
                properties.add(userProperty);
            } else {
                // 按照顺序进行排布
                int i = 0;
                int propertiesSize = properties.size();
                UserPropertyRight current = (UserPropertyRight) properties
                        .get(i);
                //i++;
                while (userProperty.getDisplaySequence() > current
                        .getDisplaySequence()) {
                	i++;
                    if ( i == propertiesSize )
                    	break;
                    current = (UserPropertyRight) properties.get(i);
                }
                properties.add(i, userProperty);
//                if (i > 0)
//                	if (i == propertiesSize)
//                		properties.add(i, userProperty);
//                	else
//                		properties.add(i - 1, userProperty);
//                else
//                	properties.add(0, userProperty);
            }
            editProperties.put(userProperty.getClassName(), properties);
        }

        if ("Y".equals(userProperty.getWhetherQuery())) {
            ArrayList properties = (ArrayList) queryProperties.get(userProperty
                    .getClassName());
            if (properties == null) {
                properties = new ArrayList();
                properties.add(userProperty);
            } else {
                // 按照顺序进行排布
                int i = 0;
                int propertiesSize = properties.size();
                UserPropertyRight current = (UserPropertyRight) properties
                        .get(i);
                //i++;
                while (userProperty.getDisplaySequence() > current
                        .getDisplaySequence()) {
                	i++;
                    if ( i == propertiesSize )
                    	break;
                    current = (UserPropertyRight) properties.get(i);
                }
                properties.add(i, userProperty);
//                if (i > 0)
//                	if (i == propertiesSize)
//                		properties.add(i, userProperty);
//                	else
//                		properties.add(i - 1, userProperty);
//                else
//                    properties.add(0, userProperty);
            }
            queryProperties.put(userProperty.getClassName(), properties);
        }
    }

    /* 获得用户所拥有权限的功能 */
    public void getPageFunctionsFromCache(boolean refresh) throws EasyJException {
        Long userId = user.getUserId();
        String userGroups = user.getUserGroupIds();
        ArrayList functionsList = SystemDataCache.getPageFunctionsList(refresh);
        
        if (refresh) {
        	pageFunctions.clear();
        }
        
        int functionSize = functionsList.size();
        for (int i = 0; i < functionSize; i++) {
            PageFunction function = (PageFunction) functionsList.get(i);
            String functionUsers = function.getUsers(); // 对此功能拥有权限的用户
            String functionUserGroups = function.getUserGroups();// 对此功能拥有权限的用户组
            /* 看看用户ID是否在function的users里面，如果在则加入到list当中去 */
            if (!GenericValidator.isBlankOrNull(functionUsers)
                    && functionUsers.indexOf("," + userId + ",") >= 0) {
                /* 得到此function所属的页面，然后根据所属的页面从hashtable中取得属于此页面的功能列表functions */
                String functionPage = function.getFunctionPage();
                ArrayList functions = (ArrayList) pageFunctions
                        .get(functionPage);
                if (functions == null)
                    functions = new ArrayList();
                /* 如果没有包含有此function则加进去 */
                if (!functions.contains(function))
                    functions.add(function);
                pageFunctions.put(functionPage, functions);
            }
            /*
             * 遍历user所属的用户组，将用户组拥有的function加入到对应的功能列表。因为用户组的格式是,id,
             * 所以如果st有内容则第一个token必然是一个空字符串，需要注意的是空字符串是不存在token当中的，所以第一个还是id
             */
            if (!GenericValidator.isBlankOrNull(functionUserGroups)
                    && !GenericValidator.isBlankOrNull(userGroups)) {
                StringTokenizer st = new StringTokenizer(userGroups, ",");
                while (st.hasMoreTokens()) {
                    String userGroupId = st.nextToken();
                    if (functionUserGroups.indexOf("," + userGroupId + ",") >= 0) {
                        String functionPage = function.getFunctionPage();
                        ArrayList functions = (ArrayList) pageFunctions
                                .get(functionPage);
                        if (functions == null)
                            functions = new ArrayList();
                        /* 如果没有包含有此function则加进去 */
                        if (!functions.contains(function))
                            functions.add(function);
                        pageFunctions.put(functionPage, functions);
                    }
                }
            }
        }
    }

    public void getModulesFromCache() throws EasyJException {
        Long userId = user.getUserId();
        String userGroups = user.getUserGroupIds();
        ArrayList modulesList = SystemDataCache.getModules();
        int moduleSize = modulesList.size();
        for (int i = 0; i < moduleSize; i++) {
            Module module = (Module) modulesList.get(i);
            String moduleUsers = module.getUsers(); // 对此功能拥有权限的用户
            String moduleUserGroups = module.getUserGroups();// 对此功能拥有权限的用户组

            /* 如果对此module拥有权限但是没有包含有此modules则加进去 */
            if (!GenericValidator.isBlankOrNull(moduleUsers)
                    && moduleUsers.indexOf("," + userId + ",") >= 0
                    && !modules.contains(module))
                modules.add(module);
            /*
             * 遍历user所属的用户组，将用户组拥有的modules加入到对应的modules集合当中去。因为用户组的格式是,id,
             * 所以如果st有内容则第一个token必然是一个空字符串
             */
            if (!GenericValidator.isBlankOrNull(userGroups)
                    && !GenericValidator.isBlankOrNull(moduleUserGroups)) {
                StringTokenizer st = new StringTokenizer(userGroups, ",");
                while (st.hasMoreTokens()) {
                    String userGroupId = st.nextToken();
                    /* 如果对此module拥有权限但是没有包含有此modules则加进去 */
                    if (moduleUserGroups.indexOf("," + userGroupId + ",") >= 0
                            && !modules.contains(module))
                        modules.add(module);
                }
            }
        }

    }

    /* 暂时没有对数据字典授权，所以返回所有的数据。 */
    private void getDictionariesFromCache() throws EasyJException {
        dictionaries = SystemDataCache.getDictinaries();
    }

    private void getInterestsFromCache() throws EasyJException {
        Long userId = user.getUserId();
        String userGroups = user.getUserGroupIds();
        ArrayList interestsList = SystemDataCache.getInterests();
        int interestsSize = interestsList.size();
        for (int i = 0; i < interestsSize; i++) {
            Interest interest = (Interest) interestsList.get(i);
            String interestUsers = interest.getUsers(); // 对此功能拥有权限的用户
            String interestUserGroups = interest.getUserGroups();// 对此功能拥有权限的用户组

            /* 如果对此module拥有权限但是没有包含有此modules则加进去 */
            if (!GenericValidator.isBlankOrNull(interestUsers)
                    && interestUsers.indexOf("," + userId + ",") >= 0
                    && !interests.contains(interest))
                interests.add(interest);
            /*
             * 遍历user所属的用户组，将用户组拥有的modules加入到对应的modules集合当中去。因为用户组的格式是,id,
             * 所以如果st有内容则第一个token必然是一个空字符串
             */
            if (!GenericValidator.isBlankOrNull(userGroups)) {
                StringTokenizer st = new StringTokenizer(userGroups, ",");
                while (st.hasMoreTokens()) {
                    String userGroupId = st.nextToken();
                    /* 如果对此module拥有权限但是没有包含有此modules则加进去 */
                    if (interestUserGroups.indexOf("," + userGroupId + ",") >= 0
                            && !interests.contains(interest))
                        interests.add(interest);
                }
            }
        }
    }

    public Hashtable getProperties() {
        return properties;
    }

    public java.util.ArrayList getModules() {
        return modules;
    }

    public java.util.ArrayList getInterests() {
        return interests;
    }

    public java.util.ArrayList getClasses() {
        return classes;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public java.util.Hashtable getPageFunctions() {
        return pageFunctions;
    }

    public void setAjax(String ajax) {
        this.ajax = ajax;
    }

    public String getAjax() {
        return ajax;
    }

    public java.util.ArrayList getEditProperties(String className) {
        return (ArrayList) editProperties.get(className);
    }

    public java.util.ArrayList getQueryProperties(String className) {
        return (ArrayList) queryProperties.get(className);
    }

    public java.util.ArrayList getDisplayProperties(String className) {
        return (ArrayList) displayProperties.get(className);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
