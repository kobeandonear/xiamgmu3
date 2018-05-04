<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>部门明细</title>
	<base href="<%=basePath%>">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

  </head>
  
  <body>
   	我是员工明细 
	<br>
	
	员工编码：${employ.employNo}<br>
	员工名称：${employ.employName}<br>
	性别：${employ.employSex}<br>
	所属部门： ${employ.employDeptName}<br>
	入职时间：${employ.createTime}<br>
	 <br>
	<br>
	<br>
	<a href="queryAllEpByPage.action" target="contentPage"> <input type="button" value="返回"></a>
  </body>
</html>
