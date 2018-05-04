
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	
			
			
		
		
		function select(employNo,cha){
			location.href="employDetail.action?employNo="+employNo;
		}
		
	
	
	function checkEpByNameDept(pageNo)
		{
			var employName = $("#employName").val();
			var employDept = $("#employDept").val();
			alert("employName="+employName+",employDept="+employDept);
			
			$.ajax({
				url :"queryChaDept.action",
				type :"POST",
				async : true,
				data : "employName="+employName+"&employDept="+employDept+"&pageNo="+pageNo,
				dataType : "json",
				success:function(result){
						//alert(result);  //Object json对象
						//alert(JSON.stringify(result));
						//拿到返回json对象中的dept数组
						
						
						if(result == "1"){
							alert("请选择输入查询！");
						}else{
						var deptArr = result.dataList;
						var tableStr = "";
						for(var i = 0; i < deptArr.length; i++)
						{
							
							//循环生成tr
							tableStr += "<tr>";
	         				
	         				tableStr += "<td>"+deptArr[i].employNo+"</td>";
         					tableStr += "<td>"+deptArr[i].employName+"</td>";
         					tableStr += "<td>"+deptArr[i].employSex+"</td>";
         					tableStr += "<td>"+deptArr[i].employDeptName+"</td>";
         					tableStr += "<td>"+deptArr[i].createTime+"</td>";
         					tableStr += "<td><img src=\"img/delete.png\" class=\"operateImg\" onclick=\"del(\'"+deptArr[i].employNo+"\',\'1\')\">";
							tableStr += "<a href=\"employDetail.action?sourceType=edit&employNo="+deptArr[i].employNo+"\" target=\"contentPage\"><img alt=\"\" src=\"img/edit.png\" class=\"operateImg\" ></a>";
         					tableStr += "<img onclick=\"select(\'"+deptArr[i].employNo+"\')\" alt=\"\" src=\"img/detail.png\" class=\"operateImg\"></td>";
							tableStr += "</tr>";
						}
						//先将第一行和最后一行之外的数据清空：
						$("tr:gt(0)").remove();
						
						tableStr +="<td colspan=\"6\"><a>首页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a>尾页</a> <span></span></td>";
						//将tableStr添加到table中去
						$("tr:eq(0)").after(tableStr);
						
						//分页,拿最后一个tr中的a标签
						var aArray = $("tr:last a");
						$(aArray[0]).attr("onclick","checkEpByNameDept(\"1\")");
						$(aArray[1]).attr("onclick","checkEpByNameDept(" + result.prePage + ")");
						$(aArray[2]).attr("onclick","checkEpByNameDept(" + result.nextPage + ")");
						$(aArray[3]).attr("onclick","checkEpByNameDept(" + result.lastPage + ")");
						
						//最后一个td 中的span
						$("td:last span").html("当前第 "+ result.pageNo + "页，共 " + result.totalPage + " 页");
					}
				}
			});
		}
	
	
    //onload事件
    $(function(){
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
    
    
    
    
    function del(employNo,quFen){
			var result = window.confirm("确认要删除吗?");
			if(true == result){
				$.ajax({
					url :"delEmploy.action",
					type :"POST",
					async : true,
					data : "employNo="+employNo,
					dataType : "text",
					success:function(result){
						if(result == "success")
						{
							alert("删除成功");
							if(quFen=="1"){
								checkEpByNameDept("1");
							}else{
								location.href="queryAllEpByPage.action";
							}
							
						}
						else {
							alert("删除失败");
						}
					}				
				});
			}
	}
    	</script>
   
  </head>
  
  <body>
			<h3 class="title">首页  &gt;&gt;员工管理 </h3>
			<div class="choiceE">
				姓名：<input width="40px;" height="10px;" id="employName"/>
         		部门： <select id="employDept" style="width:120px;">
                            <option>请选择</option>
                      </select>
                     <button onclick="checkEpByNameDept('${pageMode.pageNo}')">查询</button>
         		<span id="deptNameMsg"></span>
			</div>        		
         	<br>
         	<div class="addE" >
         		<a href="njwb/employ/employAdd.jsp" target="contentPage"><img alt="" src="img/add.png" width="18px" height="18px">添加员工</a>
         	</div>
         	
         	<table class="employInfo">
         		<tr class="titleRow">
         			<td>员工编号</td>
         			<td>员工姓名</td>
         			<td>性别</td>
         			<td>所属部门</td>
         			<td>入职时间</td>
         			<td>操作列表</td>
         		</tr>
         		<c:forEach items="${pageMode.dataList}" var="employ">
         			<tr>
	         			<td>${employ.employNo }</td>
	         			<td>${employ.employName }</td>
	         			<td>${employ.employSex }</td>
	         			<td>${employ.employDeptName }</td>
	         			<td>${employ.createTime }</td>
	         			<td>
	         				<img alt="" src="img/delete.png" class="operateImg" onclick="del('${employ.employNo }','0')">
	         				<a href="employDetail.action?sourceType=edit&employNo=${employ.employNo }" target="contentPage"><img alt="" src="img/edit.png" class="operateImg" ></a>
	         				<img onclick="select('${employ.employNo }')" alt="" src="img/detail.png" class="operateImg">
	         			</td>
	         		</tr>
         		</c:forEach>
         		<tr>
         			<td colspan="6">
         				<a href="queryAllEpByPage.action?pageNo=1">首页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllEpByPage.action?pageNo=${pageMode.prePage}">上一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllEpByPage.action?pageNo=${pageMode.nextPage}">下一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="queryAllEpByPage.action?pageNo=${pageMode.totalPage }">尾页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<span>当前第  ${pageMode.pageNo }  页，共 ${pageMode.totalPage } 页</span>
         			</td>
         			
         			
         		</tr>
         		
         		
         				     		         	
         	</table>
  </body>
</html>