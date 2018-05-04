package com.njwb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import sun.security.krb5.Config;

import com.njwb.constant.ErrorCode;

public class AuthFilter implements Filter{
	private Logger log = Logger.getLogger(AuthFilter.class);
	private String noUri = "";
	public void init(FilterConfig config) throws ServletException {
		noUri = config.getInitParameter("noAuthUrl");
		log.info("初始化");
	}
	

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String uri = request.getRequestURI();
		if(isNoAuth(uri)){
			chain.doFilter(req, resp);
		}else{
			if(request.getSession().getAttribute("loginUser")==null){
				log.info("doFilter:权限校验未通过uri:"+uri);
				request.setAttribute("resultCode", ErrorCode.LOGIN_FAIL);
				request.getRequestDispatcher("/njwb/result.jsp").forward(request, resp);
			}else{
				chain.doFilter(req,resp);
			}
		}
	}
	private boolean isNoAuth(String uri) {
		//String noUri = "login.jsp,loginServlet,.css,.js,.png,result.jsp";
		String[] noUris = noUri.split(",");
		for(String string:noUris){
			if(uri.endsWith(string)){
				return true;
			}
		}
		return false;
	}
	
	public void destroy() {
		log.info("销毁");
		
	}
}
 