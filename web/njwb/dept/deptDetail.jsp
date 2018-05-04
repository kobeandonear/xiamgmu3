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
   	我是部门明细 
	<br>
	
	 部门编码：${dept.deptNo}<br>
	部门名称：${dept.deptName}<br>
	地址：${dept.deptLoc}<br>
	负责人： ${dept.deptManager}<br>
	部门图片：<img width="200" height="180" alt="" src="${tomcatPath}${dept.imgUrl }">
	 <br>
	<br>
	<br>
	<a href="queryAllByPage.action" target="contentPage"> <input type="button" value="返回"></a>
  </body>
</html>
