
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>员工管理</title>
	<base href="<%=basePath%>">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
   
   
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	<style type="text/css">
	
	
	</style>
	
	<script type="text/javascript">
	
	
   		function del(roleId){
			var result = window.confirm("确认要删除吗?");
			if(true == result){
				$.ajax({
					
					url :"delRole.action",
					type :"POST",
					async : true,
					data : "roleId="+roleId,
					dataType : "text",
					success:function(result){
						if(result == "success")
						{
							alert("删除成功");
							
							location.href="queryAllRoByPage.action";
							
						}
						else {
							alert("已关联不能删除，删除失败");
						}
					}				
				});
			}
	}
    	</script>
   
  </head>
  
  <body>
			<h3 class="title">首页  &gt;&gt;报销管理 </h3>
		       		
         	<br>
         	<div class="addE" >
         		<a href="njwb/role/roleAdd.jsp" target="contentPage"><img alt="" src="img/add.png" width="18px" height="18px">添加角色</a>
         	</div>
         	
         	<table class="employInfo">
         		<tr class="titleRow">
         			<td>角色ID</td>
         			<td>角色名称</td>
					<td>创建时间</td>      
         			<td>操作列表</td>
         		</tr>
         		<c:forEach items="${pageMode.dataList}" var="role">
         			<tr>
	         			<td>${role.id }</td>
	         			<td>${role.roleName }</td>
	         			<td>${role.createTime}</td>
	         			
	         			<td>
	         				<img alt="" src="img/delete.png" class="operateImg" onclick="del('${role.id }')">
	         				<a href="roleDetail.action?sourceType=edit&roleId=${role.id }" target="contentPage"><img alt="" src="img/edit.png" class="operateImg" ></a>
	         			</td>
	         		</tr>
         		</c:forEach>
         		<tr>
         			<td colspan="7">
         				<a href="queryAllRoByPage.action?pageNo=1">首页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllRoByPage.action?pageNo=${pageMode.prePage}">上一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllRoByPage.action?pageNo=${pageMode.nextPage}">下一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllRoByPage.action?pageNo=${pageMode.totalPage }">尾页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<span>当前第  ${pageMode.pageNo }  页，共 ${pageMode.totalPage } 页</span>
         			</td>
         			
         			
         		</tr>
         		
         		
         				     		         	
         	</table>
  </body>
</head>