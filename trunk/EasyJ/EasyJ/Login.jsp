<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>需求获取工具</title>
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
<p id="title" align="left"><a href="http://www.seforge.org">资源库首页&gt;&gt;</a>需求库</p>
<p align="center"> <img  src="image/requirement.gif" height="40%"/></p>
<form name="form1" method="post" action="">
<p align="center">用户：<input  name="userName" type="text" value="" maxlength="10" onkeydown="enterUserName(event)"/>
密码：<input name="password" id="password" type="password"  size="20" onkeydown="enterPassword(event)"/>
<input type="button" value="登录" onClick="login()"/><input type="button" value="注册" onClick="register()"/></p>
</form>
<script language="javascript">	
<%//这里请参照Header.jsp，如果为空，说明是从空间过来的，如果不为空，说明是用户点击要登录的。
//if(request.getParameter("clickLogin")==null) {%>
//    form1.action="cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=login&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser&userName=guest&password=123";
//	form1.submit();
<%//}%>
</script>
</body>
</html>
