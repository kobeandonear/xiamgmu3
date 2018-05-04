<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'upload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   文件上传，form中只有一个文件
   <!-- 文件上传，input type="file" 文件上传控件
     	提交方式，必须是post
     	enctype="multipart/form-data" : 提交的内容，使用二进制方式提交
      -->
      <form action="uploadImg.action" method="post" enctype="multipart/form-data">
      	<input name="imgFile" type="file">
      	<br>
      	<input type="submit" value="上传">
      </form>
      
      文件上传
      <form action="upload.action" method="post" enctype="multipart/form-data">
      	部门编号：<input type="text" name="deptNo" id="deptNo"/><br>
     	部门名称：<input type="text" name="deptName" id="deptName"/><br>
     	部门位置：<input type="text" name="deptLoc" id="deptLoc"/><br>
     	部门图片：<input name="imgFile" type="file"><br>
      
      	<input type="submit" value="上传">
      </form>
  </body>
</html>
