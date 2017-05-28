package com.unit.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unit.PropertyHandler;

public class PropertyHandlerImpl implements PropertyHandler {
	
	@Override
	public Map<String, Method> getSetterMethodsMap(Object obj) {
		List<Method> methods = getSetterMethodList(obj);
		Map<String,Method> result = new HashMap<String,Method>();
		for(Method m:methods){
			String propertyName = getMethodNameWithOutSet(m.getName());
			result.put(propertyName, m);
		}
		return result;
	}
    /**
     * ��set������ԭ��������������
     * @param Methodname
     * @return
     */
	private String getMethodNameWithOutSet(String Methodname) {
		String propertyName = Methodname.replaceFirst("set", "");
		String firstword = propertyName.substring(0, 1);
		String lowerFirstWord = firstword.toLowerCase();
		return propertyName.replaceFirst(firstword, lowerFirstWord);
	}
    
	/**
	 * ������е�setter����
	 * @param obj
	 * @return
	 */
	private List<Method> getSetterMethodList(Object obj) {
		Class clazz = obj.getClass();
		Method[] methods = clazz.getMethods();
		List<Method> result = new ArrayList<Method>();
		for(Method m:methods){
			if(m.getName().startsWith("set"));
			result.add(m);
		}
		return result;
	}

	@Override
	public void executeMethod(Object object, Object argBean, Method method) throws BeanCreateException {
		//��ȡ�����Ĳ�������
		Class[] parameterTypes =method.getParameterTypes();
		//�������������Ϊ1����ִ�и÷���
		try{
		if(parameterTypes.length==1){
			//�жϲ��������Ƿ�һ��
			if(isMethodArgs(method,parameterTypes[0])){
				method.invoke(object, argBean);
			}
		}
		}catch(Exception e){
			throw new BeanCreateException("autowire faild" + e.getMessage());
		}

	}

	@Override
	public Object setProperties(Object obj, Map<String, Object> properties)
			throws NoSuchMethodException {
		// ��ò���obj��Ӧ��������
		Class clazz = obj.getClass();
		try {
			for (String key : properties.keySet()) {
				// ��÷�����
				String setterName = getSetterMethodName(key);
				// ��ò�������
				Class argClass = getClass(properties.get(key));
				// ���set�����Ķ���
				Method setterMethod = getSetterMethod(clazz, setterName,
						argClass);
				// ͨ��������ö�Ӧ��set()����
				setterMethod.invoke(obj, properties.get(key));
			}
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �õ�setter������Method����
	 * 
	 * @param clazz
	 * @param setterName
	 * @param argClass
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getSetterMethod(Class clazz, String setterName,
			Class argClass) throws NoSuchMethodException {
		Method argClassMethod = getMethod(clazz, setterName, argClass);
		if (argClassMethod == null) {
			List<Method> methods = getMethods(clazz, setterName);
			Method method = findMethod(clazz, methods);
			if (method == null) {
				throw new NoSuchMethodException(setterName);
			}
			return method;
		} else
			return argClassMethod;
	}

	/**
	 * �������еķ������жϷ����еĲ�����argClass�Ƿ���ͬһ����
	 * 
	 * @param clazz
	 * @param methods
	 * @return
	 */
	private Method findMethod(Class clazz, List<Method> methods) {
		for (Method m : methods) {
			// �жϲ��������뷽���Ĳ��������Ƿ�һ��
			if (isMethodArgs(m, clazz)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * �ж�һ�������Ĳ��������Ƿ���clazz����һ�����п���clazz�Ƿ����������͵�ʵ������
	 * 
	 * @param m
	 * @param clazz
	 * @return
	 */
	private boolean isMethodArgs(Method m, Class clazz) {
		// �õ����������в�������
		Class[] c = m.getParameterTypes();
		// ��������Ĳ�������1�������ʾ�÷�������������Ҫ��
		if (c.length == 1) {
			try {
				// ����������clazz���뷽���еĲ������ͽ���ǿ��ת�����ɹ��ͷ���true
				clazz.asSubclass(c[0]);
				return true;
			} catch (ClassCastException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * �õ���������ΪsetterName����ֻ��һ�������ķ���
	 * 
	 * @param clazz
	 * @param setterName
	 * @return
	 */
	private List<Method> getMethods(Class clazz, String setterName) {
		List<Method> result = new ArrayList<Method>();
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(setterName)) {
				Class[] c = m.getParameterTypes();
				if (c.length == 1) {
					result.add(m);
				}
			}
		}
		return result;
	}

	/**
	 * ���ݷ������Ͳ������ͻ�÷��������û�оͷ���null
	 * 
	 * @param clazz
	 * @param setterName
	 * @param argClass
	 * @return
	 */
	private Method getMethod(Class clazz, String setterName, Class argClass) {
		try {
			Method method = clazz.getMethod(setterName, argClass);
			return method;
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

	}

	/**
	 * �����Ӧ��set()�����ĺ���ȫ��
	 * 
	 * @param propertyName
	 * @return
	 */
	private String getSetterMethodName(String propertyName) {
		return "set" + upperCaseFirstWord(propertyName);
	}

	/**
	 * ���ַ���Ϊ��д
	 * 
	 * @param s
	 * @return
	 */
	private String upperCaseFirstWord(String s) {
		String firstWord = s.substring(0, 1);
		String UpperWord = firstWord.toUpperCase();
		return s.replaceFirst(firstWord, UpperWord);
	}

	private Class getClass(Object obj) {
		if (obj instanceof Integer) {
			return Integer.TYPE;
		} else if (obj instanceof Long) {
			return Long.TYPE;
		} else if (obj instanceof Boolean) {
			return Boolean.TYPE;
		} else if (obj instanceof Short) {
			return Short.TYPE;
		} else if (obj instanceof Double) {
			return Double.TYPE;
		} else if (obj instanceof Float) {
			return Float.TYPE;
		} else if (obj instanceof Character) {
			return Character.TYPE;
		} else if (obj instanceof Byte) {
			return Byte.TYPE;
		}
		return obj.getClass();
	}
}
