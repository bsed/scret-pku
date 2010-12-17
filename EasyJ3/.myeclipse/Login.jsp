<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="elicitation.model.user.SysUser"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>需求获取工具</title>


		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>

		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css" type="text/css"></link>
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
	</head>
	<body>
		<script type="text/javascript">
	Ext.onReady(function(){
		
		//Ext.QuickTips.init();
		var loginbtn = Ext.get('login');
		loginbtn.addListener('click',function(){Window.show()});
		
		var registerbtn=Ext.get('register');
		registerbtn.addListener('click',function(){RegisterW.show()});
		var loginForm = new Ext.form.FormPanel({
			
									labelWidth:60,
									width:230,
									frame:true,
									labelSeparator:': ',
									region:'center',
									items:[
										new Ext.form.TextField({
											fieldLabel:'用户名',
											name:'userName',
											allowBlank:false,
											vtype:'email',
											emptyText:'请输入常用邮箱',
											}),
										new Ext.form.TextField({
											fieldLabel:'密码',
											name:'password',
											inputType:'password',
											allowBlank:false
											})
										],
									buttons:[
										new Ext.Button({
											text:'登录',
											handler:login
										}),
										new Ext.Button({
											text:'重置',
											handler:reset
										})
										]
										
									});//FormPanel;
		var Window = new Ext.Window({
						contentEI:'window',
						title : '登录',
						width : 250,
						height : 150,
						plain : true,
						closeAction : 'hide',// 关闭窗口
						maximizable : false,// 最大化控制 值为true时可以最大化窗体
						layout:'border',
						items : [
									loginForm
						]			
					});
		var registerForm = new Ext.form.FormPanel({
									labelWidth:60,
									width:230,									
									frame:true,
									labelSeparator:': ',
									region:'center',
									items:[
										new Ext.form.TextField({
											fieldLabel:'用户名',
											name:'userName',
											allowBlank:false,
											vtype:'email',
											emptyText:'请输入常用邮箱',
											}),
										new Ext.form.TextField({
											fieldLabel:'密码',
											name:'password',
											inputType:'password',
											allowBlank:false
											})
										],
									buttons:[
										new Ext.Button({
											text:'注册',
											handler:register
										}),
										new Ext.Button({
											text:'重置',
											handler:registerReset
										})
										]
										
									});//FormPanel
		
		var RegisterW = new Ext.Window({
					contentEI:'window',
					title:'注册',
					width:250,
					height:150,
					plain:true,
					closeAction:'hide',
					maximizable:false,
					layout:'border',
					items:[registerForm]
					});
					
					
		function login(){
			loginForm.form.submit({
				clientValidation:true,
				waitMsg:'正在登录系统请稍候',
				waitTitle:'提示',
				url:'loginServlet.so',//TODO --暂时采用原始的JSP来做吧. 因为ExtJs与Struts2结合可能还比较麻烦.
				method:'GET',
				success:function(form,action){
					Ext.Msg.alert('提示','系统登录成功！');
					},
				failure:function(form,action){
					Ext.Msg.alert('提示','系统登录失败,原因:'+ action.failureType);
					}
			});
		}
		function reset(){
			loginForm.form.reset();			
		}
		function registerReset(){
			registerForm.form.reset();
		}	
		function register(){
			registerForm.form.submit({
				clientValidation:true,
				waitMsg:'正在注册，请稍候',
				waitTitle:'提示',
				url:'registerServlet.so',
				method:'GET',
				success:function(form,action){
					Ext.Msg.alert('提示','注册成功,即将跳转!');
					window.location.href="Login.jsp";
					},
				failure:function(form,action){
					Ext.Msg.alert('提示','注册失败,原因:'+action.failureType);
					}
				});
		}//register();
		
		
		
		});//onReady.		

	
</script>
	<%
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		if(user == null){	
	%>
		<input id='login' type="button" value="登录"></input>
		<input id='register' type="button" value="注册"></input>
		<div id=form style="height: 100">
		</div>
		<div id=window>
		</div>
		<%}else{				
			String url  ="userCenter.do?userId="+user.getUserId();
		%>
			<div class=welcomeUser>
				欢迎您，<a href =<%=url%>><%=user.getUserName() %></a>
			</div>
		<%} %>
	
	</body>
</html>
