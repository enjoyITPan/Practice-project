package com.unit;

import java.lang.reflect.Method;
import java.util.Map;

import com.unit.impl.BeanCreateException;

public interface PropertyHandler {
	/**
	 * Ϊ�������Խ���set()��ֵ��Map�ﱣ����Ǵ�xml�ļ����ȡ��
	 * propertyԪ�ص�name��valueֵ
	 * @param obj
	 * @param properties
	 * @return
	 * @throws NoSuchMethodException 
	 */
	Object setProperties(Object obj, Map<String, Object> properties) throws NoSuchMethodException;
	
	// ����һ�������������set��������װ��map,keyΪ�������ƣ� valueΪһ����������
	Map<String,Method> getSetterMethodsMap(Object obj);
	
	/**
	 * ִ�з���
	 * @param object
	 * @param argBean
	 * @param method
	 * @throws BeanCreateException 
	 */
	void executeMethod(Object object,Object argBean , Method method) throws BeanCreateException;
}
