
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
	
			
			
		
		
		function select(accountNo,accountStatus){
			location.href="accountDetail.action?accountNo="+accountNo+"&accountStatus="+accountStatus;
		}
		
	
	
  
    
    
   		function del(accountNo,accountStatus){
			var result = window.confirm("确认要删除吗?");
			if(true == result){
				$.ajax({
					
					url :"delAccount.action",
					type :"POST",
					async : true,
					data : "accountNo="+accountNo+"&accountStatus="+accountStatus,
					dataType : "text",
					success:function(result){
						if(result == "success")
						{
							alert("删除成功");
							
							location.href="queryAllAcByPage.action";
							
						}
						else {
							alert("已提交不能删除，删除失败");
						}
					}				
				});
			}
	}
    	</script>
   
  </head>
  
  <body>
			<h3 class="title">首页  &gt;&gt;报销管理 </h3>
			<div class="choiceE"><%--
				
         		报销类型： <select id="employDept" style="width:120px;">
                           			 <option>请选择</option>
                           			 <option>差旅费</option>
                           			 <option>招待费</option>
                           			 <option>办公费</option>
                     			 </select>
               申请状态 ： <select id="employDept" style="width:120px;">
                  					 <option>请选择</option>
                  					 <option>已提交</option>
                  					 <option>草稿</option>
                      			</select>
                     <button onclick="checkAcNameDept('${pageMode.pageNo}')">查询</button>
         		--%><span id="deptNameMsg"></span>
			</div>        		
         	<br>
         	<div class="addE" >
         		<a href="njwb/account/accountAdd.jsp" target="contentPage"><img alt="" src="img/add.png" width="18px" height="18px">添加报销</a>
         	</div>
         	
         	<table class="employInfo">
         		<tr class="titleRow">
         			<td>报销编号</td>
         			<td>申请人</td>
         			<td>报销类型</td>
         			<td>金额</td>
         			<td>申请时间</td>      			
         			<td>申请状态</td>         
         			<td>操作列表</td>
         		</tr>
         		<c:forEach items="${pageMode.dataList}" var="account">
         			<tr>
	         			<td>${account.accountNo }</td>
	         			<td>${account.accountUser }</td>
	         			<td>${account.accountType }</td>
	         			<td>${account.accountmoney }</td>
	         			<td><f:formatDate value="${account.accountTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	         			<td>${account.accountStatus }</td>
	         			
	         			<td>
	         				<img alt="" src="img/delete.png" class="operateImg" onclick="del('${account.accountNo }','${account.accountStatus}')">
	         				<a href="accountDetail.action?sourceType=edit&accountNo=${account.accountNo }&accountStatus=${account.accountStatus}" target="contentPage"><img alt="" src="img/edit.png" class="operateImg" ></a>
	         				<img onclick="select('${account.accountNo }','${account.accountStatus}')" alt="" src="img/detail.png" class="operateImg">
	         			</td>
	         		</tr>
         		</c:forEach>
         		<tr>
         			<td colspan="7">
         				<a href="queryAllAcByPage.action?pageNo=1">首页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllAcByPage.action?pageNo=${pageMode.prePage}">上一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllAcByPage.action?pageNo=${pageMode.nextPage}">下一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllAcByPage.action?pageNo=${pageMode.totalPage }">尾页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<span>当前第  ${pageMode.pageNo }  页，共 ${pageMode.totalPage } 页</span>
         			</td>
         			
         			
         		</tr>
         		
         		
         				     		         	
         	</table>
  </body>
</h