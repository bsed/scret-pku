package easyJ.business.facade;

import java.util.ArrayList;
import easyJ.database.session.Session;
import easyJ.database.session.SessionFactory;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.DAOFactory;
import easyJ.common.EasyJException;
import easyJ.system.data.UserPropertyRight;

public class UserRightFacade {
    public UserRightFacade() {}

    /**
     * 也就是访问用户对字段权限的表，得到拥有权限的字段
     * 
     * @param className
     *                String 要得到属于哪个class的property
     * @param userId
     *                Integer 是哪个用户要访问这个class的数据
     * @return ArrayList 得到此用户拥有权限的属于这个class的所有property
     */
    public static ArrayList getClassProperty(String className, Long userId)
            throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            SelectCommand scmd = DAOFactory
                    .getSelectCommand(UserPropertyRight.class);
            return session.query(scmd);
        } finally {
            if (session != null)
                session.close();
        }
    }

}
