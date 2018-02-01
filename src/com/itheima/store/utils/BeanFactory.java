package com.itheima.store.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 一个用于产生类对象的工程
 * 
 * @author xt
 *
 */
public class BeanFactory {
	// 获取对象的方法
	public static  Object getBean(String id) {
		// 使用do4j解析xml
		SAXReader reader = new SAXReader();
		try {
			Document read = reader
					.read(BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml"));
			// 使用xpath获取classname
			Element node = (Element) read.selectSingleNode("//bean[@id='" + id + "']");
			String value = node.attributeValue("class");
			// 使用反射创建对象实例
			Class<?> class1 = Class.forName(value);
			return class1.newInstance();
		} catch (DocumentException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("实例化失败");
		}
	}
}
