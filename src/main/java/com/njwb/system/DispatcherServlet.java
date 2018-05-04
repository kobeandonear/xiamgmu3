package com.njwb.system;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.njwb.entity.Action;
import com.njwb.entity.Result;

/**
 * 核心分发器 1.首先是一个自定义类 2.需要接收tomcat给的请求，所以，核心分发器是一个servlet
 * 
 * @author Administrator
 * 
 */
public class DispatcherServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(DispatcherServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.拦截所有调用后台代码的请求（比如查询部门，比如查询用户，但是，jsp,js，png等这些具体的资源文件就不需要拦截）
		String uri = req.getRequestURI();
		log.info("接收到后台请求，uri:" + uri); //

		// 2.根据请求去找这个请求对应的需要调用的类，方法
		// // /teacher_serlvet04/login.action
		// 2.1 解析地址，将请求地址拿出来 login
		String reqPath = uri.substring(req.getContextPath().length() + 1,
				uri.lastIndexOf("."));

		// 2.2 拿请求地址到映射文件（mvc.xml）中找对应的action
		/*
		 * <action name="queryAllDept"
		 * class="com.njwb.controller.DeptController" method="queryAll"> <result
		 * name="success" type="forward">/njwb/dept/dept.jsp</result> <result
		 * name="error" type="forward">/njwb/result.jsp</result> </action>
		 */
		Action action = ActionUtil.getAction(reqPath);
		if (action == null) {
			log.info("请求地址没有对应的action配置，uri:" + uri + ",requestPath:" + reqPath);
			return;
		}
		try {
			// 3.需要调用这个类的指定方法（先生成指定类的实例，然后调用指定方法）（使用反射来做）
			// 3.1 拿到class属性指向的类地址，然后newInstance,生成实例
			/*
			 * 交给对象工厂去做，我需要实例，找对象工厂要 1.先把controller配置到bean.xml 2.找工厂要，
			 * ApplicationContext.getBean(key);
			 * 
			 * Class clazz = Class.forName(action.getClassPath()); Object obj =
			 * clazz.newInstance();
			 */
			//从对象工厂中拿出controller实例
			Object obj = ApplicationContext.getBean(action.getClassPath());
			// 3.2 拿到这个类中的方法对象（类，方法名，参数类型）
			Method method = obj.getClass().getDeclaredMethod(
					action.getMethod(), HttpServletRequest.class,
					HttpServletResponse.class);
			// 3.3 执行这个方法，拿到方法的返回值
			// userController.login(request,response);
			String result = (String)method.invoke(obj, req,resp);

			// 4.根据方法的返回值，来进行页面跳转（页面跳转地址，页面跳转方式，加到配置文件中）
			// 4.1 根据方法的返回值，在mvc.xml中，当前的action里面匹配的result标签
			// <result name="success" type="forward">/njwb/dept/dept.jsp</result>
			// <result name="error" type="forward">/njwb/result.jsp</result>
			Map<String, Result> resultMap = action.getResultMap();
			if(resultMap == null || resultMap.get(result) == null)
			{
				log.info("这个action没有result标签，或者是方法的返回值没有找到匹配的result标签，actionName:" 
							+ action.getName() + ",result:" + result);
				return;
			}
			// 4.2 进行页面跳转
			Result resultBean = resultMap.get(result);
			if("forward".equals(resultBean.getType()))
			{
				//转发
				req.getRequestDispatcher(resultBean.getPagePath()).forward(req, resp);
				
			}else if("redirect".equals(resultBean.getType()))
			{
				log.info("德国和宽容的人了"+req.getContextPath() + resultBean.getPagePath());
				//重定向
				resp.sendRedirect(req.getContextPath() + resultBean.getPagePath());
				
			}else
			{
				log.info("没有对应的跳转类型，actionName:" + action.getName() + ",result:" + result);
			}
			
		} catch (NoSuchMethodException e) {
			log.info("拿方法对象出错，检查方法名称，方法参数，action:" + action,e );
		} catch (SecurityException e) {
			log.info("拿方法对象出错，检查方法名称，方法参数，action:" + action,e );
		} catch (IllegalAccessException e) {
			log.info("执行方法出错，action:" + action,e );
		} catch (IllegalArgumentException e) {
			log.info("执行方法出错，action:" + action,e );
		} catch (InvocationTargetException e) {
			log.info("执行方法出错，action:" + action,e );
		}
	};

}
