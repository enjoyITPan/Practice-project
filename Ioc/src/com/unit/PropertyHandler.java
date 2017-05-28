package com.unit;

import java.lang.reflect.Method;
import java.util.Map;

import com.unit.impl.BeanCreateException;

public interface PropertyHandler {
	/**
	 * 为对象属性进行set()赋值，Map里保存的是从xml文件里获取的
	 * property元素的name和value值
	 * @param obj
	 * @param properties
	 * @return
	 * @throws NoSuchMethodException 
	 */
	Object setProperties(Object obj, Map<String, Object> properties) throws NoSuchMethodException;
	
	// 返回一个对象里的所有set方法，封装成map,key为属性名称， value为一个方法对象
	Map<String,Method> getSetterMethodsMap(Object obj);
	
	/**
	 * 执行方法
	 * @param object
	 * @param argBean
	 * @param method
	 * @throws BeanCreateException 
	 */
	void executeMethod(Object object,Object argBean , Method method) throws BeanCreateException;
}
