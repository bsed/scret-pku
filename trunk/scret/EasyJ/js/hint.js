//***********默认设置定义.*********************
tPopWait = 50;//停留tWait豪秒后显示提示。
tPopShow = 5000;//显示tShow豪秒后关闭提示
showPopStep = 100;
popOpacity = 99;
maxWidth = 600; //默认显示宽度
var hintForeColor = "#333333"; //字符颜色
var hintBackColor = "#EDF2FC"; //背景色
var hintBorderColor = "#9DBAF7"; //边框颜色
//***************内部变量定义*****************
sPop = null;
curShow = null;
tFadeOut = null;
tFadeIn = null;
tFadeWaiting = null;
var MouseX;
var MouseY;
if (broswer == "IE") {
    document.write("<style type='text/css'id='defaultHintStyle'>");
    document.write(".cPopText {  background-color: " + hintBackColor + ";color:" + hintForeColor + "; border: 1px " + hintBorderColor + " solid;font-color: font-size: 9px; padding-right: 4px; padding-left: 4px; height: 20px; padding-top: 2px; padding-bottom: 2px; filter: Alpha(Opacity=0);}");
    document.write("</style>");
    document.write("<div id='dypopLayer' style='position:absolute;z-index:1000;' class='cPopText'></div>");
}
else
    if (broswer == "FireFox") {
        document.write("<style type='text/css'id='defaultHintStyle'>");
        document.write(".cPopText {  background-color: " + hintBackColor + ";color:" + hintForeColor + "; border: 1px " + hintBorderColor + " solid;font-color: font-size: 9px; padding-right: 4px; padding-left: 4px; height: auto;padding-top: 2px; padding-bottom: 2px; MozOpacity:0; word-wrap:break-word; }");
        document.write("</style>");
        document.write("<div id='dypopLayer' style='position:absolute;z-index:1000;'></div>");
    }
function isChinese(character){
    var re = /[^\u4e00-\u9fa5]/;
    if (re.test(character))
        return false;
    return true;
}

//按照px来对文本进行分行，maxLength用来指明一行总共占多少pix，letterSize用来指明当前font指定size下一个英文字母所占的pix大小。
function toBreakWord(maxLength, letterSize){
    var obj = document.getElementById("dypopLayer");
    var strContent = obj.innerHTML;
    var strTemp = "";
    var length = 0;
    var j = 0;
    var newLine = false;
    for (i = 0; i < strContent.length; i++) {
        //strContent.charAt(i)<0说明是中文字符
        if (isChinese(strContent.charAt(i)))
            length += letterSize * 2;
        else
            length += letterSize;
        //这时说明加上下面一个字符所占空间可能超过pixLength，需要换行。
        if ((length + letterSize * 2) > maxLength) {
            strTemp += strContent.substr(j, i) + "<br>";
            length = 0;
            j = i;
            newLine = true;
            //alert(strTemp);
        }
    }
    strTemp += strContent.substr(j, i);
    obj.innerHTML = strTemp;
    if (newLine)
        return maxLength;
    else
		if(broswer=='IE')
	        return length+letterSize*2;
		else
			return length;
}


//隐藏
function fadeIn(){
    if (broswer == "IE") {
        if (document.getElementById("dypopLayer").filters.Alpha.opacity > 0) {
            document.getElementById("dypopLayer").filters.Alpha.opacity -= 1;
            tFadeIn = setTimeout(fadeIn, 1);
        }
    }
    else
        if (broswer == "FireFox") {
            if (document.getElementById("dypopLayer").style.MozOpacity > 0) {
                document.getElementById("dypopLayer").style.MozOpacity -= 0.01;
                tFadeIn = setTimeout(fadeIn, 1);
            }
        }
}

//显示
function fadeOut(){

    if (broswer == "IE") {
        if (document.getElementById("dypopLayer").filters.Alpha.opacity < popOpacity) {
            document.getElementById("dypopLayer").filters.Alpha.opacity += showPopStep;
            tFadeOut = setTimeout(fadeOut, 1);
        }
        else {
            document.getElementById("dypopLayer").filters.Alpha.opacity = popOpacity;
            tFadeWaiting = setTimeout(fadeIn, tPopShow);
        }
    }
    else
        if (broswer == "FireFox") {
            if (document.getElementById("dypopLayer").style.MozOpacity < popOpacity / 100.0) {
                document.getElementById("dypopLayer").style.MozOpacity += showPopStep / 100.0;
                tFadeOut = setTimeout(fadeOut, 1);
            }
            else {
                document.getElementById("dypopLayer").style.MozOpacity = popOpacity / 100.0;
                tFadeWaiting = setTimeout(fadeIn, tPopShow);
            }
        }
}


function showIt(){
    document.getElementById("dypopLayer").className = "cPopText";
    document.getElementById("dypopLayer").innerHTML = sPop;
    var popWidth = toBreakWord(maxWidth, 8);

    document.getElementById("dypopLayer").style.left = (MouseX + 12) + 'px';
    document.getElementById("dypopLayer").style.top = (MouseY + 12) + 'px';
    if (popWidth != 0)
        document.getElementById("dypopLayer").style.width = popWidth + 'px';
    fadeOut();
}

function showPopupText(event){
    event = event || window.event;
    var o = event.srcElement || event.target;
    MouseX = event.x || event.pageX;
    MouseY = event.y || event.pageY;
    if (o.alt != null && o.alt != "") {
        o.dypop = o.alt;
        o.alt = ""
    };
    if (o.title != null && o.title != "") {
        o.dypop = o.title;

        o.title = ""
    };
    if (o.abbr != null && o.abbr != "") {
        o.dypop = o.innerHTML;
    }
    sPop = o.dypop;
    clearTimeout(curShow);
    clearTimeout(tFadeOut);
    clearTimeout(tFadeIn);
    clearTimeout(tFadeWaiting);
    //如果没有内容，则隐藏
    if (sPop == null || $.trim(sPop)=='') {
		//alert(sPop);
        document.getElementById("dypopLayer").innerHTML = "";
        if (broswer == "IE")
            document.getElementById("dypopLayer").filters.Alpha.opacity = 0;
        else
            document.getElementById("dypopLayer").style.MozOpacity = 0;
    }//如果有内容则显示
    else
        curShow = setTimeout(showIt, tPopWait);
}

document.onmouseover = showPopupText;

