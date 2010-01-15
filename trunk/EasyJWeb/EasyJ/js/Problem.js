
var Problem = {};
(function () {
    //进入problem页面的时候首先就加载问题主体部分和解决方案部分。
	Problem.loadProblem = function (problemId) {
		Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemAction.do?ACTION=getProblem&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problem&problemId=" + problemId + "&ajax=true", "#problem", "", false);
		document.getElementById("problem_main").style.display = "block";
		document.getElementById("tabs").style.display = "block";
	};
	Problem.loadProblemInside = function (problemId) {
	};
	Problem.modifyProblem = function (problemId) {
		document.getElementById("modify_problem").innerHTML = "";
		document.getElementById("ambiguity_list").innerHTML = "Loading";
		document.getElementById("modify_problem_button").style.display = "none";
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemAction.do?ACTION=getAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problem&problemId=" + problemId + "&order=tisme&group=y&ajax=true", "#ambiguity_list", "", false);
        //在modify_problem里面提供一个供用户描述的textarea
		var rts = "";
		rts += "\u8bf7\u8f93\u5165\u95ee\u9898\u7684\u5185\u5bb9<br><textarea name=\"problemContent\" id = \"problemContent\" cols=\"75%\" rows=\"6\" ></textarea><br>\u8bf7\u9009\u62e9\u60a8\u8ba4\u4e3a\u4e0d\u518d\u6709\u7591\u95ee\u7684\u7b54\u590d\uff1a";
		document.getElementById("modify_problem").innerHTML = rts;
        //将现有的没有标为失效的ambuity写入ambiguity_list
		rts = "";
		rts += "<a href=\"javascript:Problem.modifyingProblem(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u4fee\u6539\u95ee\u9898</a>&nbsp;";
		rts += "<a href=\"javascript:Problem.newsHide(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u5173\u95ed</a>&nbsp;";
		document.getElementById("modify_problem_button").innerHTML = rts;
		document.getElementById("modify_problem").style.display = "block";
		document.getElementById("ambiguity_list").style.display = "block";
		document.getElementById("modify_problem_button").style.display = "block";
	};
	Problem.newsHide = function (problemId) {
		document.getElementById("modify_problem").style.display = "none";
		document.getElementById("ambiguity_list").style.display = "none";
		document.getElementById("modify_problem_button").innerHTML = "<a href=\"javascript:Problem.modifyProblem(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u4fee\u6539\u95ee\u9898</a>";
	};
	Problem.newsShowHide = function (_obj) {
		var newsTD = document.getElementsByTagName("td");
		var obj = document.getElementById(_obj);
		if (obj.style.display == "none") {
			for (i = 0; i < newsTD.length; i++) {
				if (newsTD[i].id.indexOf("ambiguity") != -1) {
					newsTD[i].style.display = "none";
				}
			}
			obj.style.display = "";
		} else {
			if (obj.style.display != "none") {
				obj.style.display = "none";
			}
		}
	};
	Problem.changeTRbg = function (_self, _obj) {
		obj = document.getElementById(_obj);
		if (_self.checked) {
			obj.className = "BG3";
		} else {
			obj.className = "";
			checkall.checked = false;
		}
	};
	Problem.checkAll = function (status) {
		var chbox = document.getElementsByName("overdue_ambiguity");
		var myL = chbox.length;
		for (i = 0; i < myL; i++) {
			chbox[i].checked = status;
		}
		var trBG = document.getElementsByTagName("tr");
		for (i = 0; i < trBG.length; i++) {
			if (trBG[i].id.indexOf("trBG") != -1 && checkall.checked == true) {
				trBG[i].className = "BG3";
			} else {
				if (trBG[i].id.indexOf("trBG") != -1 && checkall.checked == false) {
					trBG[i].className = "";
				}
			}
		}
	};
	Problem.modifyingProblem = function (problemId) {
		if (document.getElementById("problemContent").innerHTML == "") {
			alert("\u95ee\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01");
		} else {
			Problem.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemAction.do?ACTION=modifyProblem&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problem&problemId=" + problemId + "&ajax=true", "#form1", "");
			Problem.newsHide(problemId);
			Problem.discussProblemAmbiguity(problemId);
		}
	};
	Problem.showAllSolution = function () {
		alert("todo:  show all the solutions.");
	};
	Problem.reply = function () {
		alert("ww");
		document.getElementById("reply_area").focus();
		alert("ww");
	};
	Problem.saveanswer = function () {
	};
	Problem.veryhighValues = function () {
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else {
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		xmlHttp.onreadystatechange = Message.writeMessageResult;
		var url = "cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=writeMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&messageReceiver=1&messageTitle=\u8fd9\u662f\u7cfb\u7edf\u81ea\u52a8\u53d1\u9001\u7684\u6d88\u606f&messageContent=\u6709\u4eba\u5bf9\u60a8\u7684\u63d0\u95ee\u505a\u51fa\u4e86\u8bc4\u4ef7";
		try {
			xmlHttp.open("GET", url, true);
			xmlHttp.send(null);
		}
		catch (e) {
		}
	};
	Problem.highValue = function () {
	};
	Problem.value = function () {
	};
	Problem.lowValue = function () {
	};
	Problem.discussProblemReason = function (problemId) {
		/*document.getElementById("value").style.display = "none";
         document.getElementById("solution").style.display = "none";
         document.getElementById("solution_more").style.display = "none";
         
         document.getElementById("reason").style.display = "block";
         document.getElementById("reason_more").style.display = "block";
         */
		$("#ProblemValueRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemValueLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#AmbiguityRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#AmbiguityLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#ProblemSolutionRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemSolutionLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		if ($("#ProblemReasonRightTab").hasClass("NewsTitleRight")) {
			$("#ProblemReasonRightTab").removeClass("NewsTitleRight").addClass("NewsTitleRightClick");
			$("#ProblemReasonLeftTab").removeClass("NewsTitleLeft").addClass("NewsTitleLeftClick");
		}
		document.getElementById("solution_more").style.display = "none";
		document.getElementById("value_more").style.display = "none";
		document.getElementById("ambiguity_more").style.display = "none";
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=getProblemreason&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemreason&problemId=" + problemId + "&ajax=true", "#problemreason", "", false);
		document.getElementById("reason_more").style.display = "block";
	};
	Problem.discussProblemValue = function (problemId) {
		/*document.getElementById("reason").style.display = "none";
         document.getElementById("explan").style.display = "none";
         document.getElementById("explan_more").style.display = "none";
         document.getElementById("solution").style.display = "none";
         document.getElementById("solution_more").style.display = "none";
         
         
         document.getElementById("value").style.display = "block";*/
		$("#ProblemReasonRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemReasonLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#AmbiguityRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#AmbiguityLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#ProblemSolutionRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemSolutionLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		if ($("#ProblemValueRightTab").hasClass("NewsTitleRight")) {
			$("#ProblemValueRightTab").removeClass("NewsTitleRight").addClass("NewsTitleRightClick");
			$("#ProblemValueLeftTab").removeClass("NewsTitleLeft").addClass("NewsTitleLeftClick");
		}
		document.getElementById("solution_more").style.display = "none";
		document.getElementById("reason_more").style.display = "none";
		document.getElementById("ambiguity_more").style.display = "none";
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=getProblemvalue&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemvalue&problemId=" + problemId + "&ajax=true", "#problemvalue", "", false);
		document.getElementById("value_more").style.display = "block";
	};
	Problem.discussProblemAmbiguity = function (problemId) {
		$("#ProblemSolutionRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemSolutionLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#ProblemValueRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemValueLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#ProblemReasonRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemReasonLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		if ($("#AmbiguityRightTab").hasClass("NewsTitleRight")) {
			$("#AmbiguityRightTab").removeClass("NewsTitleRight").addClass("NewsTitleRightClick");
			$("#AmbiguityLeftTab").removeClass("NewsTitleLeft").addClass("NewsTitleLeftClick");
		}
		/*
         document.getElementById("reason").style.display="none";
         document.getElementById("reason_more").style.display="none";
         document.getElementById("value").style.display="none";
         document.getElementById("value_more").style.display="none";
         document.getElementById("solution").style.display="none";
         document.getElementById("solution_more").style.display="none";
         */
        //document.getElementById("explan").style.display="block";
		document.getElementById("solution_more").style.display = "none";
		document.getElementById("reason_more").style.display = "none";
		document.getElementById("value_more").style.display = "none";
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=getAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&ajax=true", "#ambiguity", "", false);
		document.getElementById("ambiguity_more").style.display = "block";
	};
	Problem.discussProblemSolution = function (problemId) {
		$("#ProblemValueRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemValueLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#ProblemReasonRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#ProblemReasonLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		$("#AmbiguityRightTab").removeClass("NewsTitleRightClick").addClass("NewsTitleRight");
		$("#AmbiguityLeftTab").removeClass("NewsTitleLeftClick").addClass("NewsTitleLeft");
		if ($("#ProblemSolutionRightTab").hasClass("NewsTitleRight")) {
			$("#ProblemSolutionRightTab").removeClass("NewsTitleRight").addClass("NewsTitleRightClick");
			$("#ProblemSolutionLeftTab").removeClass("NewsTitleLeft").addClass("NewsTitleLeftClick");
		}
		document.getElementById("reason_more").style.display = "none";
		document.getElementById("value_more").style.display = "none";
		document.getElementById("ambiguity_more").style.display = "none";
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=getProblemsolution&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemsolution&problemId=" + problemId + "&ajax=true", "#problemsolution", "", false);
		document.getElementById("solution_more").style.display = "block";
	};
	Problem.veryhighValue = function () {
        //从服务器生成一个可供选择的页面,利用ajax技术 	
	};
	Problem.flower = function () {
	};
	Problem.badegg = function () {
	};
	Problem.showHide = function (_obj) {
		if (document.getElementById(_obj).style.display == "none") {
			document.getElementById(_obj).style.display = "block";
		} else {
			document.getElementById(_obj).style.display = "none";
		}
	};
	Problem.secStep = function () {
		document.getElementById("firstStep").innerHTML = "\u63d0\u4ea4";
		document.getElementById("showDetails").innerHTML = " ";
		var rts = "";
		rts += "\u8bf7\u60a8\u586b\u5199\u539f\u56e0<br><textarea name=\"messageTitle\" id = \"messageTitle\" cols=\"75%\" rows=\"1\" ></textarea>";
		rts += "<span style=\"cursor:pointer\">\u6dfb\u52a0\u65b0\u7684\u539f\u56e0</span>";
		rts += "<div id=\"Lg\"></div>";
		rts += "\u8bf7\u60a8\u63cf\u8ff0\u60a8\u7684\u89e3\u51b3\u65b9\u6848<br><textarea name=\"messageContent\" id = \"messageContent\" cols=\"75%\" rows=\"6\" ></textarea><br><br>";
		document.getElementById("secondStep").innerHTML = rts;
        //alert(document.getElementById("secondStep").style.display);
        //document.getElementById("secnodStep").style.display="block";
        //alert(document.getElementById("secondStep").style.display);
		document.getElementById("diyibu").style.color = "#000000";
		document.getElementById("dierbu").style.color = "#FF0000";
	};
	Problem.ambiguityEvaluation = function (evaluationTypeId, userId, problemId) {
		Problem.submit("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=ambiguityTypeEvaluation&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue&ambiguityTypeId=" + evaluationTypeId + "&creatorId=" + userId + "&problemId=" + problemId + "&ajax=true", "", "");
	};
	Problem.problemvalueEvaluation = function (evaluationTypeId, userId, problemId) {
		Problem.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=problemvalueTypeEvaluation&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue&problemvalueTypeId=" + evaluationTypeId + "&creatorId=" + userId + "&problemId=" + problemId + "&ajax=true", "", "");
	};
	Problem.submit = function (actionPath, formId, data) {
		var value = {};
		var formData = [];
		if (formId != "") {
			formData = $(formId).formToArray();
		}
		if (data) {
			for (var option in data) {
				formData.push({name:option, value:data[option]});
			}
		}
		$.ajax({async:false, type:"POST", url:actionPath, data:formData, success:function (msg) {
                //messgae[0]提示何种操作是否成功
			var message = [];
			message = msg.split("**");
                
                //ambiguityTypeEvaluation
			if (message[0] == "ambiguityTypeEvaluation_success") {
				Problem.discussProblemAmbiguity(message[1]);
			} else {
				if (message[0] == "ambiguityTypeEvaluation_failure") {
					alert(message[0]);
				}
			}
                //problemvalueTypeEvaluation
			if (message[0] == "problemvalueTypeEvaluation_success") {
				Problem.discussProblemValue(message[1]);
			} else {
				if (message[0] == "problemvalueTypeEvaluation_failure") {
					alert(message[0]);
				}
			}
                //voting
			if (msg == "votingfailure") {
				alert("\u6295\u7968\u5931\u8d25");
			}
                
                //modify_problem
			if (message[0] == "modify_problem_success") {
				alert("\u4fee\u6539\u6210\u529f ");
				document.getElementById("question_content").innerHTML = "<cd>" + message[1] + "</cd>";
			} else {
				if (message[0] == "modify_problem_failure") {
					alert(message[0]);
				}
			}
		}});
		return value;
	};
	Problem.createXMLHttprequest = function () {
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else {
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return xmlHttp;
	};
    
    
    //对于ambiguity的评价部分
	Problem.flower = function (problemId, AmbiguityId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=ambiguityUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation&ambiguityId=" + AmbiguityId + "&creatorId=" + userId + "&flower=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.ambiguityUpdateResult;
		xmlHttp.send(null);
	};
	Problem.badegg = function (problemId, AmbiguityId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=ambiguityUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation&ambiguityId=" + AmbiguityId + "&creatorId=" + userId + "&badegg=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.ambiguityUpdateResult;
		xmlHttp.send(null);
	};
	Problem.overdue = function (problemId, AmbiguityId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=ambiguityUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation&ambiguityId=" + AmbiguityId + "&creatorId=" + userId + "&overdue=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.ambiguityUpdateResult;
		xmlHttp.send(null);
	};
	Problem.confirm = function (problemId, AmbiguityId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=ambiguityUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation&ambiguityId=" + AmbiguityId + "&creatorId=" + userId + "&confirm=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.ambiguityUpdateResult;
		xmlHttp.send(null);
	};
	Problem.reject = function (problemId, AmbiguityId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=ambiguityUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation&ambiguityId=" + AmbiguityId + "&creatorId=" + userId + "&confirm=n";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.ambiguityUpdateResult;
		xmlHttp.send(null);
	};
	Problem.ambiguityUpdateResult = function () {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var response = xmlHttp.responseXML;
				if ("success" == response.getElementsByTagName("message")[0].firstChild.nodeValue) {
					if ("flower" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var ambiguityId = response.getElementsByTagName("ambiguityId")[0].firstChild.nodeValue;
						document.getElementById("flower" + ambiguityId).style.display = "none";
						document.getElementById("badegg" + ambiguityId).style.display = "none";
						var temp = document.getElementById("flowernum" + ambiguityId).innerHTML;
						var good_num = Number(temp);
						document.getElementById("flowernum" + ambiguityId).innerHTML = good_num + 1;
					}
					if ("badegg" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var ambiguityId = response.getElementsByTagName("ambiguityId")[0].firstChild.nodeValue;
						document.getElementById("badegg" + ambiguityId).style.display = "none";
						document.getElementById("flower" + ambiguityId).style.display = "none";
						var temp = document.getElementById("badeggnum" + ambiguityId).innerHTML;
						var bad_num = Number(temp);
						document.getElementById("badeggnum" + ambiguityId).innerHTML = bad_num + 1;
					}
					if ("overdue" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var ambiguityId = response.getElementsByTagName("ambiguityId")[0].firstChild.nodeValue;
						document.getElementById("overdue" + ambiguityId).style.display = "none";
						document.getElementById("overdue2" + ambiguityId).style.display = "block";
					}
					if ("confirm" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var ambiguityId = response.getElementsByTagName("ambiguityId")[0].firstChild.nodeValue;
						document.getElementById("confirm1" + ambiguityId).style.display = "none";
						document.getElementById("reject1" + ambiguityId).style.display = "none";
						document.getElementById("confirm2" + ambiguityId).style.display = "block";
					}
					if ("reject" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var ambiguityId = response.getElementsByTagName("ambiguityId")[0].firstChild.nodeValue;
						document.getElementById("confirm1" + ambiguityId).style.display = "none";
						document.getElementById("reject1" + ambiguityId).style.display = "none";
						document.getElementById("reject2" + ambiguityId).style.display = "block";
					}
				}
			}
		}
	};
    
    //对于problemvalue的评价部分
	Problem.problemvalueFlower = function (problemId, ProblemvalueId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=problemvalueUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation&problemvalueId=" + ProblemvalueId + "&creatorId=" + userId + "&flower=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemvalueUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemvalueBadegg = function (problemId, ProblemvalueId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=problemvalueUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation&problemvalueId=" + ProblemvalueId + "&creatorId=" + userId + "&badegg=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemvalueUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemvalueOverdue = function (problemId, ProblemvalueId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=problemvalueUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation&problemvalueId=" + ProblemvalueId + "&creatorId=" + userId + "&overdue=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemvalueUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemvalueConfirm = function (problemId, ProblemvalueId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=problemvalueUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation&problemvalueId=" + ProblemvalueId + "&creatorId=" + userId + "&confirm=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemvalueUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemvalueReject = function (problemId, ProblemvalueId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=problemvalueUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation&problemvalueId=" + ProblemvalueId + "&creatorId=" + userId + "&confirm=n";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemvalueUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemvalueUpdateResult = function () {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var response = xmlHttp.responseXML;
				if ("success" == response.getElementsByTagName("message")[0].firstChild.nodeValue) {
					if ("flower" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemvalueId = response.getElementsByTagName("problemvalueId")[0].firstChild.nodeValue;
						document.getElementById("problemvalue_flower" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_badegg" + problemvalueId).style.display = "none";
						var temp = document.getElementById("problemvalue_flowernum" + problemvalueId).innerHTML;
						var good_num = Number(temp);
						document.getElementById("problemvalue_flowernum" + problemvalueId).innerHTML = good_num + 1;
					}
					if ("badegg" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemvalueId = response.getElementsByTagName("problemvalueId")[0].firstChild.nodeValue;
						document.getElementById("problemvalue_badegg" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_flower" + problemvalueId).style.display = "none";
						var temp = document.getElementById("problemvalue_badeggnum" + problemvalueId).innerHTML;
                        //alert("221");
						var bad_num = Number(temp);
						document.getElementById("problemvalue_badeggnum" + problemvalueId).innerHTML = bad_num + 1;
					}
					if ("overdue" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemvalueId = response.getElementsByTagName("problemvalueId")[0].firstChild.nodeValue;
						document.getElementById("problemvalue_overdue" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_overdue2" + problemvalueId).style.display = "block";
					}
					if ("confirm" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemvalueId = response.getElementsByTagName("problemvalueId")[0].firstChild.nodeValue;
						document.getElementById("problemvalue_confirm1" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_reject1" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_confirm2" + problemvalueId).style.display = "block";
					}
					if ("reject" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemvalueId = response.getElementsByTagName("problemvalueId")[0].firstChild.nodeValue;
						document.getElementById("problemvalue_confirm1" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_reject1" + problemvalueId).style.display = "none";
						document.getElementById("problemvalue_reject2" + problemvalueId).style.display = "block";
					}
				}
			}
		}
	};
	 //对于problemreason的评价部分
	Problem.problemreasonFlower = function (problemId, ProblemreasonId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=problemreasonUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation&problemreasonId=" + ProblemreasonId + "&creatorId=" + userId + "&flower=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemreasonUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemreasonBadegg = function (problemId, ProblemreasonId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=problemreasonUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation&problemreasonId=" + ProblemreasonId + "&creatorId=" + userId + "&badegg=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemreasonUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemreasonOverdue = function (problemId, ProblemreasonId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=problemreasonUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation&problemreasonId=" + ProblemreasonId + "&creatorId=" + userId + "&overdue=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemreasonUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemreasonConfirm = function (problemId, ProblemreasonId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=problemreasonUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation&problemreasonId=" + ProblemreasonId + "&creatorId=" + userId + "&confirm=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemreasonUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemreasonReject = function (problemId, ProblemreasonId, userId) {
		var xmlHttp = Problem.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=problemreasonUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation&problemreasonId=" + ProblemreasonId + "&creatorId=" + userId + "&confirm=n";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problem.problemreasonUpdateResult;
		xmlHttp.send(null);
	};
	Problem.problemreasonUpdateResult = function () {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var response = xmlHttp.responseXML;
				if ("success" == response.getElementsByTagName("message")[0].firstChild.nodeValue) {
					if ("flower" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemreasonId = response.getElementsByTagName("problemreasonId")[0].firstChild.nodeValue;
						document.getElementById("problemreason_flower" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_badegg" + problemreasonId).style.display = "none";
						var temp = document.getElementById("problemreason_flowernum" + problemreasonId).innerHTML;
						var good_num = new Number(temp);
						document.getElementById("problemreason_flowernum" + problemreasonId).innerHTML = good_num + 1;
					}
					if ("badegg" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemreasonId = response.getElementsByTagName("problemreasonId")[0].firstChild.nodeValue;
						document.getElementById("problemreason_badegg" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_flower" + problemreasonId).style.display = "none";
						var temp = document.getElementById("problemreason_badeggnum" + problemreasonId).innerHTML;
						var bad_num = new Number(temp);
						document.getElementById("problemreason_badeggnum" + problemreasonId).innerHTML = bad_num + 1;
					}
					if ("overdue" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemreasonId = response.getElementsByTagName("problemreasonId")[0].firstChild.nodeValue;
						document.getElementById("problemreason_overdue" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_overdue2" + problemreasonId).style.display = "block";
					}
					if ("confirm" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemreasonId = response.getElementsByTagName("problemreasonId")[0].firstChild.nodeValue;
						document.getElementById("problemreason_confirm1" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_reject1" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_confirm2" + problemreasonId).style.display = "block";
					}
					if ("reject" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemreasonId = response.getElementsByTagName("problemreasonId")[0].firstChild.nodeValue;
						document.getElementById("problemreason_confirm1" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_reject1" + problemreasonId).style.display = "none";
						document.getElementById("problemreason_reject2" + problemreasonId).style.display = "block";
					}
				}
			}
		}
	};
	Problem.viewAllAmbiguity = function (problemId) {
		if (document.getElementById("viewAllProblemvalue") != null) {
			document.getElementById("viewAllProblemvalue").style.display = "none";
		}
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=viewAllAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&group=false&good_num=false&ajax=false", "#viewAll", "", false);
		document.getElementById("viewAll").style.display = "block";
	};
	Problem.viewAllAmbiguityChoose = function (problemId, gruop, good_num) {
		if (document.getElementById("viewAllProblemvalue") != null) {
			document.getElementById("viewAllProblemvalue").style.display = "none";
		}
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=viewAllAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&group=" + gruop + "&good_num=" + good_num + "&ajax=true", "#viewAll", "", false);
		document.getElementById("viewAll").style.display = "block";
	};
	Problem.viewAllAmbiguityNew = function (problemId, ifNewWindow) {
		Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=viewAllAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&ifNewWindow=" + ifNewWindow + "&group=false&good_num=false&ajax=false", "#columnMain", "", false);
	};
	Problem.orderByGroup = function (problemId) {
		var good_num = document.getElementById("order22").innerHTML;
		if (document.getElementById("order11").innerHTML == "true") {
			Problem.viewAllAmbiguityChoose(problemId, false, good_num);
			document.getElementById("order1").innerHTML = "\u6309\u5206\u7c7b\u6392\u5e8f";
			document.getElementById("order11").innerHTML = "false";
		} else {
			if (document.getElementById("order11").innerHTML == "false") {
				Problem.viewAllAmbiguityChoose(problemId, true, good_num);
				document.getElementById("order1").innerHTML = "\u53d6\u6d88\u5206\u7c7b\u6392\u5e8f";
				document.getElementById("order11").innerHTML = "true";
			}
		}
	};
	Problem.orderByGoodNum = function (problemId) {
		var group = document.getElementById("order11").innerHTML;
		if (document.getElementById("order22").innerHTML == "true") {
			Problem.viewAllAmbiguityChoose(problemId, group, false);
			document.getElementById("order2").innerHTML = "\u6309\u597d\u8bc4\u5347\u5e8f";
			document.getElementById("order22").innerHTML = "false";
		} else {
			if (document.getElementById("order22").innerHTML == "false") {
				Problem.viewAllAmbiguityChoose(problemId, group, true);
				document.getElementById("order2").innerHTML = "\u6309\u597d\u8bc4\u964d\u5e8f";
				document.getElementById("order22").innerHTML = "true";
			}
		}
	};
	Problem.viewAllProblemvalue = function (problemId) {
		if (document.getElementById("viewAll") != null) {
			document.getElementById("viewAll").style.display = "none";
		}
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=viewAllProblemvalue&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemvalue&problemId=" + problemId + "&group=false&good_num=false&ajax=false", "#viewAllProblemvalue", "", false);
		document.getElementById("viewAllProblemvalue").style.display = "block";
	};
	Problem.viewAllProblemvalueNew = function (problemId, ifNewWindow) {
		Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=viewAllProblemvalue&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemvalue&problemId=" + problemId + "&ifNewWindow=" + ifNewWindow + "&group=false&good_num=false&ajax=false", "#columnMain", "", false);
	};
	Problem.viewAllProblemvalueChoose = function (problemId, gruop, good_num) {
		if (document.getElementById("viewAll") != null) {
			document.getElementById("viewAll").style.display = "none";
		}
		document.getElementById("value_more").style.display = "block";
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=viewAllProblemvalue&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemvalue&problemId=" + problemId + "&group=" + gruop + "&good_num=" + good_num + "&ajax=true", "#viewAllProblemvalue", "", false);
		document.getElementById("viewAllProblemvalue").style.display = "block";
		document.getElementById("problemvalue").style.display = "block";
	};
	Problem.problemvalueOrderByGroup = function (problemId) {
		var good_num = document.getElementById("problemvalue_order22").innerHTML;
		if (document.getElementById("problemvalue_order11").innerHTML == "true") {
			Problem.viewAllProblemvalueChoose(problemId, false, good_num);
			document.getElementById("problemvalue_order1").innerHTML = "\u6309\u5206\u7c7b\u6392\u5e8f";
			document.getElementById("problemvalue_order11").innerHTML = "false";
		} else {
			if (document.getElementById("problemvalue_order11").innerHTML == "false") {
				Problem.viewAllProblemvalueChoose(problemId, true, good_num);
				document.getElementById("problemvalue_order1").innerHTML = "\u53d6\u6d88\u5206\u7c7b\u6392\u5e8f";
				document.getElementById("problemvalue_order11").innerHTML = "true";
			}
		}
	};
	Problem.problemvalueOrderByGoodNum = function (problemId) {
		var group = document.getElementById("problemvalue_order11").innerHTML;
		if (document.getElementById("problemvalue_order22").innerHTML == "true") {
			Problem.viewAllProblemvalueChoose(problemId, group, false);
			document.getElementById("problemvalue_order2").innerHTML = "\u6309\u597d\u8bc4\u5347\u5e8f";
			document.getElementById("problemvalue_order22").innerHTML = "false";
		} else {
			if (document.getElementById("problemvalue_order22").innerHTML == "false") {
				Problem.viewAllProblemvalueChoose(problemId, group, true);
				document.getElementById("problemvalue_order2").innerHTML = "\u6309\u597d\u8bc4\u964d\u5e8f";
				document.getElementById("problemvalue_order22").innerHTML = "true";
			}
		}
	};
	Problem.detailedSolution = function (problemSolutionId) {
		Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=viewDetailedSolution&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemsolution&problemsolutionId=" + problemSolutionId + "&ajax=false", "#columnMain", "", false);
	};
	//显示problemreason的排序
	Problem.viewAllProblemreason = function (problemId) {
		if (document.getElementById("viewAll") != null) {
			document.getElementById("viewAll").style.display = "none";
		}
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=viewAllProblemreason&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemreason&problemId=" + problemId + "&good_num=false&ajax=false", "#viewAllProblemreason", "", false);
		document.getElementById("viewAllProblemreason").style.display = "block";
	};
	Problem.viewAllProblemreasonChoose = function (problemId, good_num) {
		if (document.getElementById("viewAll") != null) {
			document.getElementById("viewAll").style.display = "none";
		}
		Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=viewAllProblemreason&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemreason&problemId=" + problemId + "&good_num=" + good_num + "&ajax=true", "#viewAllProblemreason", "", false);
		document.getElementById("viewAllProblemreason").style.display = "block";
	};
	Problem.viewAllProblemreasonNew = function (problemId, ifNewWindow) {
		Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=viewAllProblemreason&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemreason&problemId=" + problemId + "&ifNewWindow=" + ifNewWindow + "&group=false&good_num=false&ajax=false", "#columnMain", "", false);
	};
	Problem.problemreasonOrderByGoodNum = function (problemId) {
		if (document.getElementById("problemreason_order22").innerHTML == "true") {
			Problem.viewAllProblemreasonChoose(problemId, false);
			document.getElementById("problemreason_order2").innerHTML = "\u6309\u597d\u8bc4\u5347\u5e8f";
			document.getElementById("problemreason_order22").innerHTML = "false";
		} else {
			if (document.getElementById("problemreason_order22").innerHTML == "false") {
				Problem.viewAllProblemreasonChoose(problemId, true);
				document.getElementById("problemreason_order2").innerHTML = "\u6309\u597d\u8bc4\u964d\u5e8f";
				document.getElementById("problemreason_order22").innerHTML = "true";
			}
		}
	};
	Problem.createAmbiguityDiscuss = function (problemId) {
		document.getElementById("createAmbiguityDiscuss").innerHTML = "";
		var rts = "";
		rts += "<TR><TD><INPUT type=\"checkbox\" onclick=\"Problem.chooseAmbiguity(0)\" value=" + problemId + " name=\"ambiguity_type0\">\u6709\u6b67\u4e49</TD>";
		rts += "<TR><TD><INPUT type=\"checkbox\" onclick=\"Problem.chooseAmbiguity(1)\" value=" + problemId + " name=\"ambiguity_type1\">\u4e0d\u6613\u7406\u89e3</TD>";
		rts += "<TR><TD><INPUT type=\"checkbox\" onclick=\"Problem.chooseAmbiguity(2)\" value=" + problemId + " name=\"ambiguity_type2\">\u6613\u7406\u89e3</TD></TR><BR>";
		rts += "<textarea name=\"ambiguityContent\" id = \"ambiguityContent\" cols=\"45%\" rows=\"6\" ></textarea><br>";
		rts += "<a href=\"javascript:Problem.creatingAmbiguityDiscuss(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868</a>&nbsp;";
		rts += "<a href=\"javascript:Problem.hide('createAmbiguityDiscuss')\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u5173\u95ed</a>&nbsp;";
		document.getElementById("createAmbiguityDiscuss").innerHTML = rts;
	};
	Problem.chooseAmbiguity = function (choose) {
		var chbox0 = document.getElementsByName("ambiguity_type0");
		var chbox1 = document.getElementsByName("ambiguity_type1");
		var chbox2 = document.getElementsByName("ambiguity_type2");
		if (choose == "0") {
			chbox1[0].checked = false;
			chbox2[0].checked = false;
		}
		if (choose == "1") {
			chbox0[0].checked = false;
			chbox2[0].checked = false;
		}
		if (choose == "2") {
			chbox1[0].checked = false;
			chbox0[0].checked = false;
		}
	};
	Problem.creatingAmbiguityDiscuss = function (problemId) {
		var content = document.getElementById("ambiguityContent").innerText;
		var chbox0 = document.getElementsByName("ambiguity_type0");
		var chbox1 = document.getElementsByName("ambiguity_type1");
		var chbox2 = document.getElementsByName("ambiguity_type2");
		if (chbox0[0].checked == true || chbox1[0].checked == true || chbox2[0].checked == true) {
			if (document.getElementById("ambiguityContent").innerHTML == "") {
				alert("\u8bc4\u8bba\u4e0d\u80fd\u4e3a\u7a7a\uff01");
			} else {
				Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=creatingAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&ajax=false", "#form1", "");
				Problem.hide("createAmbiguityDiscuss");
				Problem.discussProblemAmbiguity(problemId);
			}
		} else {
			alert("\u8bf7\u9009\u62e9\u95ee\u9898\u7c7b\u578b!");
		}
	};
	
	Problem.createAmbiguityDiscussNewWindow = function (problemId) {
		var content = document.getElementById("ambiguityContent").innerText;
		var chbox0 = document.getElementsByName("ambiguity_type0");
		var chbox1 = document.getElementsByName("ambiguity_type1");
		var chbox2 = document.getElementsByName("ambiguity_type2");
		if (chbox0[0].checked == true || chbox1[0].checked == true || chbox2[0].checked == true) {
			if (document.getElementById("ambiguityContent").innerHTML == "") {
				alert("\u8bc4\u8bba\u4e0d\u80fd\u4e3a\u7a7a\uff01");
			} else {
				Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=creatingAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&ajax=false", "#form1", "");
				Problem.hide("createAmbiguityDiscuss");
				Ajax.submitLoadWithoutHistory("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=viewAllAmbiguity&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&problemId=" + problemId + "&ifNewWindow=" + ifNewWindow + "&group=false&good_num=false&ajax=false", "#columnMain", "", false);
	
			}
		} else {
			alert("\u8bf7\u9009\u62e9\u95ee\u9898\u7c7b\u578b!");
		}
	};
	Problem.createProblemReasonDiscuss = function (problemId) {
		document.getElementById("createProblemReasonDiscuss").innerHTML = "";
		var rts = "";
		rts += "<textarea name=\"problemreasonContent\" id = \"problemreasonContent\" cols=\"45%\" rows=\"6\" ></textarea><br>";
		rts += "<a href=\"javascript:Problem.creatingProblemReasonDiscuss(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868</a>&nbsp;";
		rts += "<a href=\"javascript:Problem.hide('createProblemReasonDiscuss')\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u5173\u95ed</a>&nbsp;";
		document.getElementById("createProblemReasonDiscuss").innerHTML = rts;
	};
	Problem.creatingProblemReasonDiscuss = function (problemId) {
		var content = document.getElementById("problemreasonContent").innerText;
		if (content != null) {
			Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=creatingProblemReason&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemreason&problemId=" + problemId + "&ajax=false", "#form1", "");
		} else {
			alert("\u8bf7\u8f93\u5165\u5185\u5bb9\uff01\uff01\uff01");
		}
		Problem.hide("problemreasonContent");
		Problem.discussProblemReason(problemId);
	};
	Problem.createProblemValueDiscuss = function (problemId) {
		document.getElementById("createProblemValueDiscuss").innerHTML = "";
		var rts = "";
		rts += "<TR><TD><INPUT type=\"checkbox\" onclick=\"Problem.chooseProblemValue(0)\" value=" + problemId + " name=\"problemavalue_type0\">\u65e0\u4ef7\u503c</TD>";
		rts += "<TR><TD><INPUT type=\"checkbox\" onclick=\"Problem.chooseProblemValue(1)\" value=" + problemId + " name=\"problemavalue_type1\">\u5177\u6709\u4e00\u822c\u4ef7\u503c</TD>";
		rts += "<TR><TD><INPUT type=\"checkbox\" onclick=\"Problem.chooseProblemValue(2)\" value=" + problemId + " name=\"problemavalue_type2\">\u975e\u5e38\u6709\u4ef7\u503c</TD></TR><BR>";
		rts += "<textarea name=\"problemvalueContent\" id = \"problemvalueContent\" cols=\"45%\" rows=\"6\" ></textarea><br>";
		rts += "<a href=\"javascript:Problem.creatingProblemValueDiscuss(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868</a>&nbsp;";
		rts += "<a href=\"javascript:Problem.hide('createProblemValueDiscuss')\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u5173\u95ed</a>&nbsp;";
		document.getElementById("createProblemValueDiscuss").innerHTML = rts;
	};
	Problem.chooseProblemValue = function (choose) {
		var chbox0 = document.getElementsByName("problemavalue_type0");
		var chbox1 = document.getElementsByName("problemavalue_type1");
		var chbox2 = document.getElementsByName("problemavalue_type2");
		if (choose == "0") {
			chbox1[0].checked = false;
			chbox2[0].checked = false;
		}
		if (choose == "1") {
			chbox0[0].checked = false;
			chbox2[0].checked = false;
		}
		if (choose == "2") {
			chbox1[0].checked = false;
			chbox0[0].checked = false;
		}
	};
	Problem.creatingProblemValueDiscuss = function (problemId) {
		var content = document.getElementById("problemvalueContent").innerText;
		var chbox0 = document.getElementsByName("problemavalue_type0");
		var chbox1 = document.getElementsByName("problemavalue_type1");
		var chbox2 = document.getElementsByName("problemavalue_type2");
		if (chbox0[0].checked == true || chbox1[0].checked == true || chbox2[0].checked == true) {
			if (document.getElementById("problemvalueContent").innerHTML == "") {
				alert("\u8bc4\u8bba\u4e0d\u80fd\u4e3a\u7a7a\uff01");
			} else {
				Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=creatingProblemValue&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemvalue&problemId=" + problemId + "&ajax=false", "#form1", "");
				Problem.hide("problemvalueContent");
				Problem.discussProblemValue(problemId);
			}
		} else {
			alert("\u8bf7\u9009\u62e9\u95ee\u9898\u7c7b\u578b!");
		}
	};
	Problem.hide = function (obj, problemId, content) {
		var rts = "";
		if (obj == "createAmbiguityDiscuss") {
			rts = "<div id=\"createAmbiguityDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createAmbiguityDiscuss(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868\u8bc4\u8bba</a></div>";
			document.getElementById("createAmbiguityDiscuss").innerHTML = rts;
		} else {
			if (obj == "createProblemReasonDiscuss") {
				rts = "<div id=\"createProblemReasonDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createProblemReasonDiscuss(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868\u8bc4\u8bba</a></div>";
				document.getElementById("createProblemReasonDiscuss").innerHTML = rts;
			} else {
				if (obj == "createProblemValueDiscuss") {
					rts = "<div id=\"createProblemValueDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createProblemValueDiscuss(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868\u8bc4\u8bba</a></div>";
					document.getElementById("createProblemValueDiscuss").innerHTML = rts;
				}
			}
		}
	};
})();

