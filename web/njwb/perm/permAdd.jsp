<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>帐号编辑</title>
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
	
	 //onload事件
    $(function(){
		    	$.ajax({
						url :"queryAllMenu.action",
						type :"POST",
						dataType : "json",
						async : true,
						success :function(result){
		    				
		    				var optionStr = "<option value=\"\">请选择</option>";
							for(var i = 0; i < result.length; i++){
								optionStr += "<option>"+result[i].menuName + "</option>";
							}
							$("#menuName").html(optionStr);
						}	
					});
		    	$.ajax({
						url :"queryAllRole.action",
						type :"POST",
						dataType : "json",
						async : true,
						success :function(result){
		    				
		    				var optionr = "<option value=\"\">请选择</option>";
							for(var i = 0; i < result.length; i++){
								optionr += "<option>"+result[i].roleName + "</option>";
							}
							$("#roleName").html(optionr);
						}	
					});
    	});
	</script>
	
	
</head>
  
  <body>
   	<form action="permAdd.action" method="post" enctype="multipart/form-data">
   		
	   	<table id = "deptEditTable">
	   				
         			<tr>
         				<td>
	   					角色:
	   					</td>
         				<td>
	   						<select id="roleName" name="roleName" style="width:120px;">
                           		 <option>请选择</option>
                    		</select>
	   			</td>
	         </tr>   		
	   		
	   		<tr>
	   			<td>
	   			菜单:
	   			</td>
	   			<td>
	   				<select id="menuName" name="menuName" style="width:120px;">
                            <option>请选择</option>
                    </select>
	   			</td>
	   		</tr>   
	   		
	   		
	   		<tr>
	   			<td colspan="2">
	   			
	   				<input type = "submit"  name="permStatus" value="保存"/>
	   				<input type = "reset" value="重置"/>
					<a href="queryAllPerByPage.action" target="contentPage"><input type="button" value="返回"></a>
	   			</td>
	   		</tr>  	
	   	</table>
   	
   	
   	</form>
  </body>
</html>
