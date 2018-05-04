package com.njwb.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.njwb.entity.Action;
import com.njwb.entity.Result;
/**
 * 1.解析mvc.xml，将action全部转为Action实体类，存储到容器中
 * 2.提供方法，可以从容器中根据key拿到Action对象
 * @author Administrator
 *
 */
public class ActionUtil {
	
	private static Logger log = Logger.getLogger(ActionUtil.class);
	
	//1.定义一个action的容器,key:action.name属性
	private static Map<String, Action> actionMap = new HashMap<String, Action>();
	
	//3.解析mvc.xml，将action全部转为Action实体类，存储到容器中
	static{
		parse();
	}
	
	//2.定义从容器中拿action的方法
	public static Action getAction(String key)
	{
		return actionMap.get(key);
	}
	
	/**
	 * 解析xml
	 */
	private static void parse()
	{
		//拿解析器
		SAXReader reader = new SAXReader();
		//解析xml，拿到Document对象
		try {
			Document doc = reader.read(ActionUtil.class
					.getClassLoader().getResourceAsStream("mvc.xml"));
			//拿根节点
			Element root = doc.getRootElement();
			//拿所有的action标签
			List<Element> actionList = root.elements();
			//循环所有的action标签，每个标签都会生成一个Action对象
			for (Element action : actionList) {
				Action actionInfo = new Action();
				//取name,class,method属性给action属性赋值
				actionInfo.setName(action.attributeValue("name"));
				actionInfo.setClassPath(action.attributeValue("class"));
				actionInfo.setMethod(action.attributeValue("method"));
				//拿当前action标签的所有子标签（result标签）
				List<Element> resultList = action.elements();
				if(resultList == null || resultList.size() == 0)
				{
					log.info("当前action，没有子标签，actionName:" + actionInfo.getName());
				}
				else
				{
					//循环所有的result标签，每一个标签都会生成一个result对象，
					//将所有的result对象放入action中的result容器，也就是action.resultMap
					Map<String, Result> resultMap = new HashMap<String, Result>();
					for (Element result : resultList) {
						Result resultBean = new Result();
						resultBean.setName(result.attributeValue("name"));
						resultBean.setType(result.attributeValue("type"));
						resultBean.setPagePath(result.getTextTrim());
						
						resultMap.put(resultBean.getName(), resultBean);
					}
					//把填充好的容器，丢给actionInfo
					actionInfo.setResultMap(resultMap);
				}
				
				//Action对象放入容器actionMap
				actionMap.put(actionInfo.getName(), actionInfo);
			}
			
		} catch (DocumentException e) {
			// e.printStackTrace();
			log.error("解析mvc.xml失败，检查文件名，或者是路径，或者是xml的格式", e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ActionUtil.getAction("addDept"));
	}
}
