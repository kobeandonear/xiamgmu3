package com.njwb.entity;

import java.util.Map;

/**
 * mvc.xml中action标签的实体类
 * <action name="queryAllDept" class="com.njwb.controller.DeptController" method="queryAll">
		<result name="success" type="forward">/njwb/dept/dept.jsp</result>
		<result name="error" type="forward">/njwb/result.jsp</result>
	</action>
 * @author Administrator
 *
 */
public class Action {
	/**
	 * 请求地址，action.name
	 */
	private String name;
	/**
	 * 类地址，action.class-->变为bean.xml中实例配置的beanID,用于找对象工厂要实例
	 */
	private String classPath;
	/**
	 * 方法名， action.method
	 */
	private String method;
	/**
	 * key: result.name ，方法的返回值
	 * value: 整个result对象
	 */
	private Map<String, Result> resultMap;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, Result> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Result> resultMap) {
		this.resultMap = resultMap;
	}
	@Override
	public String toString() {
		return "Action [name=" + name + ", classPath=" + classPath
				+ ", method=" + method + ", resultMap=" + resultMap + "]";
	}
	
	
}
