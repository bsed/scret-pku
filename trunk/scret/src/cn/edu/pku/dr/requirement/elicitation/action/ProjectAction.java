package cn.edu.pku.dr.requirement.elicitation.action;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.pku.dr.requirement.elicitation.data.Project;
import cn.edu.pku.dr.requirement.elicitation.data.Role;
import cn.edu.pku.dr.requirement.elicitation.data.UserProjectRelation;
import cn.edu.pku.dr.requirement.elicitation.system.Context;
import cn.edu.pku.dr.requirement.elicitation.system.DictionaryConstant;
import easyJ.business.proxy.DictionaryProxy;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderDirection;
import easyJ.database.dao.OrderRule;
import easyJ.http.Globals;
import easyJ.http.servlet.SingleDataAction;
import easyJ.system.data.SysUserCache;
import easyJ.system.service.HtmlClientComponentService;

public class ProjectAction extends SingleDataAction {
    public void view() throws EasyJException {
        Project project = (Project) object;
        project = (Project) dp.get(project);
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Context context = new Context();
        context.setProjectId(project.getProjectId());
        context.setProjectName(project.getProjectName());
        // 下面的程序用来确定此用户在此project中的角色

        // 1.首先确定是不是owner。
        if (project.getCreatorId().longValue() == user.getUserId().longValue()) {
            context.setProjectRole(DictionaryConstant.OWNER);
        } else {
            // 2.判断用户是否是参与了当前项目
            UserProjectRelation relation = new UserProjectRelation();
            relation.setUserId(user.getUserId());
            relation.setProjectId(project.getProjectId());
            ArrayList relationList = dp.query(relation);
            // 如果不存在数据，说明没有加入；否则根据状态来确定。
            if (relationList.size() == 0) {
                context.setProjectRole(DictionaryConstant.OTHER);
            } else {
                relation = (UserProjectRelation) relationList.get(0);
                int applyState = relation.getUserProjectStateRelatedValue()
                        .intValue();
                // 此状态请参见表dictionary中的PROJECT_APPLY_STATE 对应的related value部分。
                switch (applyState) {
                    case 2:
                        context.setProjectRole(DictionaryConstant.GROUP);
                        break;
                    default:
                        context.setProjectRole(DictionaryConstant.OTHER);
                        break;
                }
            }
        }

        userCache.setContext(context);

        HashMap<String,String> roles = context.getRoles();
        roles.clear();
        
        //将此项目所拥有的角色加入环境当中
        Role role = new Role();
        role.setProjectId(project.getProjectId());
        ArrayList list = dp.query(role);
        int size = list.size();
        for(int i=0; i<size; i++) {
            role = (Role)list.get(i);
            roles.put(role.getRoleId().toString(),role.getRoleName());
        }
        
        request.getSession().setAttribute(Globals.SYS_USER_CACHE, userCache);
        returnPath = "/WEB-INF/AjaxMain.jsp";
    }

    /*
     * 下面的几个方法都是对project的操作，所以每个操作之前都需要把context清空
     */

    public void apply() throws EasyJException {
        // 说明是guest
        if (userId.intValue() == -1) {
            returnMessage = "请登录";
            return;
        }

        dp.setContext(null);
        Project project = (Project) object;
        UserProjectRelation relation = new UserProjectRelation();
        relation.setProjectId(project.getProjectId());
        relation.setUserId(userId);
        ArrayList relations = dp.query(relation);
        Long userProjectStateId = DictionaryProxy.getIdByRelatedValue(
                "PROJECT_APPLY_STATE", new Long(DictionaryConstant.APPLYING));

        // 说明数据不存在。
        if (relations.size() == 0) {
            relation.setUserProjectState(userProjectStateId);
            dp.create(relation);
        } else {
            // 数据存在则只是改变状态，被拒绝或者删除之后，数据是存在的，只是状态不同
            relation = (UserProjectRelation) relations.get(0);
            relation.setUserProjectState(userProjectStateId);
            dp.update(relation);
        }
    }

    private void updateApplyState(int state) throws EasyJException {
        dp.setContext(null);
        UserProjectRelation relation = (UserProjectRelation) object;
        relation = (UserProjectRelation) dp.get(relation);
        Long userProjectState = DictionaryProxy.getIdByRelatedValue(
                "PROJECT_APPLY_STATE", new Long(state));
        relation.setUserProjectState(userProjectState);
        dp.update(relation);
    }

    public void confirmApply() throws EasyJException {
        updateApplyState(DictionaryConstant.ACCEPTED);
    }

    public void rejectApply() throws EasyJException {
        updateApplyState(DictionaryConstant.REJECTED);
    }

    public void cancel() throws EasyJException {
        updateApplyState(DictionaryConstant.CANCELED);
    }

    public void update() throws EasyJException {
        dp.setContext(null);
        super.update();
    }

    public void edit() throws EasyJException {
        dp.setContext(null);
        super.edit();
    }

    public void delete() throws EasyJException {
        dp.setContext(null);
        super.delete();
    }

    public void newObject() throws EasyJException {
        dp.setContext(null);
        super.newObject();
    }

    public void query() throws EasyJException {
        dp.setContext(null);
        super.query();
    }

    public void queryByOwner() throws EasyJException {
        dp.setContext(null);
        Project project = new Project();
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        project.setCreatorId(userCache.getUser().getUserId());
        ArrayList list = dp.query(project);
        StringBuffer result = HtmlClientComponentService.getObjectListHtml(
                list, this.getClass().getName(), "domainName", "projectName",
                "", 4);
        if (result != null) {
            returnMessage = result.toString();
        } else {
            returnMessage = "<div>您没有创建自己的项目</div>";
        }
    }

    public void mainPage() throws EasyJException {
        dp.setContext(null);
        Project project = new Project();
        OrderRule rule = new OrderRule();
        rule.setOrderColumn("domainName");
        rule.setOrderDirection(OrderDirection.ASC);
        OrderRule orderRules[] = {
            rule
        };
        ArrayList list = dp.query(project, orderRules);
        returnMessage = HtmlClientComponentService.getObjectListHtml(list,
                this.getClass().getName(), "domainName", "projectName", "", 4)
                .toString();
    }
}
