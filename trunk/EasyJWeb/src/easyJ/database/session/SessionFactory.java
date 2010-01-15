package easyJ.database.session;

public class SessionFactory {
    /**
     * <p>
     * 用来得到对数据库操作的session，所有的数据库操作都是通过session
     * </p>
     * 
     * @throws EasyJException
     * @author LiuFeng
     * @return Session
     */
    public static Session openSession() throws easyJ.common.EasyJException {
        return new SessionImpl();
    }
}
