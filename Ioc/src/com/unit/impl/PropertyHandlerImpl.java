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
     * 将set方法还原，返回属性名称
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
	 * 获得所有的setter方法
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
		//获取方法的参数类型
		Class[] parameterTypes =method.getParameterTypes();
		//如果参数数量不为1，则不执行该方法
		try{
		if(parameterTypes.length==1){
			//判断参数类型是否一致
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
		// 获得参数obj对应的类类型
		Class clazz = obj.getClass();
		try {
			for (String key : properties.keySet()) {
				// 获得方法名
				String setterName = getSetterMethodName(key);
				// 获得参数类型
				Class argClass = getClass(properties.get(key));
				// 获得set方法的对象
				Method setterMethod = getSetterMethod(clazz, setterName,
						argClass);
				// 通过反射调用对应的set()方法
				setterMethod.invoke(obj, properties.get(key));
			}
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 得到setter方法的Method对象
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
	 * 遍历所有的方法，判断方法中的参数与argClass是否是同一类型
	 * 
	 * @param clazz
	 * @param methods
	 * @return
	 */
	private Method findMethod(Class clazz, List<Method> methods) {
		for (Method m : methods) {
			// 判断参数类型与方法的参数类型是否一致
			if (isMethodArgs(m, clazz)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * 判断一个方法的参数类型是否与clazz类型一样，有可能clazz是方法参数类型的实现类型
	 * 
	 * @param m
	 * @param clazz
	 * @return
	 */
	private boolean isMethodArgs(Method m, Class clazz) {
		// 得到方法的所有参数类型
		Class[] c = m.getParameterTypes();
		// 如果方法的参数不是1个，则表示该方法不是我们需要的
		if (c.length == 1) {
			try {
				// 将参数类型clazz，与方法中的参数类型进行强制转换，成功就返回true
				clazz.asSubclass(c[0]);
				return true;
			} catch (ClassCastException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 得到所有名字为setterName并且只有一个参数的方法
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
	 * 根据方法名和参数类型获得方法，如果没有就返回null
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
	 * 返会对应的set()函数的函数全称
	 * 
	 * @param propertyName
	 * @return
	 */
	private String getSetterMethodName(String propertyName) {
		return "set" + upperCaseFirstWord(propertyName);
	}

	/**
	 * 将字符变为大写
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
