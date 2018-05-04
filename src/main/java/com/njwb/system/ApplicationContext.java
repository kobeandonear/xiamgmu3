package com.njwb.system;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 对象工厂方法 
 * 1.提供容器（Map） 
 * 2.解析bean.xml,生成每个bean的实例，放入容器 
 * 3.提供一个可以获取bean实例的方法
 * 
 * @author Administrator
 * 
 */
public class ApplicationContext {

	private static Logger log = Logger.getLogger(ApplicationContext.class);
	// 定义容器
	private static Map<String, Object> objMap = new HashMap<String, Object>();

	static {
		// 调用xml解析
		parse();
	}

	// 提供一个可以获取bean实例的方法
	public static Object getBean(String key) {
		Object obj = objMap.get(key);
		if (obj == null) {
			log.info("从容器中拿不到实例,key:" + key);
		}
		return obj;
	}

	/**
	 * 2.解析bean.xml,生成每个bean的实例，放入容器
	 */
	private static void parse() {
		// 1.解析bean.xml，拿到所有bean节点集合
		// 拿解析器
		SAXReader reader = new SAXReader();
		// 用解析器解析xml，拿到docuemnt对象
		try {
			Document doc = reader.read(ApplicationContext.class
					.getClassLoader().getResourceAsStream("bean.xml"));
			Element root = doc.getRootElement();
			List<Element> beanList = root.elements();
			if (beanList == null || beanList.size() == 0) {
				log.info("bean.xml中，beans根节点下，没有子节点");
				return;
			}
			// 2.循环bean节点集合，每个bean就是一个实例
			for (Element bean : beanList) {

				// 2.1 取出bean中属性id,class的值
				String id = bean.attributeValue("id");
				String classPath = bean.attributeValue("class");
				// 2.2 通过class值，生成对应的类实例对象
				// 拿到clazz对象
				try {
					Class clazz = Class.forName(classPath);
					// 类地址生的实例对象
					Object obj = clazz.newInstance();

					// -----------依赖对象赋值------start-----
					// 1.判断是否有依赖对象
					// 拿bean标签下所有的property标签
					List<Element> propertyList = bean.elements("property");
					// 集合不为空，则证明有依赖对象
					if (propertyList != null && propertyList.size() > 0) {
						// 2.如果有依赖对象，取出property标签集合
						// 3.循环property标签，给每一个属性进行赋值
						for (Element property : propertyList) {
							// 3.1 取出property标签中name,setMethod,ref 属性
							// 对象需要赋值的属性名称
							String proName = property.attributeValue("name");
							// 调用的set方法中传入的实参。实参在容器中的keyID
							String proRef = property.attributeValue("ref");
							// setMethod：只是用于确定调用的set方法名，此处可以不取值，使用set + 属性名拼接
							String setMethod = property
									.attributeValue("setMethod");
							//refObj: 代表实参
							Object refObj = objMap.get(proRef); 
//							Object refObj =  ApplicationContext.getBean(proRef);
							// 3.2 根据ref到工厂容器中拿实例，判断有没有，日志打印：dao还未生成
							if (refObj == null) {
								log.info("被依赖的对象还没有生成实例，请检查先后顺序。beanId:" + id
										+ ",proRef:" + proRef);
								//流程不结束
								//先生成proRef对应的实例,然后丢入容器
								
							} else {
								// 3.3 如果有实例:调用 obj.setXXXDao(xxxDao)
								//调用，用反射，拿到obj对象中set方法对象
								//拿方法对象，需要  类对象，方法名，形参类型（bean.xml中没有告诉我们形参类型是什么，但是，有告诉我们实参）
								//组装set方法
								String methodName = "set" + proName.substring(0,1).toUpperCase() + proName.substring(1);
								//objMap.get(proRef).getClass().getInterfaces()
								//        实参.getClass()  -->拿实参的类型 com.njwb.dao.impl.UserDaoImpl
//						        //        实参.getClass().getInterfaces() -->拿到的是impl的接口类型： com.njwb.dao.UserDao
								try {
									//Class : 除了可以调用newInstance()（实际上就是调用的这个类的无参构造）生成实例，还可以拿到这个类所有的东西
									//比如，所有的属性，比如所有的方法，这个时候我们的属性，方法也可以理解为是对象（属性对象，方法对象）
									
									//method: setUserDao方法对象
									Method method = obj.getClass().getDeclaredMethod(methodName, refObj.getClass().getInterfaces());
									
									//运行这个方法，等同于写代码   userServiceImpl.setUserDao(userDao) 
									//obj:运行这个方法的实例，也就是userServiceImpl，也就是当前这个bean节点，生成的实例--》obj
									//第二个参数args : 实参，也就是userDao
									method.invoke(obj, refObj);
									
								} catch (NoSuchMethodException e) {
									log.info("给对象的依赖属性赋值失败，拿set方法出错，beanID:" + id + ",proName:" + proName,e);
								} catch (SecurityException e) {
									log.info("给对象的依赖属性赋值失败，beanID:" + id + ",proName:" + proName,e);
								} catch (IllegalArgumentException e) {
									log.info("给对象的依赖属性赋值失败，beanID:" + id + ",proName:" + proName,e);
								} catch (InvocationTargetException e) {
									log.info("给对象的依赖属性赋值失败，beanID:" + id + ",proName:" + proName,e);
								}
							}

						}

					}

					// -----------依赖对象赋值------end-----

					// 2.3 将实例放入容器，id属性值，作为key, 实例对象作为value
					objMap.put(id, obj);

				} catch (ClassNotFoundException e) {
					log.error("生成类地址的class对象失败，请检查类地址是否正确，beanID:" + id, e);
					// 如果不做处理，那么流程继续生成实例对象放入容器
				} catch (InstantiationException e) {
					log.error("生成类地址的class对象实例失败，请检查类地址是否正确，类是否有无参构造，beanID:"
							+ id, e);
				} catch (IllegalAccessException e) {
					log.error("生成类地址的class对象实例失败，请检查类地址是否正确，类是否有无参构造，beanID:"
							+ id, e);
				}
			}

		} catch (DocumentException e) {
			// e.printStackTrace();
			log.error("解析bean.xml失败，检查文件名，或者是路径，或者是xml的格式", e);
		}
	}
}
