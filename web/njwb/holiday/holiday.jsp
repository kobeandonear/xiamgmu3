
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
	
			
			
		
		
		function select(holidayNo,holidayStatus){
			location.href="holidayDetail.action?holidayNo="+holidayNo+"&holidayStatus="+holidayStatus;
		}
		
	
	
  
    
    
    function del(holidayNo,holidayStatus,quFen){
			var result = window.confirm("确认要删除吗?");
			if(true == result){
				$.ajax({
					url :"delHoliday.action",
					type :"POST",
					async : true,
					data : "holidayNo="+holidayNo+"&holidayStatus="+holidayStatus,
					dataType : "text",
					success:function(result){
						if(result == "success")
						{
							alert("删除成功");
							if(quFen=="1"){
								checkHdByNameDept("1");
							}else{
								location.href="queryAllHdByPage.action";
							}
							
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
			<h3 class="title">首页  &gt;&gt;请假管理 </h3>
			<div class="choiceE">
				申请人：<input width="1px;" height="10px;" id="employName"/>
         		请假类型： <select id="employDept" style="width:120px;">
                           			 <option>请选择</option>
                           			 <option>事假</option>
                           			 <option>婚假</option>
                           			 <option>年假</option>
                           			 <option>调休</option>
                           			 <option>病假</option>
                           			 <option>丧假</option>
                     			 </select>
               申请状态 ： <select id="employDept" style="width:120px;">
                  					 <option>请选择</option>
                  					 <option>已提交</option>
                  					 <option>草稿</option>
                      			</select>
                     <button onclick="checkEpByNameDept('${pageMode.pageNo}')">查询</button>
         		<span id="deptNameMsg"></span>
			</div>        		
         	<br>
         	<div class="addE" >
         		<a href="njwb/holiday/holidayAdd.jsp" target="contentPage"><img alt="" src="img/add.png" width="18px" height="18px">添加请假</a>
         	</div>
         	
         	<table class="employInfo">
         		<tr class="titleRow">
         			<td>请假编号</td>
         			<td>申请人</td>
         			<td>请假类型</td>
         			<td>请假事由</td>
         			<td>开始时间</td>
         			<td>结束时间</td>
         			<td>申请状态</td>
         			<td>提交时间</td>
         			<td>操作列表</td>
         		</tr>
         		<c:forEach items="${pageMode.dataList}" var="holiday">
         			<tr>
	         			<td>${holiday.holidayNo }</td>
	         			<td>${holiday.holidayUser }</td>
	         			<td>${holiday.holidayType }</td>
	         			<td>${holiday.holidayBz }</td>
	         			<td><f:formatDate value="${holiday.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	         			<td><f:formatDate value="${holiday.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	         			<td>${holiday.holidayStatus }</td>
	         			<td><f:formatDate value="${holiday.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	         			
	         			<td>
	         				<img alt="" src="img/delete.png" class="operateImg" onclick="del('${holiday.holidayNo }','${holiday.holidayStatus}','0')">
	         				<a href="holidayDetail.action?sourceType=edit&holidayNo=${holiday.holidayNo }&holidayStatus=${holiday.holidayStatus}" target="contentPage"><img alt="" src="img/edit.png" class="operateImg" ></a>
	         				<img onclick="select('${holiday.holidayNo }','${holiday.holidayStatus}')" alt="" src="img/detail.png" class="operateImg">
	         			</td>
	         		</tr>
         		</c:forEach>
         		<tr>
         			<td colspan="9">
         				<a href="queryAllHdByPage.action?pageNo=1">首页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllHdByPage.action?pageNo=${pageMode.prePage}">上一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllHdByPage.action?pageNo=${pageMode.nextPage}">下一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllHdByPage.action?pageNo=${pageMode.totalPage }">尾页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<span>当前第  ${pageMode.pageNo }  页，共 ${pageMode.totalPage } 页</span>
         			</td>
         			
         			
         		</tr>
         		
         		
         				     		         	
         	</table>
  </body>
</html>