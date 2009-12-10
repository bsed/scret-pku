package test;

import easyJ.database.dao.command.*;
import easyJ.database.dao.*;
import easyJ.database.session.*;
import java.util.List;

public class TestCommand {
    public TestCommand() {}

    public static void main(String[] args) {
        try {
            Class clazz = Class
                    .forName("cn.edu.pku.dr.requirement.elicitation.data.Data");
            SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
            String[] ids = {
                "1", "2"
            };
            Filter filter = DAOFactory.getFilter("dataId", SQLOperator.IN, ids);
            scmd.setFilter(filter);
            Session session = SessionFactory.openSession();
            List list = session.query(scmd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
