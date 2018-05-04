<%@page import="com.njwb.entity.Dept"%>
<%@page import="com.njwb.system.ApplicationContext"%>
<%@page import="com.njwb.service.DeptService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
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
  <body>
   	<form action="editHoliday.action?holiday=${holiday.holidayStatus }" method="post" enctype="multipart/form-data">
   	<!-- 原始的部门编号 ，使用隐藏域-->
   	<table id = "deptEditTable">
   		<tr>
   			<td>
   			请假编号:
   			</td>
   			<td>
   				<input readonly="readonly" value="${holiday.holidayNo}" type = "text" name="holidayNo" id="holidayNo"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			申请人:
   			</td>
   			<td>
   				<%-- <input disabled value="${dept.deptName}" type = "text" name="deptName" id="deptName"/> --%>
   				<input value="${holiday.holidayUser}" type = "text" name="holidayUser" id="holidayUser"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			请假类型:
   			</td>
   			<td>
   				<select  id="holidayType" style="width:120px;"  name="holidayType">
   					<option>${holiday.holidayType}</option>
   					<option>${list[0]}</option>
   					<option>${list[1]}</option>
   					<option>${list[2]}</option>
   					<option>${list[3]}</option>
   					<option>${list[4]}</option>
   				</select>
   				<%--<input value="${holiday.holidayType }" type = "text" name="holidayType" id="holidayType"/> --%>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			请假事由:
   			</td>
   			<td>
   				<input value="${holiday.holidayBz }" type = "text" name="holidayBz" id="holidayBz"/>
   			</td>
   		</tr>
   		
   		<tr>
   			<td>
   			开始时间:
   			</td>
   			<td>
   				<input value="${holiday.startTime }" type = "text" name="startTime" id="startTime"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			结束时间:
   			</td>
   			<td>
   				<input value="${holiday.endTime }" type = "text" name="endTime" id="endTime"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			申请状态:
   			</td>
   			<td>
   				<input value="${holiday.holidayStatus }" type = "text" name="holidayStatus" id="holidayStatus"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			提交时间:
   			</td>
   			<td>
   				<input value="${holiday.createTime}" type = "text" name="createTime" id="createTime"/>
   			</td>
   		</tr>
   		
   		<tr>
   			<td colspan="2">
   				<input type = "submit" value="修改"/>
   				<input type = "reset" value="重置"/>
				<a href="queryAllHdByPage.action" target="contentPage"><input type="button" value="返回"></a>
   			</td>
   		</tr>  	
   	</table>
   	
   	
   	</form>
  </body>
</html>
