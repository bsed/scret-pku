
var Message = {};
(function(){
	Message.getMessageTemplate=function(id){
		Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=getMessageTemplate&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&ajax=true",'#columnMain','',false);
	Message.getMessage(id);
	}
	Message.getMessage=function(id){
			
	
		//document.getElementById("columnMain").style.position="absolute";
		//document.getElementById("columnMain").style.width="80%";
		
		//处理得到好友信息
		if(id==1){
				
		
			document.getElementById("page").innerText="getFriendsMessage";
			var problemDiscuss0=document.getElementById("0");
        	problemDiscuss0.className="bgactive";
			var problemDiscuss2=document.getElementById("2");
        	problemDiscuss2.className="subitema";
			var problemDiscuss3=document.getElementById("3");
        	problemDiscuss3.className="subitema";
			
			Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=getMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&id="+id+"&ajax=true",'#messages','',false);
			
			if(document.getElementById("writemessage")){
				var writemessage = document.getElementById("writemessage");
				writemessage.innerHTML='<a href="javascript:Message.writeAMessage()" style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">发送新消息</a>&nbsp;';	
			}
			
		}
		//处理得到系统信息
		else if(id==2){
			document.getElementById("page").innerText="getSysMessage";
			var problemDiscuss0=document.getElementById("0");
        	problemDiscuss0.className="subitema";
			var problemDiscuss2=document.getElementById("2");
        	problemDiscuss2.className="bgactive";
			var problemDiscuss3=document.getElementById("3");
        	problemDiscuss3.className="subitema";
			Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=getMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&id="+id+"&ajax=true",'#messages','',false);
			
			var writemessage = document.getElementById("writemessage");
			writemessage.innerHTML=" ";
			var bdWaring = document.getElementById("bdWaring");
			/*bdWaring.setAttribute("style", "MARGIN-LEFT:8px;");*/
			bdWaring.style.cssText='MARGIN-LEFT:8px';
		}
		//处理得到已发送信息
		else{
			var problemDiscuss0=document.getElementById("0");
        	problemDiscuss0.className="subitema";
			var problemDiscuss2=document.getElementById("2");
        	problemDiscuss2.className="subitema";
			var problemDiscuss3=document.getElementById("3");
        	problemDiscuss3.className="bgactive";
			document.getElementById("page").innerText="getSentMessage";
			Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=getMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&id="+id+"&ajax=true",'#messages','',false);
			
			if(document.getElementById("writemessage")){
				var writemessage = document.getElementById("writemessage");
				writemessage.innerHTML='<a href="javascript:Message.writeAMessage()" style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">发送新消息</a>&nbsp;';	
			}
			
		}
	}
		  

	Message.writeAMessage = function(){
		writemessage.innerHTML=" ";
		var rts = "";
		rts += "<div class=\"rt\" id=\"textarea\">";
		rts += "<textarea name=\"userName\" id = \"userName\" cols=\"20%\" rows=\"1\" ></textarea>请输入收件人id<br>";
		rts += "<textarea name=\"messageTitle\" id = \"messageTitle\" cols=\"60%\" rows=\"1\" ></textarea>100汉字以内<br>";
		rts += "<textarea name=\"messageContent\" id = \"messageContent\" cols=\"75%\" rows=\"6\" ></textarea><br>1000汉字(或2000字符)以内<br>";
		rts += '<a href="javascript:Message.writeMessageToFriend()"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">发送</a>&nbsp;';
		//rts += "<button onClick=\"Message.writeMessageToFriend()\" >发送</button>";
		rts += '<a href="javascript:Message.newsHide()"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">关闭</a>&nbsp;';
		rts += "</div>"
		document.getElementById("writemessage").innerHTML = rts;
		
	}
	Message.newsHide=function(){
		document.getElementById("writemessage").innerHTML = '<a href="javascript:Message.writeAMessage()" style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">发送新消息</a>&nbsp;';
	}
	
	Message.writeMessageToFriend=function(){
		Message.submit("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=writeMessageToFriend&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&ajax=true",'#form1','');
		var page = document.getElementById("page").innerText;
		if(page=="getFriendsMessage"){
		Message.getMessage(1);	
		}
		else if(page=="getSysMessage")
		{
		Message.getMessage(2);
		}
		else if(page=="getSentMessage")
		{
			
		Message.getMessage(3);
		}	
		
	}
		  
	Message.newsShowHide = function(_obj){
		var newsTD=document.getElementsByTagName("td");
		var obj=document.getElementById(_obj);
		if(obj.style.display=="none"){
			for(i=0;i<newsTD.length;i++){	
				if(newsTD[i].id.indexOf("news")!=-1){
					newsTD[i].style.display="none";
				}
			}
		obj.style.display="";
		}
		else if(obj.style.display!="none"){
			obj.style.display="none";
		}
	}
		 
	Message.showMSG = function(order,id){
		document.getElementById("MSGcontent" + order).innerHTML="Loading...";
    	Message.loadMailContent(order,id);
	}
	Message.setRead = function(messageId){
	Message.submit("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=setRead&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&ajax=true&messageId="+messageId,'','');
		
	}
	
	Message.loadMailContent= function(order,id){
		if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
		}	
		else if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlHttp.onreadystatechange = Message.getMailContent;
		try{
		xmlHttp.open("GET", "cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=getMessageContent&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&messageId="+id+"&MSGcontentId="+order, true);
		xmlHttp.send(null);
		} 
		catch(e)
		{
		}
	
	}
	
	
	Message.getMailContent= function(){
		
		if (xmlHttp.readyState == 4)
	 {
	 	if (xmlHttp.status==200)
	 	{
			var response = xmlHttp.responseXML;
			var MSGcontentId = response.getElementsByTagName("MSGcontentId")[0].firstChild.nodeValue;
			var messageContent = response.getElementsByTagName("content")[0].firstChild.nodeValue;
			if("noreference"==response.getElementsByTagName("recontent")[0].firstChild.nodeValue){
				document.getElementById("MSGcontent" + MSGcontentId).innerHTML=messageContent;
			}
			else
			{
				var reContent = response.getElementsByTagName("recontent")[0].firstChild.nodeValue
				document.getElementById("MSGcontent" + MSGcontentId).innerHTML=messageContent+"<br>=====你回复了=====<br>"+reContent;
			
			}
			

		}
	}	
	}
	
	
	Message.reply = function(_obj,id,userId,title,receiver){
		var rts = "";
		rts += "<div class=\"rt\" id=\"textarea\">";
		rts += "<textarea name=\"messageTitle\" id = \"messageTitle\" cols=\"60%\" rows=\"1\" >回复:"+title+"</textarea>100汉字以内<br>";
		rts += "<textarea name=\"messageContent\" id = \"messageContent\" cols=\"75%\" rows=\"6\" ></textarea><br>1000汉字(或2000字符)以内<br>";
		rts += "<button onClick=\"Message.writeMessage("+id+","+userId+","+receiver+","+_obj+")\" >发送</button></div>";
		var rt = "rt"+_obj;
		document.getElementById(rt).innerHTML = rts;
		Message.clearReply("replyLink"+_obj);
		
	}
	
	Message.clearReply= function(_replyLink){
		try{
		var replyLink=document.getElementById(_replyLink);
		replyLink.style.display="none";
		}
 		catch(ex)	
		{		
		}
	}
	
	Message.writeMessage=function(id,userId,receiver,_obj){
		var messageContent = document.getElementById("messageContent").innerText;
		var messageTitle = document.getElementById("messageTitle").innerText;
		if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
		}	
		else if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlHttp.onreadystatechange = Message.writeMessageResult;
		
		
		var id = encodeURIComponent(id);
		var messageContent = encodeURIComponent(messageContent);
		var userId = encodeURIComponent(userId);
		var messageTitle = encodeURIComponent(messageTitle);
		var receiver = encodeURIComponent(receiver);
		
		var url = "cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=writeMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&reMessageId="+id+"&messageContent="+messageContent+"&creatorId="+userId+"&messageTitle="+messageTitle+"&messageReceiver="+receiver+"&rtId="+_obj;
		//var trueurl = Message.encode_utf8("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=writeMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&reMessageId="+id+"&messageContent="+messageContent+"&creatorId="+userId+"&messageTitle="+messageTitle);
		try{
		xmlHttp.open("GET", url, true);
		
		xmlHttp.send(null);
		} 
		catch(e)
		{
		
		}		
	}

	
	Message.writeMessageResult= function(){
		if (xmlHttp.readyState == 4)
	 	{
	 		if (xmlHttp.status==200)
	 		{
				var response = xmlHttp.responseXML;
				if("success" ==response.getElementsByTagName("result")[0].firstChild.nodeValue)
				{
				var rtId = response.getElementsByTagName("rtId")[0].firstChild.nodeValue;
				Message.newsShowHide("news"+rtId);
				Message.newsShowHide("replyLink"+rtId);
				//Message.newsShowHide("rt"+rtId);
				//document.getElementById("messageContent").innerText="";
				//document.getElementById("messageTitle").innerText="";
				var b = document.getElementById("textarea"); 
　　				var c = b.parentNode; 
　　				c.removeChild(b); 

				alert("回复成功");
				}
				else{
				alert(response.getElementsByTagName("result")[0].firstChild.nodeValue);	
				}
			}
		}	  
	}
	
	Message.changeTRbg= function(_self,_obj){
		obj=document.getElementById(_obj);
		if(_self.checked){obj.className='BG3';}
		else{obj.className='';checkall.checked=false;}
	}
	
	
	Message.checkAll=function(status){
		var chbox=document.getElementsByName("delete_messgaeId");
		var myL=chbox.length;
		for(i=0;i<myL;i++)	{chbox[i].checked=status;}
		var trBG=document.getElementsByTagName("tr");
		for(i=0;i<trBG.length;i++)
		{	
			if(trBG[i].id.indexOf("trBG")!=-1 && checkall.checked==true)
			{trBG[i].className='BG3';}
			else if(trBG[i].id.indexOf("trBG")!=-1 && checkall.checked==false)
			{trBG[i].className='';}
		}
	}
		
		
	Message.del=function(){
		Message.submit("cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=deleteMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&ajax=true",'#form1','');
		var page = document.getElementById("page").innerText;
		if(page=="getFriendsMessage"){
		Message.getMessage(1);	
		}
		else if(page=="getSysMessage")
		{
		Message.getMessage(2);
		}
		else(page=="getSentMessage")
		{
		Message.getMessage(3);
		}		
	}  
 	
	Message.submit = function (actionPath, formId,data){
		var value={};
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
        $.ajax({
            async:false,
            type: "POST",
            url: actionPath,
            data: formData,
            success: function(msg){
				   var message=document.getElementById("message");
                   message.innerHTML=msg;
				   message.style.left=MouseX+12;
				   message.style.top=MouseY+12;

				   $("#message").fadeIn("fast");
				   $("#message").fadeOut(2000,function(){
					document.getElementById("message").innerHTML="";
				   });
                                
            }
        });
      	return value;
		
    }
	Message.remindNewMessage = function(){
		if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
		}	
		else if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		var url ="cn.edu.pku.dr.requirement.elicitation.action.MessageAction.do?ACTION=remindNewMessage&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message";
		xmlHttp.open("GET",url,true);
		xmlHttp.onreadystatechange = Message.startCallback; 
		xmlHttp.send(null); 	
	}
	
	Message.startCallback=function(){
		if (xmlHttp.readyState == 4)
	 	{
	 		if (xmlHttp.status==200)
	 		{
					
				var message = xmlHttp.responseXML.getElementsByTagName("message")[0].firstChild.nodeValue;
				if(message == "success"){
					var num = xmlHttp.responseXML.getElementsByTagName("num")[0].firstChild.nodeValue;
					
					document.getElementById("mymessage").style.color="#FF0000";
					
					document.getElementById("mymessage").innerHTML="您有"+num+"条新消息";					
				}
				else
				{	
					document.getElementById("mymessage").style.color="#000080";
					document.getElementById("mymessage").innerHTML="消息";
				}
				setTimeout("Message.remindNewMessage()",3000000);
			}
		}	  
	}

		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
})();