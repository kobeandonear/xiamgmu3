<%@page import="com.njwb.entity.Dept"%>
<%@page import="com.njwb.system.ApplicationContext"%>
<%@page import="com.njwb.service.DeptService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>部门编辑</title>
    
	<base href="<%=basePath%>">
	 <script type="text/javascript" src="js/jquery-1.8.3.js"></script>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<style type="text/css">
		body,div,table,tr,td{
			margin: 0px;
			padding: 0px;
		}
	
		#deptEditTable{
			font-size: 15px;
			border-collapse: collapse;
			width: 350px;
			margin: 20px auto;
			
			
		}
		
		#deptEditTable td{
			height: 40px;
		}
	
	</style>
	<script type="text/javascript">
		 $(function(){
			$("#oldPwd").blur(function(){
    		checkNickName();
    		}
    	});
	
	
	function checkNickName(){
    	var oldPwd = $("#oldPwd").val();  //也可以使用$(this).val();
   		//去除前后空格
   		oldPwd = $.trim(oldPwd);
   		//使用正则表达式，名称长度为4-8， 只能有字母，数字
   		var reg = /^[A-Za-z]{1}[A-Za-z0-9]{3,7}$/;
   		//alert(reg.test(nickName));
   		if(!reg.test(oldPwd))
   		{
   			$("#oldPwd_put").html("昵称不合法，长度在4-8之间，字母开头，并且只能为数字和字母");
   			return false;
   		}
   		else
   		{
   			$("#oldPwd_put").html("");
   			return true;
   		}
    }
    
	
		
	</script>
 </head>
   	<form action="editPwd.action" method="post" enctype="multipart/form-data">
   	<!-- 原始的部门编号 ，使用隐藏域-->
   	<table id = "deptEditTable">
   		<tr>
   			<td>
   			员工姓名:
   			</td>
   			<td>
   				<input  readonly="readonly"  type = "text"  value="${employ.employName }"   name="employName" id="employName"/>  				
   			</td>
   		</tr>
   		
   		<tr>
   			<td>
   			原密码:
   			</td>
   			<td>
   				<input   type = "text" name="oldPwd" id="oldPwd"/>  				
                <div id="oldPwd_put"></div>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			新密码:
   			</td>
   			<td>
   				<input   type = "password" name="newPwd" id="newPwd"/>  				
                <div id="newPwd_put"></div>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			确认密码:
   			</td>
   			<td>
   				<input   type = "text" name="cofPwd" id="cofPwd"/>  				
                <div id="cofPwd_put"></div>
   			</td>
   		</tr>  

   		<tr>
   			<td colspan="2">
   				<input type = "submit" value="修改"/>
   				
   			</td>
   		</tr>  	
   	</table>
   	
   	
   	</form>
  </body>
</html>
