﻿<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.njwb.util.StringUtil"%>
<%@page import="com.njwb.constant.ErrorCode"%>
<%@page import="com.njwb.entity.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:if test="${loginUser==null}">
	<script type="text/javascript">
		alert("你没登录，回去重来！");
		location.href="login.jsp";
	</script>
</c:if>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+ 
			request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>main.html</title>
	<base href="<%=basePath%>">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>

    <style type="text/css">
  	.hide{
  		display: none;
  	
  	}
    </style>
   <script type="text/javascript">
   	$(function(){
   		  //找到所有的li,且class=menu
   		  //alert($("li[class='menu']").length);
   		  
   		  
   		  //存在的问题：一级菜单和二级菜单能正常的显示与隐藏，但是当点击二级菜单，发现二级菜单也跟着隐藏
   		  
   		  //吸取一个经验：在网页元素排版时，要兼顾后期的js操作
   		  //一个合理的网页布局，会让js获取元素时非常遍历，否则就很痛苦
   		  /**
   		  $("li[class='menu']").each(function(){
   		  	  $(this).click(function(){
   		  	      $(this).children(".hide").slideToggle();
   		  	  });
   		  
   		  });
   		  */
   		  
   		  //对所有的span标签设置单击事件
   		  
   		  //alert($("span").length);  4个
   		  
   		  //alert($("li[class='menu'] span").length);
   		  
   		  $("li[class='menu'] span").each(function(){
   		  		$(this).click(function(){
   		  			  //this代表的是span
   		  			  $(this).siblings(".hide").slideToggle();
   		  		
   		  		});
   		  
   		  });
   	
   	});
   		
   
   function exit(){
   			var result = window.confirm("确认要退出吗?");
			if(true == result){
				$.ajax({
					url :"exit.action",
					type :"POST",
					async : true,
					dataType : "text",
					success:function(result){
						if(result == "success")
						{						
							location.href="njwb/login.jsp";
						}else
						{
							alert("退出失败");								
						}							
					}					
				});
			}
		}
   </script> 
    
   
  </head>
  
  <body>
  			<%-- <% 
  			User user = (User)session.getAttribute(&quot;loginUser&quot;); 
	    	//最后需要判断userName是否有值，如果没有值，则没有登陆 
	    	if(user == null) 
	    	{ 
	    		//如果cookie没有，那么肯定是没有登陆了 
	    		request.setAttribute(&quot;resultCode&quot;, ErrorCode.LOGIN_FAIL); 
       			request.getRequestDispatcher(&quot;/njwb/result.jsp&quot;).forward(request, response); 
	    		return; 
	    	}  			 
  			--%>
  			<%--<%
   			//是否登陆校验
	    	String userName = request.getParameter("userName");
	    	//从cookie中拿到userName
	    	Cookie[] cookies =  request.getCookies();
	    	if(cookies == null)
	    	{
	    		//如果cookie没有，那么肯定是没有登陆了
	    		request.setAttribute("resultCode", ErrorCode.LOGIN_FAIL);
       			request.getRequestDispatcher("/njwb/result.jsp").forward(request, response);
	    		return;
	    	}
	    	for(Cookie cookie : cookies)
	    	{
	    		//拿cookie名称为userName的
	    		if("userName".equals(cookie.getName()))
	    		{
	    			//取出cookie的值赋给userName变量
	    			userName = cookie.getValue();
	    		}
	    	}
	    	
	    	//最后需要判断userName是否有值，如果没有值，则没有登陆
	    	if(StringUtil.isEmpty(userName))
	    	{
	    		//如果cookie没有，那么肯定是没有登陆了
	    		request.setAttribute("resultCode", ErrorCode.LOGIN_FAIL);
       			request.getRequestDispatcher("/njwb/result.jsp").forward(request, response);
	    		return;
	    	}
	    	
	    %>--%>
  	<div id = "mainDiv">
	  	<div id = "header">
	    	<div id = "logoDiv" class="lft">
	    		南京网博教育集团
	    	</div>
	    	<div id = "userDiv" class="rft">
	    		<%--<%
			    	//取参数
			    	//重定向是get方式请求
			    	//String userName = request.getParameter("userName");
			    	userName = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
			    %>
			    <%=user.getUserName() %>--%>${loginUser.userName },欢迎你
	    	</div>
	    </div>
	    <div id = "welcomeDiv">
	    	欢迎使用网博管理系统
	    </div>
	    
	    
	    <div id = "contentDiv">
	    	<div id = "content-left" class="lft">
	    		<ul>
	    			<!-- 循环一级菜单 -->
	    			<c:forEach items="${menuList }" var="menu">
	    			<li class="menu">
		    				<span>${menu.menuName }</span>
		    				<ul class="hide">
		    					<!-- 循环子菜单 -->
		    					<c:forEach items="${menu.sonMenuList }" var="sonMenu">
			    					<li class="menu-sub" ><a href="${sonMenu.hrefUrl }"  target="contentPage">${sonMenu.menuName }</a></li>
		    					</c:forEach>
		    					
		    				</ul>
		    			
		    			</li>
	    			
	    			
	    			</c:forEach>
	    			<!--  <li class="menu">
	    				<span>人事管理</span>
	    				<ul class="hide">
	    					<li class="menu-sub" ><a href="queryAllByPage.action"  id="deptManager"   target="contentPage">部门管理</a></li>
	    					<li class="menu-sub"><a href="queryAllEpByPage.action" id="deptManager" target="contentPage">员工管理</a></li>
	    					<li class="menu-sub"><a href="queryAllHdByPage.action" id="deptManager" target="contentPage">请假管理</a></li>
	    				</ul>
	    			
	    			</li>
	    			
	    			<li class="menu">
	    				<span>财务管理</span>
	    				<ul  class="hide">
	    					<li class="menu-sub"><a href="queryAllAcByPage.action"  id="deptManager"   target="contentPage">报销管理</a></li>
	    				</ul>
	    			
	    			</li>	    			
	    		    <li class="menu">
	    				<span>系统管理</span>
	    				<ul class="hide">
	    					<li class="menu-sub"><a href="queryAllUsByPage.action"  id="deptManager"   target="contentPage">账户维护</a></li>
	    					<li class="menu-sub"><a href="queryAllRoByPage.action"  id="deptManager"   target="contentPage">角色管理</a></li>
	    					<li class="menu-sub"><a href="queryAllPerByPage.action"  id="deptManager"   target="contentPage">权限管理</a></li>
	    					<li class="menu-sub"><a href="njwb/rePwd/rePwd.jsp"  id="deptManager"   target="contentPage">密码重置</a></li>
	    					<li class="menu-sub"><a href="exit2.action" id="deptManager"   target="contentPage">系统退出</a></li>
	    				</ul>
	    			
	    			</li>-->

	    		</ul>
	    		
	    		
	    	</div>
	    	
	    	<div id = "content-right" class="rft">
	    		<iframe src="" name="contentPage" scrolling="yes" frameborder="0" width="788px" height="470px">
	    		</iframe>
	    	</div>
	    </div>
	    
	    <div id = "footer">
	    	<span>&copy;版权归属南京网博江北总部</span>
	    </div>
  	
  	</div>
   
  </body>
</html>