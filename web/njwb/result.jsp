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
		
		switch(resultCode)
		{
			
			case 1001:
				alert("未登陆，或登陆失败，请重新登陆！");
				window.parent.location.href = "njwb/login.jsp";
				break;
			case 1055:
				alert("已退出");
				window.parent.location.href = "njwb/login.jsp";
				break;
			case 1051:
				alert("原始密码或新密码与确认密码不一致！");
				location.href = "njwb/rePwd/rePwd.jsp";
				break;	
			case 1002:
				alert("添加失败，参数不合法");
				location.href = "queryAllByPage.action";
				break;
			case 1054:
				alert("删除失败，有关联，不能删除！");
				location.href = "queryAllByPage.action";
				break;
			case 1052:
				alert("修改失败");
				location.href = "njwb/rePwd/rePwd.jsp";
				break;
				
			case 1038:
				alert("添加失败，参数不合法");
				location.href = "queryAllRoByPage.action";
				break;
			case 1047:
				alert("添加失败，参数不合法");
				location.href = "queryAllPerByPage.action";
				break;
			case 1042:
				alert("添加失败，参数不合法");
				location.href = "queryAllRoByPage.action";
				break;
			case 1048:
				alert("修改失败，参数不合法");
				location.href = "njwb/rePwd/rePwd.jsp";
				break;
			case 0017:
				alert("已提交，不能修改17");
				location.href = "queryAllHdByPage.action";
				break;
			
			case 0022:
				alert("已提交，不能修改22");
				location.href = "queryAllAcByPage.action";
				break;
			case 1019:
				alert("删除失败");
				location.href = "queryAllAcByPage.action";
				break;
			case 1045:
				alert("删除失败");
				location.href = "queryAllPerByPage.action";
				break;
			case 1014:
				alert("添加失败，数据库异常,请检查编码是否重复，或者联系管理员");
				location.href = "queryAllHdByPage.action";
				break;
			case 1021:
				alert("添加失败，数据库异常,请检查编码是否重复，或者联系管理员");
				location.href = "queryAllAcByPage.action";
				break;
			case 1028:
				alert("添加失败，数据库异常,请检查编码是否重复，或者联系管理员");
				location.href = "queryAllAcByPage.action";
				break;
			case 1036:
				alert("添加失败，数据库异常,请检查编码是否重复，或者联系管理员");
				location.href = "queryAllRoByPage.action";
				break;
			case 1043:
				alert("添加失败，数据库异常,请检查编码是否重复，或者联系管理员");
				location.href = "queryAllPerByPage.action";
				break;
			case 1004:
				alert("添加失败，名称重复");
				location.href = "queryAllByPage.action";
				break;
			case 1046:
				alert("添加失败，名称重复");
				location.href = "queryAllRoByPage.action";
				break;
			case 1013:
				alert("添加失败，名称重复");
				location.href = "queryAllHdByPage.action";
				break;
			case 1020:
				alert("添加失败，名称重复");
				location.href = "queryAllAcByPage.action";
				break;
			case 1056:
				alert("添加成功");
				location.href = "queryAllPerByPage.action";
				break;
			case 1026:
				alert("添加失败，名称重复");
				location.href = "queryAllUsByPage.action";
				break;
			case 1026:
				alert("修改失败，某些不能为空");
				location.href = "queryAllUsByPage.action";
				break;
			case 1027:
				alert("员工或角色找不到！无法添加");
				location.href = "queryAllUsByPage.action";
				break;
			case 1005:
				alert("删除失败");
				location.href = "queryAllByPage.action";
				break;
			case 1006:
				alert("更新失败");
				location.href = "queryAllByPage.action";
				break;
			case 1015:
				alert("更新失败");
				location.href = "queryAllHdByPage.action";
				break;
			case 1049:
				alert("修改密码成功，请重新登录");
				location.href = "exit2.action";
				break;
			case 1032:
				alert("员工或角色找不到，不能修改！");
				location.href = "queryAllUsByPage.action";
				break;
			case 1023:
				alert("更新失败");
				location.href = "queryAllAcByPage.action";
				break;
			case 1033:
				alert("更新失败");
				location.href = "queryAllUsByPage.action";
				break;
			case 1050:
				alert("修改密码失败");
				location.href = "njwb/rePwd/rePwd.jsp";
				break;
			case 1044:
				alert("更新失败");
				location.href = "queryAllPerByPage.action";
				break;
			case 1035:
				alert("更新失败");
				location.href = "queryAllRoByPage.action";
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
			case 1011:
				alert("删除请假信息失败");
				location.href = "queryAllHdByPage.action";
				break;
			case 1111:
				alert("删除账户信息失败");
				location.href = "queryAllUsByPage.action";
				break;
			case 1034:
				alert("删除角色信息失败");
				location.href = "queryAllRoByPage.action";
				break;
			case 0000:
				alert("操作成功");
				location.href = "queryAllByPage.action";
				break;
			case 16:
				alert("操作成功");
				location.href = "queryAllHdByPage.action";
				break;
			case 1040:
				alert("操作成功");
				location.href = "queryAllPerByPage.action";
				break;
			case 1030:
				alert("操作成功");
				location.href = "queryAllUsByPage.action";
				break;
			case 1030:
				alert("操作成功");
				location.href = "queryAllRoByPage.action";
				break;
			case 1039:
				alert("操作成功");
				location.href = "queryAllRoByPage.action";
				break;
			case 9999:
				alert("操作失败");
				location.href = "queryAllByPage.action";
				break;
			case 12:
				alert("操作失败");
				location.href = "queryAllHdByPage.action";
				break;
			case 1041:
				alert("操作失败");
				location.href = "queryAllPerByPage.action";
				break;
			case 24:
				alert("操作失败");
				location.href = "queryAllUsByPage.action";
				break;
			case 1037:
				alert("操作失败");
				location.href = "queryAllRoByPage.action";
				break;
			case 18:
				alert("操作成功");
				location.href = "queryAllAcByPage.action";
				break;
			case 1057:
				alert("已有，不用增加");
				location.href = "queryAllPerByPage.action";
				break;
			case 1060:
				alert("名称重复，不能修改");
				location.href = "queryAllRoByPage.action";
				break;
			case 1061:
				alert("有关联，不能更新");
				location.href = "queryAllRoByPage.action";
				break;
			case 1062:
				alert("日期格式不正确");
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
