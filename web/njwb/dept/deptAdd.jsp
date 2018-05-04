<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		//js取值：校验
		//校验; 1。输入框失去焦点的时候   2.表单提交的时候
		
		
	</script>
	
	
  </head>
  
  <body>
   	<form action="deptAdd.action" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="methodType" value="addDept"> 
	   	<table id = "deptEditTable">
	   		
	   		<tr>
	   			<td>
	   			部门名称:
	   			</td>
	   			<td>
	   				<input type = "text" name="deptName" id="deptName"/>
	   			</td>
	   		</tr>  
	
	   		<tr>
	   			<td>
	   			部门位置:
	   			</td>
	   			<td>
	   				<input type = "text" name="deptLoc" id="deptLoc"/>
	   			</td>
	   		</tr>  
	
	   		<tr>
	   			<td>
	   			部门负责人:
	   			</td>
	   			<td>
	   				<input type = "text" name="deptMaster" id="deptMaster"/>
	   			</td>
	   		</tr>  
	   		<tr>
	   			<td>
	   			部门图片：
	   			</td>
	   			<td>
	   				<input type="file" name="imgFile">
	   			</td>
	   		</tr>
	   		<tr>
	   			<td colspan="2">
	   				<input type = "submit" value="添加"/>
	   				<input type = "reset" value="重置"/>
					<a href="queryAllByPage.action" target="contentPage"><input type="button" value="返回"></a>
	   			</td>
	   		</tr>  	
	   	</table>
   	
   	
   	</form>
  </body>
</html>
