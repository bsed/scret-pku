var Ajax = {};

(function(){
//ç¨æ¥è®°å½åå²è®°å½
	var histories = []; //ç¨æ¥å­æ¾historyçæ°ç»
	var historyStep = -1; //æç¤ºå½åè®¿é®çhistoryçä½ç½®
	var historyBegin = 0; //æç¤ºhistoryå¼å§çä½ç½®ã
	var historyEnd = 0; //æç¤ºhistoryç»æçä½ç½®ã
    //actionPath     æè¦è®¿é®æå¡å¨çåªä¸ªæå¡ã
    //htmlComponent  æä»æå¡å¨è¿åçæ°æ®æ¾å°åªä¸ªhtmlå¯¹è±¡å½ä¸­ï¼å¦æhtmlComponentçå¼ä¸ºç©ºï¼åç´æ¥æä»æå¡å¨è·å¾çåå®¹è¿åã
    //formId         æå¨è®¿é®æå¡å¨æå¡çæ¶åï¼éè¦å°åªä¸ªformçåå®¹ä¼ ç»æå¡å¨ã
    //data           æé¤äºformä¹å¤éè¦åæå¡å¨ä¼ éçæ°æ®ï¼dataä»¥valueï¼keyçå½¢å¼å­å¨ã
    //syn            ææ­¤æ¬¡æå¡å¨è®¿é®æ¯å¦æ¯åæ­¥çï¼ä¸è¬æåµå¦ææ¯å°åå®¹æ¾å°htmlContentçè¯ï¼ä¸éè¦åæ­¥ãå¦ææ¯è¿åä¿¡æ¯ï¼åéè¦åæ­¥ãå½å¼ä¸ºfalseçæ¶åä»£è¡¨åæ­¥
    //callbacks      åå«start, successä¸¤ä¸ªå¯éé¡¹ï¼ç¨äºå¨ajaxå¼å§åæåæ¶æ§è¡é¢å¤çä»£ç ï¼æ³¨ï¼æ­¤åæ°æ¾å¨æåï¼å æ­¤ä¸å½±åç°æè°ç¨Ajaxçä»£ç ï¼
    Ajax.submitLoad = function (actionPath, htmlComponent, formId,data,syn, callbacks){
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
            beforeSend: (callbacks && callbacks["start"] ? callbacks["start"] : null),
            success: function(msg){
                returnMsg=msg;
                var comp = null;
                if(typeof htmlComponent == "string"){
                  comp = $(htmlComponent);
                } else {
                  comp = htmlComponent;
                }
                comp.empty().append(msg);
                Ajax.saveHistory(msg);
                if (callbacks && callbacks["success"]) {
                    callbacks["success"](msg);
                }
            }
        });
      }else
      {
        $.ajax({
            type: "POST",
            url: actionPath,
            data: formData,
            beforeSend: (callbacks && callbacks["start"] ? callbacks["start"] : null),
            success: function(msg){
                returnMsg=msg;
                var comp = null;
                if(typeof htmlComponent == "string"){
                  comp = $(htmlComponent);
                } else {
                  comp = htmlComponent;
                }
                comp.empty().append(msg);
                Ajax.saveHistory(msg);
                if (callbacks && callbacks["success"]) {
                    callbacks["success"](msg);
                }
            }
        });
      }
      return returnMsg;
    }
    //å¯¹äºæ´æ°æä½ï¼åªéè¦submitï¼èå¹¶ä¸éè¦ä»æå¡å¨loadãæå¡å¨è¿åçä¿¡æ¯åªæ¯æä½æåï¼æèååºçå¼å¸¸ä¿¡æ¯ã
    Ajax.submit = function (actionPath, formId,data, callbacks){
		var value={};
        var formData = [];
        if (formId != '')
            formData = $(formId).formToArray();
		if (data) {
			for (var option in data) {
              //alert(option);
              //alert(data[option]);
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
            beforeSend: (callbacks && callbacks["start"] ? callbacks["start"] : null),
            success: function(msg){
				//éè¦å¯¹messageè¿è¡å¤çï¼å¦ææ¯æ°å¢æ°æ®çè¯ï¼åéè¦å¯¹ä¸»é®è¿è¡è®¾ç½®ãæä»¥å¨msgä¸­é¤äºæç¤ºä¿¡æ¯ä¹å¤è¿ææ°æ®ä¿¡æ¯
				//ä¾å¦ï¼æ°å¢ä¸ä¸ªstudentçè¯ï¼msgçå¼åºè¯¥ä¸º"studentId=1<message>ä¿å­æå"ãå¦æéè¦åæ¶è®¾ç½®å¶ä»çå±æ§å¼
				//å°±éè¦æ¹åä¸º"studentId=1&studentName=liu<message>ä¿å­æå"
				var message=[];
				message=msg.split("<message>");
				var propertyValueStr,hintMessage;
				if (message.length == 2) {
					propertyValueStr = message[0];
					hintMessage = message[1];
				}else
				{
					hintMessage = message[0];
				}
				value["hintMessage"]=hintMessage;
				if (propertyValueStr) {
					var propertyValues = propertyValueStr.split("&");
					//alert("propertyValues="+propertyValues.length);
					for (i = 0; i < propertyValues.length; i++) {
						var property = propertyValues[i].split("=");
						//alert(property[0]);
						//alert(property[1]);
						value[property[0]] = property[1];
					}
				}
                if(hintMessage&&hintMessage!='')
                {
				   var message=document.getElementById("message");
                  		   message.innerHTML=hintMessage;
				   message.style.left=MouseX+12;
				   message.style.top=MouseY+12;

				   $("#message").fadeIn("fast");
				   $("#message").fadeOut(2000,function(){
					document.getElementById("message").innerHTML="";
				   });
                 }
                 if (callbacks && callbacks["success"]) {
                    callbacks["success"](msg);
                }
            }
        });
      	return value;
    }

    Ajax.submitLoadWithoutHistory = function (actionPath, htmlComponent, formId,data,syncType, callbacks){
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
		var message=null;
        $.ajax({
			async:syncType,
            type: "POST",
            url: actionPath,
            data: formData,
            beforeSend: (callbacks && callbacks["start"] ? callbacks["start"] : null),
            success: function(msg){
                //ä¹æä»¥ä½¿ç¨innerHTMLæ¯å ä¸ºæ»ä¹æ²¡æ¾ç¤ºï¼åæ¥ç¥éæ¯å ä¸ºè¿åçåå®¹hmtlæ ç­¾æé®é¢ãå ä¸ºinnerHTMLä¼èªå¨çè¡¥é½æ ç­¾ï¼
                //èappendä¸ä¼ï¼æä»¥é æäºè¿æ ·çé®é¢ã
                //PopUpWindowFrame.innerHTML=msg;
				message=msg;
				var comp = null;
                if(typeof htmlComponent == "string"){
                  comp = $(htmlComponent);
                } else {
                  comp = htmlComponent;
                }
                comp.empty().append(msg);
                if (callbacks && callbacks["success"]) {
                    callbacks["success"](msg);
                }
            }
        });
		return message;
    }

    Ajax.loadHistory = function (direction){
        if (direction == 'pre') {
        	//alert(historyStep);
            if (historyStep == historyBegin)
                return; //å°æå¤´çæ¶åè¿åã
            historyStep--;
        }
        else {
            if (historyStep == historyEnd)
                return; //å°æå°¾çæ¶åè¿åã
            historyStep++;
        }
        if (historyStep < 0)
            historyStep = historyStep + 10;
        var columnMain = document.getElementById("columnMain");
        columnMain.innerHTML = histories[historyStep];
    }

    Ajax.saveHistory = function(){
        var columnMain = document.getElementById("columnMain");
        historyStep++;
        //alert(historyStep);
        histories[historyStep] = columnMain.innerHTML;
        //alert(columnMain.innerHTML);
        if (historyStep >= 10) {
            historyStep %= 10;
            historyEnd = historyStep;
            historyBegin = historyStep + 1;
        }
        else {
            historyEnd = historyStep;
        }

    }

})();
