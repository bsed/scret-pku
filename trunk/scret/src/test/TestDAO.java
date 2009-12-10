package test;

import easyJ.database.session.*;
import java.util.List;
import easyJ.database.*;
import easyJ.tools.*;
import easyJ.database.dao.*;
import java.util.ArrayList;
import easyJ.system.data.*;

public class TestDAO {
    public TestDAO() {}

    public static void main(String[] args) throws easyJ.common.EasyJException {
        Student student = new Student();

        Session session = SessionFactory.openSession();
        Page page = session.query(student, student, 2);
        // Page page=session.query(upr,upr,1,orderRules);
        // Page page=session.query(student,1);
        // Page page=session.query(student,1,orderRules);
        // System.out.println(page.getPageData().size());

        // ArrayList list=session.query(student);
        // ArrayList list=session.query(student,student1,orderRules);
        // ArrayList list=session.query(student);
        // ArrayList list=session.query(upr,orderRules);
        // System.out.println(list.size());
        for (int i = 0; i < page.getPageData().size(); i++) {
            System.out.println(page.getPageData().get(i));
        }
        /*
         * Student insertStudent=new Student(); insertStudent.setEmpId(new
         * Integer(5)); insertStudent.setEmpName("liufeng");
         * session.delete(insertStudent);
         */
    }
}
