package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import easyJ.http.Globals;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import easyJ.common.BeanUtil;
import java.util.ArrayList;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.database.dao.*;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.common.BeanUtil;
import easyJ.database.dao.command.SelectCommand;
import cn.edu.pku.dr.requirement.elicitation.data.*;
import easyJ.database.session.SessionFactory;
import easyJ.database.session.Session;
import java.io.*;

public class DescriptionVersionAction extends
        easyJ.http.servlet.SingleDataAction {
    public DescriptionVersionAction() {}

    public void process(HttpServletRequest request, HttpServletResponse response)
            throws EasyJException {
        super.process(request, response, application);
    }

    public boolean encapsulateObject() throws EasyJException {
        super.encapsulateObject();
        return true;
    }

    public void edit() throws EasyJException {
        super.edit();
    }

    public void newObject() throws EasyJException {
        super.newObject();
    }

    public void update() throws EasyJException {
        super.update();
    }

    public void query() throws EasyJException {
        super.query();
        returnPath = "/WEB-INF/template/AjaxNewWindowDescription.jsp";
    }

    public void showDescriptionHistory() throws EasyJException {
        DescriptionVersion descriptionversion = new DescriptionVersion();
        // 取这个用户对应的所有的Description
        Long creatorId = new Long(request.getParameter("CreatorId"));

        Object object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion");
        Class clazz = object.getClass();
        SelectCommand scmdDescriptionVersion = DAOFactory
                .getSelectCommand(clazz);
        Filter filter = DAOFactory.getFilter("creatorId", SQLOperator.EQUAL,
                creatorId);
        scmdDescriptionVersion.setFilter(filter);
        scmdDescriptionVersion.addOrderRule(new OrderRule(
                "descriptionVersionId", OrderDirection.DESC));
        Session session = SessionFactory.openSession();
        ArrayList tempdescriptionVersions = session
                .query(scmdDescriptionVersion);
        ArrayList descriptionVersions = new ArrayList();
        if (tempdescriptionVersions.size() > 8) {
            for (int size = 0; size < 8; size++) {
                descriptionVersions
                        .add(size, tempdescriptionVersions.get(size));
            }
        } else {
            descriptionVersions = tempdescriptionVersions;
        }
        String content = "";
        // 取用户名
        object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
        clazz = object.getClass();
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        Filter filter1 = DAOFactory.getFilter("roleId", SQLOperator.EQUAL,
                creatorId);
        scmd.setFilter(filter1);
        ArrayList creators = session.query(scmd);
        String creatorName = "";
        if (creators.size() > 0) {
            Role creator = (Role) creators.get(0);
            creatorName = creator.getRoleName();
        }

        descriptionversion = (DescriptionVersion) descriptionVersions.get(0);
        content = "<div id=\"1\" class=\"panel-up name\" >&nbsp;&nbsp;&nbsp;场景历史记录：</div>";
        String temp = "";
        for (int i = 0; i < descriptionVersions.size(); i++) {
            descriptionversion = (DescriptionVersion) descriptionVersions
                    .get(i);
            temp = temp
                    + "<div class=\"first\" ></div><div class=\"target-closed\"><span class =\"changeDomain\">&nbsp;&nbsp;&nbsp;&nbsp;版本"
                    + descriptionversion.getDescriptionVersionId() + "</span>";
            temp = temp + "<span class=\"name\">&nbsp;修改时间:</span>";
            temp = temp + "<span class=\"time\">&nbsp;"
                    + descriptionversion.getUpdateTime().toGMTString()
                    + "</span>";
            temp = temp + "<span>修改</span></div>";
            temp = temp
                    + "<div class=\"target-closed\"><table class=\"changeborder\"><tr class=\"content\"><td>"
                    + descriptionversion.getDescriptionVersionContent()
                    + "</td></table></div>";
        }
        content = content + temp;
        // content = content+"<div class=\"jqHandle jqResize\"></div>";
        System.out.println(content);
        try {
            response.getWriter().println(content);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

}
