
var Problemsolution = {};
(function () {
	Problemsolution.showHide = function (_obj) {
	
		if (document.getElementById(_obj).style.display == "none") {
			document.getElementById(_obj).style.display = "block";
			
		} else {
			document.getElementById(_obj).style.display = "none";
		}
	};
	Problemsolution.createXMLHttprequest = function () {
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else {
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return xmlHttp;
	};

    
    //对于problemsolution的评价部分
	Problemsolution.problemsolutionFlower = function (problemId, ProblemsolutionId, userId, useState) {
		var xmlHttp = Problemsolution.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation&problemsolutionId=" + ProblemsolutionId + "&creatorId=" + userId + "&flower=y&useState=" + useState;
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problemsolution.problemsolutionUpdateResult;
		xmlHttp.send(null);
	};
	Problemsolution.problemsolutionBadegg = function (problemId, ProblemsolutionId, userId, useState) {
		var xmlHttp = Problemsolution.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation&problemsolutionId=" + ProblemsolutionId + "&creatorId=" + userId + "&badegg=y&useState=" + useState;
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problemsolution.problemsolutionUpdateResult;
		xmlHttp.send(null);
	};
	Problemsolution.problemsolutionOverdue = function (problemId, ProblemsolutionId, userId) {
	
		var xmlHttp = Problemsolution.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation&problemsolutionId=" + ProblemsolutionId + "&creatorId=" + userId + "&overdue=y";
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problemsolution.problemsolutionUpdateResult;
		xmlHttp.send(null);
	};
	Problemsolution.problemsolutionVoting = function (ProblemsolutionId, decision) {
		var xmlHttp = Problemsolution.createXMLHttprequest();
		var url = "cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=problemsolutionUpdate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation&problemsolutionId=" + ProblemsolutionId + "&decision="+decision;
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = Problemsolution.problemsolutionUpdateResult;
		xmlHttp.send(null);
	};
	Problemsolution.problemsolutionUpdateResult = function () {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var response = xmlHttp.responseXML;
				if ("success" == response.getElementsByTagName("message")[0].firstChild.nodeValue) {
					if ("flower" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemsolutionId = response.getElementsByTagName("problemsolutionId")[0].firstChild.nodeValue;
						document.getElementById("problemsolution_flower" + problemsolutionId).style.display = "none";
						document.getElementById("problemsolution_badegg" + problemsolutionId).style.display = "none";
						var temp = document.getElementById("problemsolution_flowernum" + problemsolutionId).innerHTML;
						var good_num = Number(temp);
						document.getElementById("problemsolution_flowernum" + problemsolutionId).innerHTML = good_num + 1;
					}
					if ("badegg" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemsolutionId = response.getElementsByTagName("problemsolutionId")[0].firstChild.nodeValue;
						document.getElementById("problemsolution_badegg" + problemsolutionId).style.display = "none";
						document.getElementById("problemsolution_flower" + problemsolutionId).style.display = "none";
						var temp = document.getElementById("problemsolution_badeggnum" + problemsolutionId).innerHTML;
                        //alert("221");
						var bad_num = Number(temp);
						document.getElementById("problemsolution_badeggnum" + problemsolutionId).innerHTML = bad_num + 1;
					}
					if ("overdue" == response.getElementsByTagName("choose")[0].firstChild.nodeValue) {
						var problemsolutionId = response.getElementsByTagName("problemsolutionId")[0].firstChild.nodeValue;
						document.getElementById("problemsolution_overdue" + problemsolutionId).style.display = "none";
						document.getElementById("problemsolution_overdue2" + problemsolutionId).style.display = "block";
					}
					
					
				}
			}
		}
	};
	Problemsolution.detailedSolution = function (problemSolutionId) {
		Problemsolution.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=viewDetailedSolution&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemsolution&problemsolutionId=" + problemSolutionId + "&ajax=false", "#detailedSolution", "", false);
	};
	Problemsolution.loadNewSolution = function (problemId) {
		
		Problemsolution.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=createSolution&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemsolution&problemId=" + problemId + "&ajax=false", "#columnMain", "", false);
	};
	Problemsolution.newsHide = function (problemId) {
		document.getElementById("modify_problem").style.display = "none";
		document.getElementById("ambiguity_list").style.display = "none";
		document.getElementById("modify_problem_button").innerHTML = "<a href=\"javascript:Problem.modifyProblem(" + problemId + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">\u4fee\u6539\u95ee\u9898</a>";
	};
	Problemsolution.newsShowHide = function (_obj) {
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
	Problemsolution.changeTRbg = function (_self, _obj) {
		obj = document.getElementById(_obj);
		if (_self.checked) {
			obj.className = "BG3";
		} else {
			obj.className = "";
			checkall.checked = false;
		}
	};
	Problemsolution.checkAll = function (status) {
		var chbox = document.getElementsByName("choose_reason");
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
	Problemsolution.submitSolution = function(problemId){
		Problemsolution.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=creatingSolution&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemsolution&problemId=" + problemId + "&ajax=true", '#form2', '');
            
	};
		Problemsolution.submit = function (actionPath, formId, data) {
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
			if (message[0] == "create_solution_success") {
				alert("发表成功！");
			} else {
				if (message[0] == "create_solution_failure") {
					alert(message[0]);
				}
			}
		}});
		return value;
	};
	    Problemsolution.submitLoad = function (actionPath, htmlComponent, formId,data,syn){
      var returnMsg;
        var formData = [];
        if (formId != '')
            formData = $(formId).formToArray();
      if (data) {
              for (var option in data) {
                      formData.push({
                              name: option,
                              value: data[option]
                      });
              }
      }
      if(syn!=null)
      {
        $.ajax({
            async:syn,
            type: "POST",
            url: actionPath,
            data: formData,
            success: function(msg){
                returnMsg=msg;
                if(htmlComponent){
                  $(htmlComponent).empty();
                  $(htmlComponent).append(msg);
                 // Ajax.saveHistory(msg);
                }
            }
        });
      }else
      {
        $.ajax({
            type: "POST",
            url: actionPath,
            data: formData,
            success: function(msg){
                returnMsg=msg;
                if(htmlComponent){
                  $(htmlComponent).empty();
                  $(htmlComponent).append(msg);
                 // Ajax.saveHistory(msg);
                }
            }
        });
      }
      return returnMsg;
    };
})();

