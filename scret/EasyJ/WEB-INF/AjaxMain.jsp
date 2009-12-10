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
<link href="css/common.css" rel="stylesheet" type="text/css">
<link href="css/common/dialog.css" rel="stylesheet" type="text/css">
<link href="css/table.css" rel="stylesheet" type="text/css">
<link href="css/pattern.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<script language="javascript" type="text/javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" type="text/javascript" src="js/jquery.form.js"> </script>
<script language="javascript" type="text/javascript" src="js/jquery.dimensions.js"></script>
<script language="javascript" src="js/jqDnR.js"> </script>
<script language="javascript" type="text/javascript" src="js/ui.base.js"></script>
<script language="javascript" type="text/javascript" src="js/ui.draggable.js"></script>

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
       sizeControlImg.src="image/assoc_up.gif";
       sizeControlImg.alt="最大化";
    }else{
       left.style.display="none";
       right.style.display="none";
       main.style.width="95%";
       main.style.maxWidth = "95%";
       sizeControl.style.width="96%";
       sizeControlImg.src="image/assoc_down.gif";
       sizeControlImg.alt="最小化";
    }
}

/*这个方法用来显示场景角色数据关系图的，因为需要在功能列表中使用此方法，所以放到这里*/

  function getSelectedScenarios() {
 	var dest=document.getElementById("destSelect");
 	var selectedValue=[];
	for(i=0;i<dest.length;i++){
		selectedValue=selectedValue+dest.options[i].value+",";
	}
	var data={};
	data.scenarios=selectedValue;
    var fileName=Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=showRelationDiagram&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Scenario&ajax=true",null,data);
    document.getElementById("sdrRelation").style.display="block";
    document.getElementById("sdrRelation").innerHTML=
    "<applet code=\"InteractionAppletStart.class\" archive = \"graph.jar\" width=\"100%\" height=\"500\" >"+
    "<param name = \"inputFileName\" value = \""+fileName["fileName"]+"\">"+
    "</applet>";
  }
  
  function showRelationDiagram() {
	  var data={};
	  data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.Scenario";
	  Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=multiSelect", "#columnMain", '',data,false);
      //将参数传递给MultiSelect，在确认的时候使用。
      MultiSelect.confirm=getSelectedScenarios;
      MultiSelect.data=data;
      MultiSelect.cacheSourceData();
  }
  
  
/*这个方法用来显示场景角色数据关系矩阵的，因为需要在功能列表中使用此方法，所以放到这里*/

  function getSelectedScenariosForMatix() {
 	var dest=document.getElementById("destSelect");
 	var selectedValue=[];
	for(i=0;i<dest.length;i++){
		selectedValue=selectedValue+dest.options[i].value+",";
	}
	var data={};
	data.scenarios=selectedValue;
    var fileName=Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=showRelationMatrix&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Scenario&ajax=true",null,data);
    document.getElementById("sdrRelation").style.display="block";
    document.getElementById("sdrRelation").innerHTML=
    "<applet code=\"RelationAppletStart.class\" archive = \"graph.jar\" width=\"100%\" height=\"500\" >"+
    "<param name = \"inputFileName\" value = \""+fileName["fileName"]+"\">"+
    "</applet>";
  }
  
  function showRelationMatrix() {
	  var data={};
	  data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.Scenario";
	  Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=multiSelect", "#columnMain", '',data,false);
      //将参数传递给MultiSelect，在确认的时候使用。
      MultiSelect.confirm=getSelectedScenariosForMatix;
      MultiSelect.data=data;
      MultiSelect.cacheSourceData();
  }
</script>
<script language="javascript" type="text/javascript" src="js/Util.js"></script>
<script language="javascript" type="text/javascript" src="js/Validate.js"></script>

<script language="javascript" type="text/javascript" src="js/tree.js"></script>
<script language="javascript" type="text/javascript" src="js/Ajax.js"></script>
<script language="javascript" type="text/javascript" src="js/Data.js"></script>

<script language="javascript" type="text/javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" type="text/javascript" src="js/hint.js"></script>
<script language="javascript" type="text/javascript" src="js/PopUpWindow.js"></script>
<script language="javascript" type="text/javascript" src="js/MultiSelect.js"></script>
<script language="javascript" type="text/javascript" src="js/ScriptLoader.js"></script>



</head>
<body>
<div id="pageWrapper"> <!-- 为了CSS而加入的额外DIV -->

<form name="form1" id="form1" method="post" action="">

  <jsp:include flush="true" page="/WEB-INF/template/common/Header.jsp"/>
  
  <div id="message" align="center">
  </div>
      <div id="sizeControl" align="right">
        <img id="sizeControlImg" alt="最大化" src="image/assoc_up.gif" onclick="changeSize()"/>
     </div>
  <jsp:include flush="true" page="/WEB-INF/template/common/Left.jsp"/>

  <div id="columnMain">

    <div class="border">
      
	  <DIV class="NewsTitleRight" style="width:30%"><DIV class=NewsTitleLeft>需求库简介</DIV></DIV>
        <div style="COLOR: #000080" align=left>
        　　欢迎来到需求库! 通过领域需求库的帮助来发现、明确并表达自己的需求。 明确的需求是项目成功的基础，本知识库通过各个领域所积累的需求知识，并且通过协同的方法帮助用户来明确地、完整的、无歧义地表达自己的需求。 </div>
        <div style="COLOR: #000080" align=left>　　附: 需求库通过总结领域之内需求的共性以及差异性给用户提供基于实际项目的建议。并基于领域专家的知识给用户提供专业的帮助。</div>
      </div>
    <div class="border">
        <DIV class=NewsTitleRight style="width:30%"><DIV class=NewsTitleLeft>需求库使用简介</DIV></DIV>
        <div style="COLOR: #000080" align=left>　首先会为用户建立自己的项目，并发现相应的角色，数据以及场景。用户可以在自己拥有的场景当中讨论自己的日常工作，并指出日常工作中存在的问题，并提出解决方案。在描述清楚当前的场景之后，用户会提出响应的期望场景。描述在有系统参与的时候业务应该如何来处理。</div>
        <div style="COLOR: #000080" align=left>　附: 需求库通过总结领域之内需求的共性以及差异性给用户提供基于实际项目的建议。并基于领域专家的知识给用户提供专业的帮助。</div>
     </div>

  </div>

  <jsp:include flush="true" page="/WEB-INF/template/common/Right.jsp"/>

  <div id="footer">
    @Copyright: Institute of Software School of Electronics Engineering and Computer Science,Peking University, Beijing, China
   </div>
</form>

</div>
</body>
</html>
