
var ProblemsolutionReply = {};
(function () {
	ProblemsolutionReply.createXMLHttprequest = function () {
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else {
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return xmlHttp;
	};

    //对于problemsolutionReply的评价部分
	ProblemsolutionReply.problemsolutionreplyFlower = function (ProblemsolutionId, ProblemsolutionReplyId, userId) {
		var xmlHttp = ProblemsolutionReply.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionReplyUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation&problemsolutionReplyId=" + ProblemsolutionReplyId + "&creatorId=" + userId + "&flower=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = ProblemsolutionReply.problemsolutionReplyUpdateResult;
		xmlHttp.send(null);
	};
	ProblemsolutionReply.problemsolutionreplyBadegg = function (ProblemsolutionId, ProblemsolutionReplyId, userId) {
		var xmlHttp = Problemsolution.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionReplyUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation&problemsolutionReplyId=" + ProblemsolutionReplyId + "&creatorId=" + userId + "&badegg=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = ProblemsolutionReply.problemsolutionReplyUpdateResult;
		xmlHttp.send(null);
	};
	ProblemsolutionReply.problemsolutionreplyOverdue = function (ProblemsolutionId, ProblemsolutionReplyId, userId) {
		var xmlHttp = Problemsolution.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionReplyUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation&problemsolutionReplyId=" + ProblemsolutionReplyId + "&creatorId=" + userId + "&overdue=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = ProblemsolutionReply.problemsolutionReplyUpdateResult;
		xmlHttp.send(null);
	};
	ProblemsolutionReply.problemsolutionReplyUpdateResult = function () {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var response = xmlHttp.responseXML;
				if ("success" == response.getElementsByTagName("message")[0].firstChild.nodeValue) {
					if ("flower" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemsolutionReplyId = response.getElementsByTagName("problemsolutionReplyId")[0].firstChild.nodeValue;
						document.getElementById("problemsolutionreply_flower" + problemsolutionReplyId).style.display = "none";
						document.getElementById("problemsolutionreply_badegg" + problemsolutionReplyId).style.display = "none";
						var temp = document.getElementById("problemsolutionreply_flowernum" + problemsolutionReplyId).innerHTML;
						var good_num = Number(temp);
						document.getElementById("problemsolutionreply_flowernum" + problemsolutionReplyId).innerHTML = good_num + 1;
					}
					if ("badegg" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemsolutionReplyId = response.getElementsByTagName("problemsolutionReplyId")[0].firstChild.nodeValue;
						document.getElementById("problemsolutionreply_badegg" + problemsolutionReplyId).style.display = "none";
						document.getElementById("problemsolutionreply_flower" + problemsolutionReplyId).style.display = "none";
						var temp = document.getElementById("problemsolutionreply_badeggnum" + problemsolutionReplyId).innerHTML;
						var bad_num = Number(temp);
						document.getElementById("problemsolutionreply_badeggnum" + problemsolutionReplyId).innerHTML = bad_num + 1;
					}
					if ("overdue" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemsolutionReplyId = response.getElementsByTagName("problemsolutionReplyId")[0].firstChild.nodeValue;
						document.getElementById("problemsolutionreply_overdue" + problemsolutionReplyId).style.display = "none";
						document.getElementById("problemsolutionreply_overdue2" + problemsolutionReplyId).style.display = "block";
					}
				}
			}
		}
	};
	ProblemsolutionReply.createReply = function (problemsolutionId, referenceId, choose) {
		document.getElementById("createReply").innerHTML = "";
		document.getElementById("createReply_button").style.display = "none";
		document.getElementById("createReply_button1").style.display = "none";
		//在createReply里面提供一个供用户描述的textarea
		var rts = "";
		rts += "<textarea name=\"problemsolutionReplyContent\" id = \"problemsolutionReply\" cols=\"45%\" rows=\"6\" ></textarea>";
		document.getElementById("createReply").innerHTML = rts;
        //将现有的没有标为失效的ambuity写入ambiguity_list
		rts = "";
		rts += "<a href=\"javascript:ProblemsolutionReply.creatingReply(" + problemsolutionId + "," + referenceId + ",'" + choose + "')\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u53d1\u8868</a>&nbsp;";
		rts += "<a href=\"javascript:ProblemsolutionReply.hide()\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u5173\u95ed</a>&nbsp;";
		document.getElementById("createReply_button").innerHTML = rts;
		document.getElementById("createReply").style.display = "block";
		document.getElementById("createReply_button").style.display = "block";
	};
	ProblemsolutionReply.creatingReply = function (problemsolutionId, referenceId, choose) {
		//alert(document.getElementById("problemsolutionReplyContent").innerHTML);
		if (document.getElementById("problemsolutionReply").innerText == "") {
			alert("\u56de\u590d\u4e0d\u80fd\u4e3a\u7a7a");
		} else {
			ProblemsolutionReply.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=creatingReply&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReply&problemsolutionId=" + problemsolutionId + "&referenceId=" + referenceId + "&choose=" + choose + "&ajax=true", "#form1", "");
			ProblemsolutionReply.hide();
			//Problem.discussProblemAmbiguity(problemId);
		}
	};
	ProblemsolutionReply.hide = function () {
		document.getElementById("createReply").style.display = "none";
		document.getElementById("createReply_button").style.display = "none";
		document.getElementById("createReply_button1").style.display = "block";
	};
	ProblemsolutionReply.submit = function (actionPath, formId, data) {
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
                
            
                
                //create_reply
			if (message[0] == "create_reply_success") {
				alert("\u53d1\u8868\u6210\u529f\uff01");
			} else {
				if (message[0] == "create_reply_failure") {
					alert(message[0]);
				}
			}
		}});
		return value;
	};
})();

