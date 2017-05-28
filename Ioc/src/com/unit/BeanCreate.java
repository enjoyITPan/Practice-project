package com.unit;

import java.util.List;

import com.unit.impl.BeanCreateException;

public interface BeanCreate {
	/**
	 * 无参的构造方法
	 * @param className
	 * @return
	 * @throws BeanCreateException
	 */
   Object createBeanUseDefaultConstruct(String className) throws BeanCreateException;
   
   /**
    * 带参数的构造方法
    * @param className
    * @param args
    * @return
    * @throws BeanCreateException
    */
   Object createBeanUseDefaultConstruct(String className,List<Object> args) throws BeanCreateException;
}
