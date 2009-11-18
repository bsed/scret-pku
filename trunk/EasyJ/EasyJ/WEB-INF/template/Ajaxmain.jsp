<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,easyJ.system.data.*,easyJ.business.proxy.*"%>
<%@ page import="easyJ.common.validate.*"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<html>
<head>
<title>
协同需求获取工具
</title>
<link href="table.css" rel="css/stylesheet" type="text/css">
<link href="pattern.css" rel="css/stylesheet" type="text/css">
<link rel="stylesheet" href="css/scenario.css" type="text/css" />
<link rel="stylesheet" href="iknow1_1.css" type="text/css" />
<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" src="js/jquery.form.js"> </script>
<script language="javascript" src="js/Ajax.js"></script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/Problem.js"></script>
<script language="javascript" src="js/Problemsolution.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<script language="javascript">
function checkBrowser(){
    var cb = "Unknown";
    if(window.ActiveXObject){
        cb = "IE";
    }else if(navigator.userAgent.toLowerCase().indexOf("firefox") != -1){
        cb = "FireFox";
    }else if((typeof document.implementation != "undefined") && (typeof document.implementation.createDocument != "undefined") && (typeof HTMLDocument != "undefined")){
        cb = "Mozilla";
    }else if(navigator.userAgent.toLowerCase().indexOf("opera") != -1){
        cb = "Opera";
    }
    return cb;
}

var broswer=checkBrowser();

function moduleQuery(actionName){
  var formData={};
  $.ajax({
     type: "POST",
     url: actionName,
     data: formData,
     success: function(msg){
       $("#columnMain").empty();
       $("#columnMain").append(msg);
       Ajax.saveHistory(msg);
     }
  });
  //$("#columnMain").load(actionName,formData,saveHistory());
}

</script>
<script language="javascript" type="text/javascript" src="js/Validate.js"></script>
<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" src="js/jquery.form.js"> </script>
<script language="javascript" src="js/tree.js"></script>
<script language="javascript" src="js/Ajax.js"></script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/jquery.dimensions.js"></script>
<script language="javascript" src="js/ui.base.js"></script>
<script language="javascript" src="js/ui.draggable.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/hint.js"></script>
<script language="javascript" src="js/PopUpWindow.js"></script>
<script language="javascript" src="js/ScriptLoader.js"></script>

</head>
<body onload="initEnter2Tab()">

<form name="form1" id="form1" method="post" action="" type="enter2tab">
  <jsp:include flush="true" page="common/Header.jsp">
  </jsp:include>
  <jsp:include flush="true" page="common/Left.jsp">
  </jsp:include>
  <div id="message" align="center">
  </div>
  <div id="columnMain">
  </div>
  <jsp:include flush="true" page="common/Right.jsp">
  </jsp:include>
  <div id="footer">
    @Copyright: Institute of Software School of Electronics Engineering and Computer Science,Peking University, Beijing, China
   </div>
</form>
</body>
</html>

