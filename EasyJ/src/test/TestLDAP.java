package test;

import easyJ.system.service.LDAPService;
import easyJ.system.data.SysUser;

public class TestLDAP {
    public TestLDAP() {}

    public static void main(String[] args) {
        try {
            SysUser user = LDAPService.getUserFromLDAP("liufeng06");
            System.out.println(user.getUserName() + "  " + user.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
