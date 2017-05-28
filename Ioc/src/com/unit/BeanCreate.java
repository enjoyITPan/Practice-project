package com.unit;

import java.util.List;

import com.unit.impl.BeanCreateException;

public interface BeanCreate {
	/**
	 * �޲εĹ��췽��
	 * @param className
	 * @return
	 * @throws BeanCreateException
	 */
   Object createBeanUseDefaultConstruct(String className) throws BeanCreateException;
   
   /**
    * �������Ĺ��췽��
    * @param className
    * @param args
    * @return
    * @throws BeanCreateException
    */
   Object createBeanUseDefaultConstruct(String className,List<Object> args) throws BeanCreateException;
}
