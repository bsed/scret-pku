<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�����ȡ����</title>
</head>
<script language="javascript">

    function login()
    {
          form1.action="cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=login&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser";
            form1.submit();
    }
        function register(){
          form1.action="cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=register&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser";
          form1.submit();
        }
        function enterUserName(event)
        {
            var key = -1;
            if (window.event) { //IE
                    key = event.keyCode;
            } else { // Gecko
                    key = event.which;
            }
            if(key==13) document.getElementById("password").focus();
        }
    function enterPassword(event) {
            var key = -1;
            if (window.event) { //IE
                    key = event.keyCode;
            } else { // Gecko
                    key = event.which;
            }
            if(key==13) login();
    }
</script>
<body>
<p id="title" align="left"><a href="http://www.seforge.org">��Դ����ҳ&gt;&gt;</a>�����</p>
<p align="center"> <img  src="image/requirement.gif" height="40%"/></p>
<form name="form1" method="post" action="">
<p align="center">�û���<input  name="userName" type="text" value="" maxlength="10" onkeydown="enterUserName(event)"/>
���룺<input name="password" id="password" type="password"  size="20" onkeydown="enterPassword(event)"/>
<input type="button" value="��¼" onClick="login()"/><input type="button" value="ע��" onClick="register()"/></p>
</form>
<script language="javascript">	
<%//���������Header.jsp�����Ϊ�գ�˵���Ǵӿռ�����ģ������Ϊ�գ�˵�����û����Ҫ��¼�ġ�
//if(request.getParameter("clickLogin")==null) {%>
//    form1.action="cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=login&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser&userName=guest&password=123";
//	form1.submit();
<%//}%>
</script>
</body>
</html>
