<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ajaxJs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	
	<script type="text/javascript">
		function checkNameText()
		{
			var deptName = $("#deptName").val();
			//使用jQuery.ajax
			//var param = {};
			//$.ajax(param); 可以先直接定义json，然后传参，也是可以的
			$.ajax({
				url : "checkNameText.action", //请求地址
				type : "GET",  //请求方式，默认get
				async : true,  //true:异步     false:同步
				data : "deptName=" + deptName,   //参数
				dataType : "text",   //返回值类型
				success : function(result){
					//ajax请求响应成功之后调用的函数
					//alert("校验结果：" + result);
					if(result == "success")
					{
						$("#deptNameMsg").html("名称可用");
						$("#deptNameMsg").attr("style","color:black");
					}
					else {
						$("#deptNameMsg").html("名称重名，换一个");
						$("#deptNameMsg").attr("style","color:red");
					}
				}
			});
			
			//异步还是同步，决定以下的代码什么时候调用
			//alert(2222222);
		}
		
		function queryDeptXml()
		{
			var deptNo = $("#deptNo").val();
			$.ajax({
				url : "queryDeptXml.action",
				type : "POST",
				data : "deptNo=" + deptNo,
				dataType : "xml",
				async : true,
				success:function(result){
					alert(result.getElementsByTagName("deptName")[0].textContent);
				}
			});
		}
		
		//后台返回json格式的字符串
		function queryDeptJson()
		{
			var deptNo = $("#deptNo").val();
			$.ajax({
				url : "queryDeptJson.action",
				type : "POST",
				data : "deptNo=" + deptNo,
				dataType : "json",
				async : true,
				success:function(result){
					//alert(result);  //Object json对象
					//alert(JSON.stringify(result));
					alert(result.deptName);
				}
			});
		}
		
		function queryDeptArray(pageNo){
			$.ajax({
					url : "queryDeptArray.action",
					type : "POST",
					data : "pageNo=" + pageNo,
					dataType : "json",
					async : true,
					success:function(result){
						//alert(result);  //Object json对象
						//alert(JSON.stringify(result));
						//alert(result.deptName);
						//拿到返回json对象中的dept数组
						var deptArr = result.dataList;
						var tableStr = "";
						for(var i = 0; i < deptArr.length; i++)
						{
							//循环生成tr
							tableStr += "<tr>";
	         				tableStr += "<td>" + deptArr[i].deptNo +  "</td>";
	         				tableStr += "<td>" + deptArr[i].deptName + "</td>";
	         				tableStr += "<td>部门图片</td>";
         					tableStr += "<td>部门位置</td>";
         					tableStr += "<td>部门负责人</td>";
         					tableStr += "<td>操作列表</td>";
							tableStr += "</tr>";
						}
						//先将第一行和最后一行之外的数据清空：
						// TODO 
						
						
						//将tableStr添加到table中去
						$("tr:eq(0)").after(tableStr);
						
						//分页,拿最后一个tr中的a标签
						var aArray = $("tr:last a");
						$(aArray[1]).attr("onclick","queryDeptArray(" + result.prePage + ")");
						$(aArray[2]).attr("onclick","queryDeptArray(" + result.nextPage + ")");
						$(aArray[3]).attr("onclick","queryDeptArray(" + result.lastPage + ")");
						
						//最后一个td 中的span
						$("td:last span").html("当前第 "+ result.pageNo + "页，共 " + result.totalPage + " 页");
					}
				});
		}
	
	</script>

  </head>
  
  <body>
    JavaScript的ajax<br>
    	部门名称：<input type="text" id="deptName"><span id="deptNameMsg"></span>
    	<button onclick="checkNameText()">重名校验text</button>
    	<br>
    	<br>
    	部门编码：<input  type="text" id="deptNo">
    	<button onclick="queryDeptXml()">查询xml</button>
    	<button onclick="queryDeptJson()">查询Json</button>
    	<br>
    	<br>
    	<br>
    	<br>
    	<hr>
    	部门列表<button onclick="queryDeptArray(1)">查询部门列表</button>
    	<table class="deptInfo">
         		<tr class="titleRow">
         			<td>部门编号</td>
         			<td>部门名称</td>
         			<td>部门图片</td>
         			<td>部门位置</td>
         			<td>部门负责人</td>
         			<td>操作列表</td>
         		</tr>
         		<tr>
         			<td colspan="6">
         				<a href="javascript:;" onclick="queryDeptArray(1)">首页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="javascript:;" onclick="queryDeptArray(1)">上一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="javascript:;" onclick="queryDeptArray(1)">下一页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<a href="javascript:;" onclick="queryDeptArray(1)">尾页</a> 
         				&nbsp;&nbsp;&nbsp;&nbsp;
         				<span></span>
         			</td>
         			
         			
         		</tr>	
         </table>
  </body>
</html>
