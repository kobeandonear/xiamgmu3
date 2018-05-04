<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户登录</title>
	<base href="<%=basePath%>">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>

   <style type="text/css">
   body{
   	 background-color: #0070A2;
   }
   </style>
   
   <script type="text/javascript">
   		$(function(){
   		
   			//需要做参数校验，校验如果不通过，则需要阻止用户提交
   			$("form").submit(function(){
   				var userName = $("#userName").val();
   				if($.trim(userName) == "")
   				{
   					alert("用户名不能为空！");
   					return false;
   				}
   			});
   		});
   		function changeImg(obj){
   			$(obj).attr("src","codeServlet.action?id="+new Date());
   		}
   		
   </script>
   
  
  </head>
  
  <body>
     <div id = "login">
     	  <div id = "title">
     	  		NJWB管理系统
     	  </div>
     	  <form action="login.action" method="post">
	     	  <table id="loginTable">
	     	  		<tr>
	     	  			<td>用户名:&nbsp;</td>
	     	  			<td>
	     	  				<input value="admin" type= "text" name = "userName" id = "userName"/>
	     	  			</td>
	     	  			<td>&nbsp;</td>
	     	  		</tr>
	     	  		<tr>
	     	  			<td>密&nbsp;&nbsp;&nbsp;码:&nbsp;</td>
	     	  			<td>
	     	  				<input value="admin" type= "password" name = "password" id = "password"/>
	     	  			</td>
	     	  			<td>&nbsp;</td>
	     	  		</tr>
	      	  		<tr>
	     	  			<td>验证码:&nbsp;</td>
	     	  			<td>
	     	  				<input type= "text" name = "code" id = "code"/>
	     	  				<img onclick="changeImg(this)" title="眼神不好，换一张" alt="" src="codeServlet.action">
	     	  			</td>
	     	  			<td>
	     	  				&nbsp;
	     	  			</td>
	     	  		</tr>
	     	  		
	     	  		<tr>
	     	  			<td>&nbsp;</td>
	     	  			<td colspan="2">
	     	  				<input type= "submit" value="登&nbsp;录" class="btn"/>
	     	  			</td>
	     	  		</tr>
	     	  </table>
     		</form>
     </div>
  </body>
</html>
