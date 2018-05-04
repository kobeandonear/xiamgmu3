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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function checkNameText(){
			//拿到输入的部门名称
			var deptName = document.getElementById("deptName").value;
			//ajax调用后台，使用XMLHttpResquest对象来请求后台
			var xmlRequest = new XMLHttpRequest();
			//请求后台，1，请求地址， 2.请求方式（get/post） 
			//xmlRequest.open("get","checkNameText.action?deptName=" + deptName,false);
			xmlRequest.open("get","checkNameText.action?deptName="+deptName,true);
			xmlRequest.send();
			//拿后台的响应
			//alert("拿后台响应"); //加上，能拿到后台的responseText,去掉，拿不到
			//var result = xmlRequest.responseText;
			//alert("后台响应结果：" + result);
			//等异步线程成功拿到后台的响应之后再来取值
			//XMLHttpRequest这个对象中，有个请求状态改变的事件。
			//给这个状态改变的事件绑定一个匿名函数，判断，请求结束
			xmlRequest.onreadystatechange = function(){
				if(xmlRequest.readyState == 4 && xmlRequest.status == 200){
					var result = xmlRequest.responseText;
					if(result == "success"){
						alert("名称可用");
					}else{
						alert("名称重名了，请换一个！");
					}			
				}
			};
		}
	function queryDeptXml()
		{
			var deptNo = document.getElementById("deptNo").value;
			var xmlRequest = new XMLHttpRequest();
			xmlRequest.open("POST","queryDeptXml.action",true);
			//post需要设置提交方式
			xmlRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlRequest.send("deptNo=" + deptNo);
			//拿到后台响应之后，再来取值
			xmlRequest.onreadystatechange = function(){
				if(xmlRequest.readyState == 4 && xmlRequest.status == 200)
				{
					var result = xmlRequest.responseXML;
					//alert(result); //[object XMLDocument]
					alert(result.getElementsByTagName("deptName")[0].textContent);
				}
			};
		}
		
		//后台返回json格式的字符串
		function queryDeptJson()
		{
			var deptNo = document.getElementById("deptNo").value;
			var xmlRequest = new XMLHttpRequest();
			xmlRequest.open("POST","queryDeptJson.action",true);
			//post需要设置提交方式
			xmlRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlRequest.send("deptNo=" + deptNo);
			//拿到后台响应之后，再来取值
			xmlRequest.onreadystatechange = function(){
				if(xmlRequest.readyState == 4 && xmlRequest.status == 200)
				{
					var dept = xmlRequest.responseText;
					//alert(dept);//{deptName:"研发",deptNo:"A0003",deptLoc:"326"}
					//将json格式的字符串转换成json对象
					dept = eval("(" + dept + ")");
					alert(JSON.stringify(dept));
					alert(dept.deptName);
				}
			};
		}
	</script>
  </head>
  
  <body>
   JavaScript的ajax<br>
   		部门名称：<input onblur="checkNameText()" type="text" id="deptName">
   		<br>
    	<br>
    	部门编码：<input  type="text" id="deptNo">
    	<button onclick="queryDeptXml()">查询xml</button>
    	<button onclick="queryDeptJson()">查询Json</button>
  </body>
</html>
