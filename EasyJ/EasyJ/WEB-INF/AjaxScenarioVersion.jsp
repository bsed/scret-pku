<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="easyJ.system.service.*,java.util.*,easyJ.common.*,easyJ.common.validate.*"%>
<%@ page import="cn.edu.pku.dr.requirement.elicitation.data.*,easyJ.http.*,easyJ.database.dao.Page"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<script language="javascript" src="/js/Datas.js"> </script>
<link rel="stylesheet" type="text/css" href="/css/jquery.panels.css"/>
<link rel="stylesheet" type="text/css" href="/css/example.css"/>
<link href="/css/ScenarioVersion.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(document).ready(function(){

        $("#1").bind("click",function(){


        if($(this).nextAll(".first").next().hasClass("target-closed")){

                $(this).nextAll(".first").next().removeClass("target-closed").removeClass("down").addClass("up");
                $(this).removeClass("panel-up").addClass("panel-down");

        }
        else{
                var temp =$(this).nextAll(".first").next();
                if(temp.next().hasClass("target-closed")){
                temp.next().addClass("target-closed");
                temp.next().removeClass("up");
                }

                $(this).nextAll(".first").next().removeClass("up").addClass("target-closed");


                $(this).removeClass("panel-down").addClass("panel-up");
        }
        });
        //$('.display').unbind("click");
        $("#1").nextAll(".first").next().bind("click",function(){
				var table = $("#1").nextAll(".first").next().next();
		var length = table.length;
		var content = new Array(length);
		for(var i =0;i<length;i++)
			content[i] = new Array(2);
		for(var row = 0; row<content.length;row++){
					content[row][0]= table[row].getAttribute("id");
					content[row][1]= table[row].innerHTML;
		}
		var scenarioVersionId = $(this).next().attr("id");
		var flag = false;
		for(var j = 0; j<content.length;j++){
		if ((content[j][0] ==scenarioVersionId)&&$(this).next().hasClass("added") ){
			flag = true;
			$(this).next().replaceWith(content[j][1]);
		}
		}
		if(flag == false){
				Ajax.submitLoad('cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction.do?ACTION=getContent&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion&ScenarioVersionId='+scenarioVersionId+'&ajax=true','#'+scenarioVersionId,'','');
				$(this).next().addClass("added");
				}
        if($(this).next().hasClass("target-closed")){
						$(this).addClass("down");
				$("#1").nextAll(".first").next().next().removeClass("down").addClass("target-closed");
				$(this).removeClass("up");
				$("#1").nextAll(".first").next().next().addClass("up");

                $(this).removeClass("up").addClass("down");
                $(this).next().removeClass("target-closed");
        }
        else{
                $(this).next().addClass("target-closed");
                $(this).removeClass("down").addClass("up");
        }
        });

});
</script>
<%
  Page pagecontent = (Page)request.getAttribute(Globals.PAGE);
  ArrayList svList=pagecontent.getPageData();
%>
<easyJ:FunctionScriptTag pageName="ScenarioVersion.jsp"/>
<easyJ:QueryHiddenTag/>
  <table class="border"><tr><td>
    <easyJ:QueryTag/>
    <easyJ:FunctionTag pageName="ScenarioVersion.jsp" position="<%=Globals.FUNCTION_QUERY%>"/>
  </td></tr></table>
<table>
<%
//*****bug***** 待修改，这里需要判断从page中传来的数据是否为空
ScenarioVersion scenarioversion = new ScenarioVersion();
if(svList.size()>0){
 scenarioversion=(ScenarioVersion)svList.get(0);
%>

<div id="1" class="panel-up name" href="#" title="<%=scenarioversion.getScenarioName()%>,有<%=pagecontent.getTotalNum()%>个场景版本,点击察看详情">&nbsp;&nbsp;&nbsp;<%=scenarioversion.getScenarioName()%></div>


<%

for(int i = 0;i<svList.size();i++){
	scenarioversion = (ScenarioVersion)svList.get(i);
%>
<div class="first" ></div>
<div class="target-closed" >
<span class="changeDomain" href="#" title="<%=scenarioversion.getScenarioName()%>,有<%=scenarioversion.getModifyMark().split("\\,").length%>域被改,点击察看详情">&nbsp;&nbsp;&nbsp;&nbsp; 版本号<%=scenarioversion.getScenarioVersionId()%>:</span>
<span class="name"><%=scenarioversion.getCreatorName()%> </span><span class="normal">于</span><span class="time"><%=scenarioversion.getUpdateTime().toGMTString()%> </span><span class="normal">修改了</span><span class="changeDomain"><%=scenarioversion.getModifyMark()%> </span></div>
<div class="target-closed" id="<%=scenarioversion.getScenarioVersionId()%>">&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;Loading...</div>


<%}%>

</table>
 <table class="border"><tr><td>
<easyJ:PageTag position="Data"/>
</td></tr></table>
<%




 }else{
   out.print("此场景没有历史版本");
 }
%>



<script>
function showScenario(scenarioVersionId)
  {
    <%
    	String url=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction.do?ACTION=showScenarioVersion&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion&scenarioversionId=","utf-8");
    %>
    var showUrl=<%="\""+url+"\""%>;
    window.open("easyJ.http.servlet.FowardAction.do?"+
    "jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+scenarioVersionId,"问题讨论","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");

  }

</script>

