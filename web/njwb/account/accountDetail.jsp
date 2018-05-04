<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>明细</title>
	<base href="<%=basePath%>">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

  </head>
  
  <body>
   	我是报销员工明细 
	<br>
	
	报销编号：${account.accountNo}<br>
	申请人：${account.accountUser}<br>
	报销类型：${account.accountType}<br>
	摘要：${account.accountYao}<br>
	金额： ${account.accountmoney}<br>
	申请时间：<f:formatDate value="${account.accountTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
	申请状态：${account.accountStatus}<br>
	
	 <br>
	<br>
	<br>
	<a href="queryAllAcByPage.action" target="contentPage"> <input type="button" value="返回"></a>
  </body>
</html>
