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
   	我是请假员工明细 
	<br>
	
	请假编号：${holiday.holidayNo}<br>
	申请人：${holiday.holidayUser}<br>
	请假类型：${holiday.holidayType}<br>
	请假事由： ${holiday.holidayBz}<br>
	开始时间：<f:formatDate value="${holiday.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br>
	结束时间：<f:formatDate value="${holiday.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/><br>
	申请状态：${holiday.holidayStatus}<br>
	
	创建时间：<f:formatDate value="${holiday.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/><br>
	 <br>
	<br>
	<br>
	<a href="queryAllHdByPage.action" target="contentPage"> <input type="button" value="返回"></a>
  </body>
</html>
