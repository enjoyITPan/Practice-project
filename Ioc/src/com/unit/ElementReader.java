package com.unit;

import java.util.List;

import org.dom4j.Element;

import com.unit.impl.PropertyElement;

public interface ElementReader {
	/**
	 * 判断是否是延迟加载
	 * @param element
	 * @return
	 */
	boolean isLazy(Element element);
    
	/**
	 * 获取一个bean 下的 constructor-org
	 * @param element
	 * @return
	 */
	List<Element> getConstructorElements(Element element);
	
	/**
	 * 返回一个bean下的所有property元素
	 * @param element
	 * @return
	 */
	List<Element> getPropertyElements(Element element);
	
	/**
	 * 返回bean下的constructor下的所有值（包括ref、value）
	 * @param element
	 * @return
	 */
	List<DataElement> getConstructorValue(Element element);
	
    /**
     * 获得bean下的Property下的所有值（包括ref、value）
     * @param element
     * @return
     */
	List<PropertyElement> getPropertyValue(Element element);
    
	/**
	 * 得到bean下的属性名为name的属性值
	 * @param element
	 * @param name
	 * @return
	 */
	String getAttribute(Element element, String name);
   /**
    * 是否是单例模式
    * @param element
    * @return
    */
	boolean isSingleton(Element element);
    
	/**
	 * 返回一个bean对应的Autowire对象
	 * @param element
	 * @return
	 */
	Autowire getAutowire(Element element);
   
}
