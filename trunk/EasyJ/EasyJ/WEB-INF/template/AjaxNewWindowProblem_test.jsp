<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<link href="/css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="/css/table.css" rel="stylesheet" type="text/css">

<style>.cpro table{margin:auto}.cpro{ text-align:center;float:auto;}.mr20{margin-right:20px;}.note{background:url(http://img.baidu.com/img/iknow/popo.gif) no-repeat left; margin:12px 0;padding-left:20px;font-weight:bold;}
.answer1,.answer2,.answer3{background:url(http://img.baidu.com/img/iknow/answer.gif) no-repeat;width:100px;height:25px;overflow:hidden}
.answer1{background-position:0 0}
.answer2{background-position:0 -25px}
.answer3{background-position:0 -50px}
.rk2,.rk4{right:-2px}
#Lg{ border-top:1px dashed #bbb;}
.irelate{background: transparent url(http://img.baidu.com/img/iknow/icons.gif) no-repeat;width:16px;height:16px;margin-right:2px;overflow:hidden;float:left;background-position:0 -176px;}
#cproshow .r{ word-break:break-all;cursor:hand;width:100%;}
#cproshow div.r font {font-size:14px;}
#cproshow div.r  span font {font-size:12px;}
.relateTag{padding-top:12px;padding-left:12px;}
.relateTag a{white-space:nowrap;word-break:keep-all;margin-left:1em;}
.relateTable td{font-size:14px;text-align:left}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<script language="javascript" src="/js/Problem.js"></script> 
<script language="javascript" src="/js/Message.js"></script> 




<jsp:include flush="true" page="/WEB-INF/template/common/Header.jsp"/>


<div id="center2"  style="position:absolute;RIGHT:10%;left:10%;" >
<div class="bai" >

<%--以下部分为问题主体--%>
<div class="mb12 bai" > 
<div class="rg_1"></div><div class="rg_2"></div><div class="rg_3"></div>
<div class="rg">

<div class="t1" id="question_status"><div class="ico"><div class="iok"></div></div>问题状态：解决方案征集投票中 </div>
<div class ="t1" id="solution_status"><span style="color:#FF0099">1</span>个解决方案在投票中，现共有<span style="color:#FF0099">2</span>个解决方案</div>
<div class="bc0">
<div class="p90">
<div class="f14 B wr" id="question_title"> <cq>问题标题：这个场景的业务流程是不是需要改变啊？</cq></div>
<div class="wr" id="question_info"><span class="award"></span><span class="red"> 悬赏分：10</span> - <span class="gray">离结束还有 4 天 22 小时</span></div>
<div class="f14 wr" id="question_content"><cd>我觉得这个场景所描述的业务流程有点问题，我们能不能把入库单直接</cd></div>
<div class="f14 wr" id="question_sup"></div>
</div>
<div align="left" ><input type="button" onCLick="Problem.veryhighValues()" value="修改问题描述" class="bn100" id="good_4_best_answer"></div>
<div align="right" class="gray wr" id="question_author" >提问者： <a href="#" target=_blank>evilbobo</a>  <a href="#"   style="display:none"  target=_blank> - 魔法师 五级</a> </div>
</div>

</div>
<div class="rg_4"></div><div class="rg_5"></div><div class="rg_1"></div>
</div>
<%--问题主体结束--%>
<%--tab开始--%>
<div>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemReason()"><SPAN class=NewsTitleLeft>问题原因讨论</SPAN></SPAN>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemExplan()"><SPAN class=NewsTitleLeft>问题可理解性讨论</SPAN></span>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemValue()"><SPAN class=NewsTitleLeft>问题存在价值讨论</SPAN></SPAN>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemSolution()"><SPAN class=NewsTitleLeft>问题解决方案讨论</SPAN></span>
<span 	id="reason" style="display:none">针对问题的原因有<span style="color:#FF0000">1</span>个讨论</span>
<span  id="explan" style="display:none">针对问题的可理解性有<span style="color:#FF0000">1</span>个讨论</span>
<span  id="value" style="display:none">针对问题存在价值有<span style="color:#FF0000">1</span>个讨论</span>
<span  id="solution" style="display:none">针对问题的解决方案有<span style="color:#FF0000">1</span>个讨论</span>
</div>
<%--tab结束--%>



<%--问题存在价值开始--%>
<div class="mb12 bai" id="value_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr">
<div class="t1"><div class="ico"><div class="ibest"></div></div>问题存在的价值讨论页面<button  style="display:none" onClick="Problem.remark()" id="do_comment">关闭评论</button></div>
<div class="bc0" style="padding:0px 0pt;">
<div class="t2">您觉得这个问题有没有价值？&nbsp;&nbsp;&nbsp;&nbsp;<span class="f12 gray" style="font-weight:normal">目前有 1 个人评价</span>
<table  border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr  >
<td width="120" class="f14" ><input type="button" onCLick="Problem.veryhighValues()" value="价值很高" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
<td class="f14" width="120"><input type="button" onCLick="Problem.highValue()"value="价值较高" class="bn100" id="bad_4_best_answer"><br><span class="grn">25% </span>（1）</td>
<td width="120" class="f14"><input type="button" onCLick="Problem.value()"value="一般价值" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
<td width="120" class="f14"><input type="button" onCLick="Problem.lowValue()"value="无价值" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
</tr>
</tbody>
</table>
<div  style="font-size:14px;font-weight:bold;" > 评价已经被关闭&nbsp;&nbsp;&nbsp;&nbsp;<span class="f12 gray" style="font-weight:normal">目前有 4 个人评价</span></div>
</div>
<div class="t2">对于问题是否有价值的详细解释：</div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">英镑</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"> <cn>啥是高糖？
<br>但只要是糖类
<br>我想都会造成短期血糖升高</cn></div>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a>  <a href="#" style="display:none" > -试用期 一级</a> </div>
<div id="Lg"></div>
<div class="pl10"><a href="#" class="lmore" target="_blank" id="more_comment">查看所有评论&gt;&gt;</a></div>
</div>
</div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题存在价值结束--%>



<%--问题存在原因开始--%>
<div class="mb12 bai" id="reason_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr">
<div class="t1"><div class="ico"><div class="ibest"></div></div>问题存在原因的讨论页面<button  style="display:none" onClick="Problem.remark()" id="do_comment">关闭评论</button></div>
<div class="t1">针对问题的可理解性有<span style="color:#FF0000">1</span>个讨论</div>
<div class="bc0" style="padding:0px 0pt;">
<div class="t2">您觉得这个问题有没有价值？&nbsp;&nbsp;&nbsp;&nbsp;<span class="f12 gray" style="font-weight:normal">目前有 1 个人评价</span>
<table  border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr  >
<td width="120" class="f14"><input type="button" onCLick="Problem.veryhighValue()"value="价值很高" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
<td class="f14" width="120"><input type="button" onCLick="Problem.highValue()"value="价值较高" class="bn100" id="bad_4_best_answer"><br><span class="grn">25% </span>（1）</td>
<td width="120" class="f14"><input type="button" onCLick="Problem.value()"value="一般价值" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
<td width="120" class="f14"><input type="button" onCLick="Problem.lowValue()"value="无价值" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
</tr>
</tbody>
</table>
<div  style="font-size:14px;font-weight:bold;" > 评价已经被关闭&nbsp;&nbsp;&nbsp;&nbsp;<span class="f12 gray" style="font-weight:normal">目前有 4 个人评价</span></div>
</div>
<div class="t2">对于问题是否有价值的详细解释：</div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">英镑</div>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"> <cn>啥是高糖？
<br>但只要是糖类
<br>我想都会造成短期血糖升高</cn></div>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a>  <a href="#" style="display:none" > -试用期 一级</a> </div>
<div id="Lg"></div>
<div class="pl10"><a href="#" class="lmore" target="_blank" id="more_comment">查看所有评论&gt;&gt;</a></div>
</div>
</div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题存在原因结束--%>

<%--问题可理解性开始--%>
<div class="mb12 bai" id="explan_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr">
<div class="t1"><div class="ico"><div class="ibest"></div></div>问题可理解性的讨论页面</div>
<div class="bc0" style="padding:0px 0pt;">
<div class="t2">您觉得这个问题有没有价值？&nbsp;&nbsp;&nbsp;&nbsp;<span class="f12 gray" style="font-weight:normal">目前有 1 个人评价</span>
<table  border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr  >
<td width="120" class="f14"><input type="button" onCLick="Problem.veryhighValue()"value="价值很高" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
<td class="f14" width="120"><input type="button" onCLick="Problem.highValue()"value="价值较高" class="bn100" id="bad_4_best_answer"><br><span class="grn">25% </span>（1）</td>
<td width="120" class="f14"><input type="button" onCLick="Problem.value()"value="一般价值" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
<td width="120" class="f14"><input type="button" onCLick="Problem.lowValue()"value="无价值" class="bn100" id="good_4_best_answer"><br><span class="red">25%</span> （1）</td>
</tr>
</tbody>
</table>
<div  style="font-size:14px;font-weight:bold;" > 评价已经被关闭&nbsp;&nbsp;&nbsp;&nbsp;<span class="f12 gray" style="font-weight:normal">目前有 4 个人评价</span></div>
</div>
<div class="t2">对于问题是否有价值的详细解释：</div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">英镑</div>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"> <cn>啥是高糖？
<br>但只要是糖类
<br>我想都会造成短期血糖升高</cn></div>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a>  <a href="#" style="display:none" > -试用期 一级</a> </div>
<div id="Lg"></div>
<div class="pl10"><a href="#" class="lmore" target="_blank" id="more_comment">查看所有评论&gt;&gt;</a></div>
</div>
</div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题可理解性结束--%>

<%--问题方案讨论开始--%>
<div class="mb12 bai" id="solution_more" >
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr">
<div class="t1"><div class="ico"><div class="ibest"></div></div>问题解决方案讨论页面<button  style="display:none" onClick="Problem.remark()" id="do_comment">关闭评论</button></div>
<div class ="t1" id="solution_status"><span style="color:#FF0099">1</span>个解决方案在投票中，现共有<span style="color:#FF0099">2</span>个解决方案</div>
<div class="bc0" style="padding:0px 0pt;">
<div class="t2" onclick="javascript:Problem.createNewSolution()"  style="cursor:pointer"">发表新的解决方案</div>
<div class="t2" onclick="Problem.showHide('votingSolution')" style="cursor:pointer"  title="点击显示正在投票中的方案">正在投票中的解决方案:</div>
<div style="display:none" id="votingSolution">
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">对于问题原因1,3的解决方案(经过综合解决方案得到的结果)</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
<a href="javascript:Problem.showHide('votingSolution1')">详细</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="votingSolution1" style="display:none">
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content" >re解决方案一,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"   >rere解决方案一,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
</div>
<div id="Lg"></div>
<div style="cursor:pointer">综合解决方案</div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">对于问题原因2,4的解决方案(经过综合解决方案得到的结果)</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
<a href="javascript:Problem.showHide('votingSolution2')">详细</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="votingSolution2" style="display:none">
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content" >re解决方案一,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"   >rere解决方案一,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div style="cursor:pointer">综合解决方案</div>

</div>

</div>








<div class="t2" onclick=Problem.showHide('allSolution') style="cursor:pointer">所有的解决方案</div>
<div style="display:none" id="allSolution">
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">解决方案一,我们可以这么做</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
<a href="javascript:Problem.showHide('allSolution1')">详细</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="allSolution1" style="display:none">
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content" >re解决方案一,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"   >rere解决方案一,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div style="cursor:pointer">综合解决方案</div>

</div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content">解决方案二,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
<a href="javascript:Problem.showHide('allSolution2')">详细</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="allSolution2" style="display:none">
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content" >re解决方案二,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div class="f14 p90 pl10" class="answer_content"   >rere解决方案二,我们可以这么做....</div>
<IMG src="/image/flower.gif"> <A class=brown12  href="javascript:Problem.flower()">送鲜花</A>（得<SPAN>0</SPAN>支）
<IMG height=15 src="/image/badegg.gif" width=16><A class=brown12  href="javascript:Problem.badegg()">扔鸡蛋</A>（得<SPAN class=orange12>0</SPAN>个)
&nbsp<a>回复</a>
<div align="right" style="margin: 5px 5px 8px;" id="comment_info"><span class="gray">评论者：</span>	<a href="#">zjxgn</a> <a href="#"  style="display:none">- 试用期 一级</a> </div>
<div id="Lg"></div>
<div style="cursor:pointer">综合解决方案</div>


</div>

<div id="Lg"></div>
<div class="pl10"><a href="#" class="lmore" target="_blank" id="more_comment">查看所有解决方案&gt;&gt;</a></div>
</div>
</div>
</div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题解决方案结束--%>

</div>
</div>





