var Discuss = {};
(function(){
    //primaryKey用来指示主键名字。id用来主键值。queryBySource用来表示展开是否是按照同主题的
    Discuss.expandContent = function (primaryKey, id,queryByTopic,topicId){
      //alert(topicId);
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        var contentClass=".totalContent"+topicId;
        var contents=$(contentClass);
        //alert(contents.length);
        if(contents.length==0)
        {
	     var message=Ajax.submitLoad(actionName.value + ".do?ACTION=expand&" + primaryKey + "=" + id+"&queryByTopic="+queryByTopic, null,"#form1",null,false);
             $("#tr"+id).after(message);
        }else
        {     $.each(contents,function(i){
             	if(this.style.display=='none')this.style.display='block';
                else this.style.display='none';
             });
        }
    }
   Discuss.reply=function(id){
     var replyId="reply"+id;
     //alert(replyId);
     var reply=document.getElementById(replyId);
     //alert(reply);
     reply.style.display='block';
     var titleId="title"+id;
     var title=document.getElementById(titleId);
     title.style.display='block';
   }

   Discuss.queryProblemDiscuss=function()
   {
        form1.discussType.value="problem";
        Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.DiscussAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Discuss&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxForumQuery.jsp",'#columnMain','#form1');
   	var problemDiscuss=document.getElementById("problemDiscuss");
        problemDiscuss.style.display="block";
   	var solutionDiscuss=document.getElementById("solutionDiscuss");
        solutionDiscuss.style.display="none";
   }

   Discuss.querySolutionDiscuss=function()
   {
        form1.discussType.value="solution";
        Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.DiscussAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Discuss&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxForumQuery.jsp",'#columnMain','#form1');
   	var solutionDiscuss=document.getElementById("solutionDiscuss");
        solutionDiscuss.style.display="block";
   	var problemDiscuss=document.getElementById("problemDiscuss");
        problemDiscuss.style.display="none";
   }

   Discuss.saveReply=function(id,discussSourceId,discussTopicId,queryByTopic)
   {
     var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
     //首先保存内容。
     var propertyValues=Ajax.submit(actionName.value + ".do?ACTION=saveReply&discussParentId="+ id+"&discussSourceId="+discussSourceId+"&discussTopicId="+discussTopicId, "#form1",null);
     //然后将回复的内容隐藏掉。
     if(!queryByTopic)
     {
     var replyId="reply"+id;
     var reply=document.getElementById(replyId);
     var contentId="content"+id;
     var content=document.getElementById(contentId);
     var titleId="title"+id;
     var title=document.getElementById(titleId);

     reply.style.display='none';
     content.style.display='none';
     title.style.display='none';
     //最后在最上面插入一行新增的内容。
     Discuss.addRow(propertyValues,id,discussSourceId,discussTopicId);
     }else
     {
     	Data.query(1);
     }
   }
   Discuss.queryByTopic=function()
   {
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
		//alert(actionName);
        Ajax.submitLoad(actionName.value + ".do?ACTION=queryByTopic", "#columnMain", "#form1",null);
   }
    Discuss.addRow = function(propertyValues,id,discussSourceId,discussTopicId){
	var properties=form1.properties.value.split(",");
        var discussTitle;
        var tr="<tr><td class=\"trContent\"  align=\"center\" width=\"5%\"><input type=\"checkbox\" name=\"check\" id=\"check"+id+"\"  value=\""+id+"\"/> </td>";
        for (i = 0; i < properties.length; i++) {
            //alert(properties[i]);
            //alert(propertyValues[properties[i]]);
            if(properties[i]=="discussTitle") discussTitle=propertyValues[properties[i]];
            tr+="<td class=\"trContent\" onclick=\"Discuss.expandContent('discussId','"+id+"','"+discussSourceId+"','"+discussTopicId+"')\" align=\"center\">"+propertyValues[properties[i]]+"</td>";
        }
        tr+="</tr>";
        $("#tr"+id).after(tr);
    }
})();
