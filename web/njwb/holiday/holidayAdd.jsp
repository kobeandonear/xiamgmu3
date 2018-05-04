<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>请假员工编辑</title>
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
	<%--
		
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
	--%>
	</script>
	
	
</head>
  
  <body>
   	<form action="holidayAdd.action" method="post" enctype="multipart/form-data">
   		
	   	<table id = "deptEditTable">
	   		
	   		<tr>
	   			<td>
	   			请假类型:
	   			</td>
	   			<td>
	   				<select id="holidayType" name="holidayType">
	   					<option>事假</option>
	   					<option>婚假</option>
	   					<option>年假</option>
	   					<option>调休</option>
	   					<option>病假</option>
	   					<option>丧假</option>
	   				</select>
	   			</td>
	   		</tr>  
	
	   		
	   		<tr>
	   			<td>
	   			请假事由：
	   			</td>
	   			<td>
	   			<textarea rows="3" cols="5" name="holidayBz" id="holidayBz"></textarea>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>
	   			开始时间:
	   			</td>
	   			<td>
	   				<input type = "text" name="startTime" id="startTime"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>
	   			结束时间:
	   			</td>
	   			<td>
	   				<input type = "text" name="endTime" id="endTime"/>
	   			</td>
	   		</tr>    
	   		
	   		<tr>
	   			<td colspan="2">
	   				<input type = "submit"  name="holidayStatus" value="提交"/>
	   				<input type = "submit"  name="holidayStatus" value="草稿"/>
	   				<input type = "reset" value="重置"/>
					<a href="queryAllHdByPage.action" target="contentPage"><input type="button" value="返回"></a>
	   			</td>
	   		</tr>  	
	   	</table>
   	
   	
   	</form>
  </body>
</html>
