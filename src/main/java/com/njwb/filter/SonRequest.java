package com.njwb.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.njwb.util.StringUtil;

/**
 * 用于字符集的get方式提交 重写getParameter方法
 * 继承HttpServletRequest的实现类，HttpServletRequestWrapper
 * 
 * @author Administrator
 * 
 */
public class SonRequest extends HttpServletRequestWrapper {

	/**
	 * request中的取值，对象中的内容，肯定还是来源于原来的request
	 * 
	 * @param request
	 */
	public SonRequest(HttpServletRequest request) {
		super(request);

	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		// 首先需要拿到原始的参数值
		// 调用父类的获取参数方法，拿到原始的参数值
		//因为用了终极大招（在sevlet里配置了Enconding）所以这里就不调用了
		String value = super.getParameter(name);
		try {
			//对值进行转码然后返回
			value = StringUtil.isEmpty(value) ? "" : new String(
					value.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			value = "";
		}
		return value;
	}
}
