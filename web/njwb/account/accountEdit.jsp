<%@page import="com.njwb.entity.Dept"%>
<%@page import="com.njwb.system.ApplicationContext"%>
<%@page import="com.njwb.service.DeptService"%>
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
  </head><%--
  ?holiday=${holiday.holidayStatus }
  --%><body>
   	<form action="editAccount.action?account=${account.accountStatus }" method="post" enctype="multipart/form-data">
   	<!-- 原始的部门编号 ，使用隐藏域-->
   	<table id = "deptEditTable">
   		<tr>
   			<td>
   			报销编号:
   			</td>
   			<td>
   				<input readonly="readonly" value="${account.accountNo}" type = "text" name="accountNo" id="accountNo"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			申请人:
   			</td>
   			<td>
   				<%-- <input disabled value="${dept.deptName}" type = "text" name="deptName" id="deptName"/> --%>
   				<input value="${account.accountUser}" type = "text" name="accountUser" id="accountUser"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			报销类型:
   			</td>
   			<td>
   				<input value="${account.accountType }" type = "text" name="accountType" id="accountType"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			金额:
   			</td>
   			<td>
   				<input value="${account.accountmoney }" type = "text" name="accountmoney" id="accountmoney"/>
   			</td>
   		</tr>
   		
   		<tr>
   			<td>
   			申请时间:
   			</td>
   			<td>
   				<input value="${account.accountTime}" type = "text" name="accountTime" id="accountTime"/>
   			</td>
   		</tr>
   		
   		<tr>
   			<td>
   			申请状态:
   			</td>
   			<td>
   				<input value="${account.accountStatus }" type = "text" name="accountStatus" id="accountStatus"/>
   			</td>
   		</tr>
   		
   		
   		<tr>
   			<td colspan="2">
   				<input type = "submit" value="修改"/>
   				<input type = "reset" value="重置"/>
				<a href="queryAllAcByPage.action" target="contentPage"><input type="button" value="返回"></a>
   			</td>
   		</tr>  	
   	</table>
   	
   	
   	</form>
  </body>
</html>
