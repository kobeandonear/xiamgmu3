<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'result.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		//alert("登陆失败，请重新登陆！");
		//location.href="njwb/login.jsp";
		//需要判断来源是啥，然后根据来源（使用标志：错误码）做不同的提示，和页面跳转
		var resultCode = <%=request.getAttribute("resultCode") %>;
		//alert(resultCode);
		switch(resultCode)
		{
			case 1001:
				alert("未登陆，或登陆失败，请重新登陆！");
				window.parent.location.href = "njwb/login.jsp";
				break;
			case 1002:
				alert("添加失败，参数不合法");
				location.href = "queryAllEpByPage.action";
				break;
			case 1003:
				alert("添加失败，数据库异常,请检查编码是否重复，或者联系管理员");
				location.href = "queryAllEpByPage.action";
				break;
			case 1004:
				alert("添加失败，名称重复");
				location.href = "queryAllEpByPage.action";
				break;
			case 1005:
				alert("删除失败");
				location.href = "queryAllEpByPage.action";
				break;
			case 1006:
				alert("更新失败");
				location.href = "queryAllEpByPage.action";
				break;
			case 1007:
				alert("登陆失效，请重新登陆！");
				//拿当前窗口对象的父对象。window.parent
				window.parent.location.href = "njwb/login.jsp";
				break;
			case 1010:
				alert("验证码输入错误，请重新登录！");
				//拿当前窗口对象的父对象。window.parent
				location.href = "njwb/login.jsp";
				break;
			case 0000:
				alert("操作成功");
				location.href = "queryAllEpByPage.action";
				break;
			
				
				
			case 9999:
				alert("操作失败");
				location.href = "queryAllEpByPage.action";
				break;
			default:
				alert("登陆失败，请重新登陆！");
				location.href="njwb/login.jsp";
				break;
		}
	</script>

  </head>
  
  <body>
    取值，resultCode:<%=request.getAttribute("resultCode") %><br>
    
    
  </body>
</html>