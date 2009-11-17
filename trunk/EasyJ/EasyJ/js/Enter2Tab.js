var isCycle = false; //当光标到最后一个元素的时候，是否循环光标焦点，
var iCurrent = -1;
var frmName = "0";//input_form
//
function enterToTab()  //网页里按回车时焦点的转移
{
  var e = document.activeElement;
  if(e == null) return false;
  //获得当前表单的名字
  for(i=0;i<document.forms.length;i++){
 for(var el in document.forms[i].elements){
  if(e.UniqueID == el.UniqueID){
   frmName = document.forms[i].name
  }
 }
  }
  if(window.event.keyCode == 13)
  {
   switch(e.tagName)//标签类型
 {
     case "INPUT":
   handleInput(e)
   break;
     case "TEXTAREA":
   expendTextArea(e)
   break;
     case "SELECT":
   handleSelect(e)
   break;
     case "TEXTAREA":
   handleTextarea(e)
   break;
         default:
    //window.status = "未知的标签名称:"+e.tagName+"，不能移动焦点！"
    }
  }// end if
}
//处理input 标签类型
function handleInput(e)
{
   switch(e.type)
 {
     case "text":
  case "password":
  case "checkbox":
  case "radio":
  case "file":
   moveFocusToNextElement(e)
   break;
  case "submit"://处理有提交按钮的情况
  case "button":
   if(isHandleSubmit(e)){
    handleSubmit(e)
    focusOnNextElement(document.forms[frmName].elements,iCurrent-1)
    break;
   }
   moveFocusToNextElement(e)
   break;
         default:
    }
}

//处理TextArea 标签类型
function expendTextArea(e)
{
    var lines=e.value.split('\n');
    e.rows=e.rows>lines.length+1?e.rows:lines.length+1;
}
//处理select 标签类型
function handleSelect(e)
{
 moveFocusToNextElement(e)
}
//处理textarea 标签类型
function handleTextarea(e)
{
 moveFocusToNextElement(e)
}
//移动到下一个元素
function moveFocusToNextElement(e)
{
  var oE = document.forms[frmName].elements, iCurentPos=-1;
  for(var i=0; i<oE.length; i++)
  {
    if(oE[i] == e) iCurentPos = i;
    if(iCurentPos>-1 && iCurentPos+1<oE.length)
    {
    //把焦点设置到下一个可用的元素上
  focusOnNextElement(oE,iCurentPos)
    }
  }
}
//下一个可用元素得到焦点 n 元素的位置
function focusOnNextElement(oElements,iIndex)
{
 var oE = oElements
 var oldIndex = iIndex
  while(oE[iIndex+1].type =="hidden" || oE[iIndex+1].disabled || oE[iIndex+1].readOnly == true || oE[iIndex+1].style.display == "none")
  {
   /*
   window.status += "e.name = "+oE[iIndex+1].name
   window.status += ";e.type = "+oE[iIndex+1].type
   window.status += ";e.disabled = "+oE[iIndex+1].disabled
   window.status += ";e.readOnly = "+oE[iIndex+1].readOnly+"."
   */
   iIndex++;
   if(iIndex+1 == oE.length)
   {
    if(isCycle){//设置焦点在第一元素
     focusOnNextElement(oE,-1)
    }
    return;
   }
  }//end while
  iCurrent = iIndex+1
  oE[iCurrent].focus();
        window.event.keyCode    = 0;
        window.event.returnValue= false;
        return;
}
//处理当前元素
function handleSubmit(element)
{
 element.click()
 return;
}
//判断是否处理提交
function isHandleSubmit(element)
{
 var ret = false;
 if(element !=null && (element.id.toUpperCase() == "SUBMIT" || element.name.toUpperCase() == "SUBMIT" || element.isSubmit)){
  ret = true;
 }
 return ret;
}
//初始化 initEnter2Tab()
function initEnter2Tab()
{
 for(i=0;i<document.forms.length;i++){
  if(document.forms[i].type !=null && document.forms[i].type == "enter2tab"){
   document.forms[i].onkeydown = function f(){enterToTab();};
  }
 }
 //文档初始化焦点
 if(document.forms[0].elements != null)
  focusOnNextElement(document.forms[0].elements,-1)
}

//-->

