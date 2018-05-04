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
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>
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
    	alert("123");
		    	$.ajax({
		    		
						url :"queryAllDept.action",
						type :"POST",
						dataType : "json",
						async : true,
						success :function(result){
		    				var optionStr = "<option value=\"\">请选择</option>";
							for(var i = 0; i < result.length; i++){
								optionStr += "<option>"+result[i].deptName + "</option>";
							}
							$("#employDept").html(optionStr);
						}	
					});
    	});
    
	</script>
	
	
</head>
  
  <body>
   	<form action="employAdd.action" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="methodType" value="addDept"> 
	   	<table id = "deptEditTable">
	   		<%--<tr>
	   			<td>
	   			员工编号:
	   			</td>
	   			<td>
	   				<input type = "text" name="employNo" id="employNo"/>
	   			</td>
	   		</tr>
	   		--%><tr>
	   			<td>
	   			员工姓名:
	   			</td>
	   			<td>
	   				<input type = "text" name="employName" id="employName"/>
	   			</td>
	   		</tr>  
	
	   		<tr>
	   			<td>
	   			性别:
	   			</td>
	   			<td>
	   				<select id="employSex" name="employSex">
	   					<option>男</option>
	   					<option>女</option>
	   				</select>
	   			</td>
	   		</tr>  
	
	   		<tr>
	   			<td>
	   			所属部门:
	   			</td>
	   			<td>
	   			<select id="employDept" name="employDept">
	   				<option>请选择</option>
	   			</select>
	   			</td>
	   		</tr>  
	   		<tr>
	   			<td>
	   			入职时间：
	   			</td>
	   			<td>
	   				<input type = "text" name="createTime" id="createTime"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td colspan="2">
	   				<input type = "submit" value="添加"/>
	   				<input type = "reset" value="重置"/>
					<a href="queryAllEpByPage.action" target="contentPage"><input type="button" value="返回"></a>
	   			</td>
	   		</tr>  	
	   	</table>
   	
   	
   	</form>
  </body>
</html>
