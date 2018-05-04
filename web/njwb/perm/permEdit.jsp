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
   	<form action="editPerm.action" method="post" enctype="multipart/form-data">
   	<!-- 原始的部门编号 ，使用隐藏域-->
   	<table id = "deptEditTable">
   		
   					<tr>
         				<td>
	   					角色:
	   					</td>
         				<td>
         				<select id="roleName" name="roleName" >
         					<c:forEach items="${roleList }" var="role">	         				
		   						<option  <c:if test="${role.roleName==permissions.role.roleName }">selected</c:if>>${role.roleName }</option>
		   					</c:forEach>		
	   					</select>         				
	         			</td>
	         		</tr>   	
   		
   					<tr>
         				<td>
	   					菜单:
	   					</td>
         				<td>
         				<select id="menuName" name="menuName">
         					<c:forEach items="${menuList }" var="menu">	         				
		   						<option  <c:if test="${menu.menuName==permissions.menu.menuName }">selected</c:if>>${menu.menuName}</option>
		   					</c:forEach>	
	   					</select>         				
	         			</td>
	         		</tr>   			
   		<tr>
   			<td colspan="2">
   				<input type = "submit" value="修改"/>
   				<input type = "reset" value="重置"/>
				<a href="queryAllPerByPage.action" target="contentPage"><input type="button" value="返回"></a>
   			</td>
   		</tr>  	
   	</table>
   	
   	
   	</form>
  </body>
</html>
