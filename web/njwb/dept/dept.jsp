<%@page import="com.njwb.system.ApplicationContext"%>
<%@page import="com.njwb.entity.Dept"%>
<%@page import="com.njwb.service.DeptService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.njwb.constant.ErrorCode"%>
<%@page import="com.njwb.util.StringUtil"%>
<%@page import="com.njwb.entity.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>部门管理</title>
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
		
		//谁要调用我做删除，请把你编码给我
		function del(deptNo){
			var result = window.confirm("确认要删除吗?");
			if(true == result){
				//提交请求给doDelete.jsp
				//需要携带要删除的部门编码
				location.href = "delDept.action?deptNo=" + deptNo;
			}
		}
		function select(deptNo){
			location.href="deptDetail.action?deptNo="+deptNo;
		}
		/* function queryByPage(pageNo,totalPage)
		{
			if(pageNo < 1)
			{
				alert("当前已经是第一页");
				return;
			}
			if(pageNo > totalPage)
			{
				alert("当前已经是最后一页");
				return;
			}
			location.href="queryAllByPage.action?pageNo="+pageNo;
		} */
	
	</script>
  </head>
  
  <body>
			<h1 class="title">首页  &gt;&gt;部门管理 </h1>
         	
         	<div class="add">
         		<a href="njwb/dept/deptAdd.jsp" target="contentPage"><img alt="" src="img/add.png" width="18px" height="18px">添加部门</a>
         	</div>
         	
         	<table class="deptInfo">
         		<tr class="titleRow">
         			<td>部门编号</td>
         			<td>部门名称</td>
         			<td>部门图片</td>
         			<td>部门位置</td>
         			<td>部门负责人</td>
         			<td>操作列表</td>
         		</tr>
         		<c:forEach items="${pageMode.dataList}" var="dept">
         			<tr>
	         			<td>${dept.deptNo }</td>
	         			<td>${dept.deptName }</td>
	         			<td>
	         			<img width="60" height="40px;" src="${tomcatPath}${dept.imgUrl}"><br>
	         			${dept.imgRealName }<br>
	         			${dept.imgUrl }
	         			</td>
	         			<td>${dept.deptLoc }</td>
	         			<td>${dept.deptManager }</td>
	         			<td>
	         				<img alt="" src="img/delete.png" class="operateImg" onclick="del('${dept.deptNo }')">
	         				<a href="deptDetail.action?sourceType=edit&deptNo=${dept.deptNo }" target="contentPage"><img alt="" src="img/edit.png" class="operateImg" ></a>
	         				<img onclick="select('${dept.deptNo }')" alt="" src="img/detail.png" class="operateImg">
	         			</td>
	         		</tr>
         		</c:forEach>
         		<tr>
         			<td colspan="6">
         				<a href="queryAllByPage.action?pageNo=1">首页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllByPage.action?pageNo=${pageMode.prePage}">上一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllByPage.action?pageNo=${pageMode.nextPage}">下一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllByPage.action?pageNo=${pageMode.totalPage }">尾页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				当前第  ${pageMode.pageNo }  页，共 ${pageMode.totalPage } 页
         			</td>
         			
         			
         		</tr>
         		
         		
         		<!--       		
					if(deptList != null && deptList.size() != 0)
					{
		         		//2.循环遍历deptList,每循环一次，生成一次tr,怎么生成？？？，使用out.write()
						for(Dept dept : deptList)
						{
							out.write("<tr>");
						    out.write("<td>" + dept.getDeptNo() + "</td>");
						    out.write("<td>" + dept.getDeptName() + "</td>");
						    out.write("<td>" + dept.getDeptLoc() + "</td>");
						    out.write("<td>" + dept.getDeptManager() + "</td>");
						    out.write("<td>");
						    out.write("\t<img alt=\"\" src=\"img/delete.png\" class=\"operateImg\" onclick=\"del('" + dept.getDeptNo() + "')\">");
						    out.write("\t<a href=\"njwb/dept/deptEdit.jsp?deptNo="+dept.getDeptNo()+"\" target=\"contentPage\"><img alt=\"\" src=\"img/edit.png\" class=\"operateImg\" ></a>");
						   	out.write("\t<img alt=\"\" src=\"img/detail.png\" class=\"operateImg\" onclick=\"select('"+dept.getDeptNo()+"')\">");
						    //out.write("\t<a href=\"deptDetail.html\" target=\"contentPage\"><img alt=\"\" src=\"img/detail.png\" class=\"operateImg\"></a>");
						    out.write("</td>");
						    out.write("</tr>");
						}
					}
					else
					{
						//输出一个tr,显示“没有数据”
					}
         		
         		
         		
         		--><!-- 
         		
         		<tr>
         			<td>A0001</td>
         			<td>总经办</td>
         			<td>101室</td>
         			<td>李雷</td>
         			<td>
         				<img alt="" src="img/delete.png" class="operateImg" onclick="del()">
         				<a href="deptEdit.html" target="contentPage"><img alt="" src="img/edit.png" class="operateImg" ></a>
         				<a href="deptDetail.html" target="contentPage"><img alt="" src="img/detail.png" class="operateImg"></a>
         			</td>
         		</tr>
          		<tr>
         			<td>A0002</td>
         			<td>渠道部</td>
         			<td>102室</td>
         			<td>韩梅梅</td>
         			<td>
         				<img alt="" src="img/delete.png" class="operateImg">
         				<a href="deptEdit.html" target="contentPage"><img alt="" src="img/edit.png" class="operateImg"></a>
         				<a href="deptDetail.html" target="contentPage"><img alt="" src="img/detail.png" class="operateImg"></a>
         			</td>         			
         		</tr>  
          		<tr>
         			<td>A0003</td>
         			<td>市场营销部</td>
         			<td>103室</td>
         			<td>张三丰</td>
         			<td>
         				<img alt="" src="img/delete.png" class="operateImg">
         				<a href="deptEdit.html" target="contentPage"><img alt="" src="img/edit.png" class="operateImg"></a>
         				<a href="deptDetail.html" target="contentPage"><img alt="" src="img/detail.png" class="operateImg"></a>
         			</td>         			
         		</tr>  
          		<tr>
         			<td>A0004</td>
         			<td>教质部</td>
         			<td>104室</td>
         			<td>李莫愁</td>
         			<td>
         				<img alt="" src="img/delete.png" class="operateImg">
         				<a href="deptEdit.html" target="contentPage"><img alt="" src="img/edit.png" class="operateImg"></a>
         				<a href="deptDetail.html" target="contentPage"><img alt="" src="img/detail.png" class="operateImg"></a>
         			</td>         			
         		</tr> 
          		<tr>
         			<td>A0005</td>
         			<td>教学部</td>
         			<td>105室</td>
         			<td>白字画</td>
         			<td>
         				<img alt="" src="img/delete.png" class="operateImg">
         				<a href="deptEdit.html" target="contentPage"><img alt="" src="img/edit.png" class="operateImg"></a>
         				<a href="deptDetail.html" target="contentPage"><img alt="" src="img/detail.png" class="operateImg"></a>
         			</td>         			
         		</tr> 
          		<tr>
         			<td>A0006</td>
         			<td>就业部</td>
         			<td>106室</td>
         			<td>花千骨</td>
         			<td>
         				<img alt="" src="img/delete.png" class="operateImg">
         				<a href="deptEdit.html" target="contentPage"><img alt="" src="img/edit.png" class="operateImg"></a>
         				<a href="deptDetail.html" target="contentPage"><img alt="" src="img/detail.png" class="operateImg"></a>
         			</td>         			
         		</tr>           --> 		     		         	
         	</table>
  </body>
</html>