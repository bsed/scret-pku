<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="elicitation.service.request.RequestService"%>
<%@page import="elicitation.model.project.Project,elicitation.model.request.Request,java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add Function</title>
<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>
<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css" type="text/css"></link>
<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
<script type="text/javascript" src="domain.js"></script>
<script type="text/javascript" src="project.js"></script>
<script type="text/javascript" src="data.js"></script>
<script type="text/javascript" src="role.js"></script>
<script type="text/javascript" src="scenario.js"></script>
<script type="text/javascript" src="edit_scenario.js"></script>
<script type="text/javascript" src="join_scenario.js"></script>
<script type="text/javascript" src="request.js"></script>
</head>

<body>
<a href="#" id="add_domain">增加领域</a>
<a href="#" id="add_project">增加项目</a>

<a href="#" id="add_data">增加数据 </a>
<a href="#" id="add_role">增加角色</a>
<a href="#" id="add_scenario">增加场景</a>


<a href="#" id="edit_scenario" name="45">编辑场景</a>
<a href="#" id="req_add_data" name="45">请求增加数据</a>
<a href="#" id="req_join_scenario">请求加入场景</a>

<div class="project_req_num" id=92>

	<script type="text/javascript">
		function req_num(){
			document.getElementById('92').first
			return "haha";
		}
	</script>
</div>

<input type="hidden" id="scenario_id" value=95 />
<input type="hidden" id="project_id" value=98 />

</body>
</html>