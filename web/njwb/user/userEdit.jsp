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
  </head>
   	<form action="editUser.action" method="post" enctype="multipart/form-data">
   	<!-- 原始的部门编号 ，使用隐藏域-->
   	<table id = "deptEditTable">
   	
   		
   		<tr>
   			<td>
   			帐号:
   			</td>
   			<td>
   				<input readonly="readonly" value="${user.userName}" type = "text" name="userName" id="userName"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			密码:
   			</td>
   			<td>
   				<%-- <input disabled value="${dept.deptName}" type = "text" name="deptName" id="deptName"/> --%>
   				<input value="${user.pwd}" type = "text" name="pwd" id="pwd"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			员工编码:
   			</td>
   			<td>
   				<input value="${user.empNo }" type = "text" name="empNo" id="empNo"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			员工姓名:
   			</td>
   			<td>
   				<input value="${user.employ.employName }" type = "text" name="employName" id="employName"/>
   			</td>
   		</tr>
   		
   		<tr>
   			<td>
   			状态:
   			</td>
   			
   			<td>
   				<select id="userStatus" name="userStatus" style="width:120px;">
   					<option <c:if test="${user.userStatus =='正常'}"></c:if> >正常</option>
   					<option <c:if test="${user.userStatus =='注销'}"></c:if> >注销</option>  				
   				</select>  			
   			</td>
   			
   		</tr>
   		
   		<tr>
   			<td>
   			角色:
   			</td>
   			
   			<td>
   				<select id="roleName" name="roleName">
         					<c:forEach items="${Rolelist }" var="rolee">	         				
		   						<option  <c:if test="${rolee.roleName==user.role.roleName }">selected</c:if>>${rolee.roleName}</option>
		   					</c:forEach>	
	   			</select>    
   							
   			</td>
   			
   		</tr>
   		
   		
   		<tr>
   			<td colspan="2">
   				<input type = "submit" value="修改"/>
   				<input type = "reset" value="重置"/>
				<a href="queryAllUsByPage.action" target="contentPage"><input type="button" value="返回"></a>
   			</td>
   		</tr>  	
   	</table>
   	
   	
   	</form>
  </body>
</html>
