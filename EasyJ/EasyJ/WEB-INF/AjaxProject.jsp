<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,easyJ.system.data.*,easyJ.business.proxy.*"%>
<%@ page import="easyJ.common.validate.*"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>
协同式需求获取工具
</title>
<link href="/css/common.css" rel="stylesheet" type="text/css">
<link href="/css/common/dialog.css" rel="stylesheet" type="text/css">
<link href="/css/table.css" rel="stylesheet" type="text/css">
<link href="/css/pattern.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<script language="javascript" type="text/javascript" src="/js/jquery-1.2.3.js"> </script>
<script language="javascript" src="/js/jqDnR.js"> </script>
<script language="javascript" type="text/javascript" src="/js/jquery.form.js"> </script>
<script language="javascript" type="text/javascript" src="/js/jquery.dimensions.js"></script>
<script language="javascript" type="text/javascript" src="/js/ui.base.js"></script>
<script language="javascript" type="text/javascript" src="/js/ui.draggable.js"></script>

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
var midX=screen.width/2;
var midY=screen.height/2;

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

function changeSize() {
    var left=document.getElementById("columnLeft");
    var right=document.getElementById("columnRight");
    var main=document.getElementById("columnMain");
    var sizeControl=document.getElementById("sizeControl");
    var sizeControlImg=document.getElementById("sizeControlImg");
    // sizeControl的宽度比left + main多1%的话，效果比较好
    if(left.style.display=="none") {
       left.style.display="block";
       right.style.display="block";
       main.style.width="72%";
       main.style.maxWidth = "72%";
       sizeControl.style.width="85%";
       sizeControlImg.src="/image/assoc_up.gif";
       sizeControlImg.alt="最大化";
    }else{
       left.style.display="none";
       right.style.display="none";
       main.style.width="95%";
       main.style.maxWidth = "95%";
       sizeControl.style.width="96%";
       sizeControlImg.src="/image/assoc_down.gif";
       sizeControlImg.alt="最小化";
    }
}
</script>
<script language="javascript" type="text/javascript" src="/js/Util.js"></script>
<script language="javascript" type="text/javascript" src="/js/Validate.js"></script>

<script language="javascript" type="text/javascript" src="/js/tree.js"></script>
<script language="javascript" type="text/javascript" src="/js/Ajax.js"></script>
<script language="javascript" type="text/javascript" src="/js/Data.js"></script>

<script language="javascript" type="text/javascript" src="/js/Enter2Tab.js"></script>
<script language="javascript" type="text/javascript" src="/js/hint.js"></script>
<script language="javascript" type="text/javascript" src="/js/PopUpWindow.js"></script>
<script language="javascript" type="text/javascript" src="/js/MultiSelect.js"></script>
<script language="javascript" type="text/javascript" src="/js/ScriptLoader.js"></script>



</head>
<body>
<div id="pageWrapper"> <!-- 为了CSS而加入的额外DIV -->

<form name="form1" id="form1" method="post" action="">
  <jsp:include flush="true" page="/WEB-INF/template/common/Header.jsp"/>

  <div id="message" align="center">
  </div>
    <div id="sizeControl" align="right">
        <img id="sizeControlImg" alt="最大化" src="/image/assoc_up.gif" onclick="changeSize()"/>
    </div>
	
	<div id="columnLeft">
	  <easyJ:TreeTag className="easyJ.system.data.Module" selectMode="2">
	  	<easyJ:TreeConditionTag property="moduleType" value=",project,"/>
	  </easyJ:TreeTag>
	</div>

  <div id="columnMain">
	<easyJ:ObjectListTag actionName="cn.edu.pku.dr.requirement.elicitation.action.ProjectAction" 
	className="cn.edu.pku.dr.requirement.elicitation.data.Project" imgProperty="" showProperty="projectName"
	tabProperty="domainName"/>
  </div>

  <jsp:include flush="true" page="/WEB-INF/template/common/Right.jsp"/>

  <div id="footer">
    @Copyright: Institute of Software School of Electronics Engineering and Computer Science,Peking University, Beijing, China
   </div>
</form>

</div>
</body>
</html>
