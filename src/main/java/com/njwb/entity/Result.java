package com.njwb.entity;
/**
 * action标签中的result标签
 * <result name="success" type="forward">/njwb/dept/dept.jsp</result>
 * @author Administrator
 *
 */
public class Result {
	/**
	 * result.name, 方法的返回值
	 */
	private String name;
	/**
	 * result.type, 页面跳转方式，forward/redirect
	 */
	private String type;
	/**
	 * 跳转的页面地址
	 */
	private String pagePath;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	@Override
	public String toString() {
		return "Result [name=" + name + ", type=" + type + ", pagePath="
				+ pagePath + "]";
	}
	
}
