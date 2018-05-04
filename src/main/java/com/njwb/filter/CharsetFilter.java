package com.njwb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 设置字符集编码
 * @author Administrator
 *
 */
public class CharsetFilter implements Filter{
	private Logger log = Logger.getLogger(CharsetFilter.class);
	private String encoding = "";
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getServletContext().getInitParameter("charsetEncoding");
		log.info("初始化编码格式");
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		//log.info("字符集过滤器");
		//设置一下字符集
		//get，post?
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		if("POST".equals(request.getMethod()))
		{
			request.setCharacterEncoding(encoding);
		}
		else if("GET".equals(request.getMethod()))
		{
			//想办法对request进行偷梁换柱
			//request对象为重写了getParameter方法的对象
//			SonRequestImpl实现了HttpServletRequest,所以类型可以互转。
			//实现接口的时候，在getParameter方法中，进行了转码
//			SonRequestImpl impl = new SonRequestImpl(request);
//			request = impl;
			
			//实现接口，需要实现的方法太多，javaEE中提供了HttpServletRequest的实现类
			//
			log.info("如果是get方式提交，那么将request对象偷梁换柱");
			SonRequest sonReq = new SonRequest(request);
			//request = sonReq;
			
		}
		
		//流程继续
		chain.doFilter(request, response);
		
	}


}
