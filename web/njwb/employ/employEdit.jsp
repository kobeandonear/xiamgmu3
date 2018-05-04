<%@page import="com.njwb.entity.Dept"%>
<%@page import="com.njwb.system.ApplicationContext"%>
<%@page import="com.njwb.service.DeptService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>员工编辑</title>
	<base href="<%=basePath%>">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    
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
			<div class="choiceE">
				姓名：<input width="40px;" height="10px;" id="employName" value="${employ.employName}"/>
         		部门：<select id="employDept" style="width:120px;">
                            <option>${employ.employDeptName }</option>
                          </select>
                          <button onclick="/njwb/employ/employEdit.jsp">查询</button>
         		<span id="deptNameMsg"></span>
			</div>        		
			<br><br><br><br/>
   	<form action="editEmploy.action" method="post" enctype="multipart/form-data">
   	<!-- 原始的部门编号 ，使用隐藏域-->
   	<table id = "deptEditTable">
   		<tr>
   			<td>
   			员工编号:
   			</td>
   			<td>
   				<input readonly="readonly" value="${employ.employNo}" type = "text" name="employNo" id="employNo"/>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			员工名称:
   			</td>
   			<td>
   				<%-- <input disabled value="${dept.deptName}" type = "text" name="deptName" id="deptName"/> --%>
   				<input value="${employ.employName}" type = "text" name="employName" id="employName"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			性别:
   			</td>
   			<td>
   				<input value="${employ.employSex }" type = "text" name="employSex" id="employSex"/>
   			</td>
   		</tr>  

   		<tr>
   			<td>
   			所属部门:
   			</td>
   			<td>
   						<select id="employDept" name="employDept" >
         					<c:forEach items="${deptList }" var="dept">	         				
		   						<option  <c:if test="${dept.deptName==employ.employDeptName }">selected</c:if>>${dept.deptName }</option>
		   					</c:forEach>		
	   					</select>     
   				<%-- <input value="${employ.employDeptName }" type = "text" name="employDept" id="employDept"/>--%>
   			</td>
   		</tr>
   		<tr>
   			<td>
   			入职时间:
   			</td>
   			<td>
   				<input value="${employ.createTime}" type = "text" name="createTime" id="createTime"/>
   			</td>
   		</tr>
   		
   		<tr>
   			<td colspan="2">
   				<input type = "submit" value="修改"/>
   				<input type = "reset" value="重置"/>
				<a href="queryAllEpByPage.action" target="contentPage"><input type="button" value="返回"></a>
   			</td>
   		</tr>  	
   	</table>
   	
   	
   	</form>
  </body>
</html>
