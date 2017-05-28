package com.unit.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.unit.BeanCreate;

/**
 * 构造器注入值
 * @author pan
 *
 */
public class BeanCreateImpl implements BeanCreate {

	@Override
	public Object createBeanUseDefaultConstruct(String className) throws BeanCreateException {
		try{
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(className);
			Object obj = clazz.newInstance();
			return obj;
		}catch(ClassNotFoundException e){
			throw new BeanCreateException("class not found" + e.getMessage());
		}catch(Exception e){
			throw new BeanCreateException(e.getMessage());
		}
	}

	@Override
	public Object createBeanUseDefaultConstruct(String className,
			List<Object> args) throws BeanCreateException {
		
		//返回参数的对应类类型
		Class[] argsClass = getArgsClasses(args);
		try{
			Class clazz = Class.forName(className);
			
			//通过className对应的类类型和参数列表寻找对应的构造函数
			Constructor constructor = findConstructor(clazz,argsClass);
			
			return constructor.newInstance(args.toArray());
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new BeanCreateException("class not found" + e.getMessage());
		}catch(Exception e){
			throw new BeanCreateException(e.getMessage());
		}
	}
	
	/*
	 * 获取对应参数的构造方法
	 */
    private Constructor getConstructor(Class clazz,Class[] argsClass){
		try{
			Constructor constructor = clazz.getConstructor(argsClass);
			return constructor;
		}catch(NoSuchMethodException e){
			return null;
		}
    }
	private Constructor findConstructor(Class clazz, Class[] argsClass) throws NoSuchMethodException {
		Constructor constructor = getConstructor(clazz, argsClass);
		if(constructor == null){
			Constructor[] constructors = clazz.getConstructors();
			for(Constructor e:constructors){
				Class[] constructorArgsClass = e.getParameterTypes();
				if(constructorArgsClass.length == argsClass.length){
					if(isSameArgs(argsClass,constructorArgsClass)){
						return e;
					}
				}
			}
		}else{
			return constructor;
		}
		throw new NoSuchMethodException("could not find any constructor");
	}

	private boolean isSameArgs(Class[] argsClass, Class[] constructorArgsClass) {
		for(int i=0;i<argsClass.length;i++){
			try{
				//将左边的转为参数的类类型，不匹配就会抛出异常
				argsClass[i].asSubclass(constructorArgsClass[i]);
				
				if(i==(argsClass.length-1)){
					return true;
				}
			}catch(Exception e){
				break;
			}
		}
		return false;
	}
    
	/*
	 * 获得参数类型
	 */
	private Class[] getArgsClasses(List<Object> args) {
		List<Class> result = new ArrayList<Class>();
		for(Object arg:args){
			result.add(getClass(arg));
		}
		Class[] a = new Class[result.size()];
		return result.toArray(a);
	}

	private Class getClass(Object obj) {
		if(obj instanceof Integer){
			return Integer.TYPE;
		}else if(obj instanceof Long){
			return Long.TYPE;
		}else if(obj instanceof Boolean){
			return Boolean.TYPE;
		}else if(obj instanceof Short){
			return Short.TYPE;
		}else if(obj instanceof Double){
			return Double.TYPE;
		}else if(obj instanceof Float){
			return Float.TYPE;
		}else if(obj instanceof Character){
			return Character.TYPE;
		}else if(obj instanceof Byte){
			return Byte.TYPE;
		}
		return obj.getClass();
	}

}
