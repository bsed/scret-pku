//valueName,idName是PopUpWindow中的showTree()方法设置的全局变量。代表选择数据之后要设置的input字段的name。
function leafSelect(id,name,functionUrl)
{
  var retValue={};
  document.form1.elements[valueName].value=name;
  document.form1.elements[idName].value=id;
  PopUpWindow.hide();
}
//<!--此方法用来实现当用户要查看叶节点所对应的功能-->
function leafFunction(id,name,functionUrl)
{
  if(functionUrl!='')
  {
    moduleQuery(functionUrl);
  }
}
//<!--此方法用来实现当用户要选择一个分支节点并且返回-->
function nodeSelect(id,name,functionUrl)
{

}

function tclick(id) {
  if(document.getElementById("node"+id).style.display!='none'){ // hide the subtree
     document.getElementById("node"+id).style.display='none';
     //if(document.getElementById("logo"+id).src.search("root")!=-1)       document.getElementById("logo"+id).src="Images/zip_root.gif"
   }else{                          // open the subtree
     document.getElementById("node"+id).style.display='block';
     //if(document.getElementById("logo"+id).src.search("root")!=-1)       document.getElementById("logo"+id).src="Images/unzip_root.gif"
   }
}

//<!--此方法用来实现当用户要查看分支节点所对应的功能-->
function nodeFunction(id,name,functionUrl)
{
  tclick(id);
  if(functionUrl!='') {
    window.location=functionUrl;
  }
}


