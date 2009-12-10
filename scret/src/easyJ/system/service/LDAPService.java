package easyJ.system.service;

import javax.naming.ldap.LdapContext;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;
import easyJ.system.data.SysUser;
import java.util.Iterator;
import easyJ.common.EasyJException;

public class LDAPService {
    public static final String LDAPServerAddress = "192.168.4.120";

    public LDAPService() {}

    public static SysUser getUserFromLDAP(String uid) throws EasyJException {
        LDAPConnection connection = new LDAPConnection();
        try {
            connection.connect(LDAPServerAddress, 389);
            connection.bind(LDAPConnection.LDAP_V3, "cn=admin,dc=sei,dc=pku",
                    "seiseforge");
            LDAPSearchResults rs = connection.search("uid=" + uid
                    + ",ou=People,o=SEForge,dc=sei,dc=pku",
                    LDAPConnection.SCOPE_SUB, "objectClass=*", null, false);
            SysUser user = new SysUser();
            user.setUserName(uid);
            if (!rs.hasMore()) {
                throw new EasyJException(
                        null,
                        "easyJ.system.service.LDAPService.getUserFromLDAP(String)",
                        user.getUserName() + "的用户名密码错", "用户名密码错");
            }
            while (rs.hasMore()) {

                LDAPEntry entry = rs.next();
                LDAPAttributeSet attSet = entry.getAttributeSet();
                Iterator it = attSet.iterator();
                while (it.hasNext()) {
                    LDAPAttribute attr = (LDAPAttribute) it.next();
                    if (attr.getName().equalsIgnoreCase("userPassword")) {
                        user.setPassword(attr.getStringValue());
                    }
                }
            }
            return user;
        } catch (LDAPException e) {
            // e.printStackTrace();
            throw new EasyJException(null,
                    "easyJ.system.service.LDAPService.getUserFromLDAP(String)",
                    "验证服务器出错", "验证服务器出错");
        } finally {
            try {
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addUserToLDAP(SysUser user) throws EasyJException {
        LDAPConnection connection = new LDAPConnection();
        try {
            LDAPAttributeSet attributeSet = new LDAPAttributeSet();

            attributeSet.add(new LDAPAttribute("objectclass", new String[] {
                "pilotPerson", "uidObject"
            }));
            attributeSet.add(new LDAPAttribute("uid", user.getUserName()));
            attributeSet.add(new LDAPAttribute("userPassword", user
                    .getPassword()));
            attributeSet.add(new LDAPAttribute("mail", user.getEmail()));
            attributeSet.add(new LDAPAttribute("sn", "snMass"));
            attributeSet.add(new LDAPAttribute("cn", "cnMass"));
            LDAPEntry entry = new LDAPEntry("uid=" + user.getUserName()
                    + ",ou=People,o=SEForge,dc=sei,dc=pku", attributeSet);
            connection.connect(LDAPServerAddress, 389);
            connection.bind(LDAPConnection.LDAP_V3, "cn=admin,dc=sei,dc=pku",
                    "seiseforge");
            connection.add(entry);
            System.out.println("成功的添加了一条记录！");
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EasyJException(null,
                    "easyJ.system.service.LDAPService.addUserToLDAP(SysUser)",
                    "验证服务器出错", "验证服务器出错");
        }

    }

    public static void changePassword(SysUser user) throws EasyJException {
        LDAPConnection connection = new LDAPConnection();
        try {
            connection.connect(LDAPServerAddress, 389);
            connection.bind(LDAPConnection.LDAP_V3, "cn=admin,dc=sei,dc=pku",
                    "seiseforge");
            connection.modify("uid=" + user.getUserName()
                    + ",ou=People,o=SEForge,dc=sei,dc=pku",
                    new LDAPModification(LdapContext.REPLACE_ATTRIBUTE,
                            new LDAPAttribute("userPassword", user
                                    .getPassword())));
            System.out.println("成功修改一条记录！");
            connection.disconnect();
        } catch (LDAPException e) {
            e.printStackTrace();
            throw new EasyJException(null,
                    "easyJ.system.service.LDAPService.changePassword(SysUser)",
                    "验证服务器出错", "验证服务器出错");
        }

    }

}
