package com.njwb.system;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 获取系统配置文件信息system.properties
 * 
 * @author Administrator
 * 
 */
public class SystemProterties {
	private static Logger log = Logger.getLogger(SystemProterties.class);

	private static Properties properties = new Properties();

	static {
		try {
			// 加载配置文件
			properties.load(SystemProterties.class.getClassLoader()
					.getResourceAsStream("system.properties"));
		} catch (IOException e) {
			log.info("加载system.properties配置文件失败", e);

		}
	}

	/**
	 * 根据key获取配置项的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		//带默认值： properties.getProperty(key, defaultValue);
		return properties.getProperty(key);  
	}

	public static void main(String[] args) {
		System.out.println(SystemProterties.getValue("pageSize"));
	}
}
