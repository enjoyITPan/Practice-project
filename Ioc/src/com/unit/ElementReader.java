package com.unit;

import java.util.List;

import org.dom4j.Element;

import com.unit.impl.PropertyElement;

public interface ElementReader {
	/**
	 * �ж��Ƿ����ӳټ���
	 * @param element
	 * @return
	 */
	boolean isLazy(Element element);
    
	/**
	 * ��ȡһ��bean �µ� constructor-org
	 * @param element
	 * @return
	 */
	List<Element> getConstructorElements(Element element);
	
	/**
	 * ����һ��bean�µ�����propertyԪ��
	 * @param element
	 * @return
	 */
	List<Element> getPropertyElements(Element element);
	
	/**
	 * ����bean�µ�constructor�µ�����ֵ������ref��value��
	 * @param element
	 * @return
	 */
	List<DataElement> getConstructorValue(Element element);
	
    /**
     * ���bean�µ�Property�µ�����ֵ������ref��value��
     * @param element
     * @return
     */
	List<PropertyElement> getPropertyValue(Element element);
    
	/**
	 * �õ�bean�µ�������Ϊname������ֵ
	 * @param element
	 * @param name
	 * @return
	 */
	String getAttribute(Element element, String name);
   /**
    * �Ƿ��ǵ���ģʽ
    * @param element
    * @return
    */
	boolean isSingleton(Element element);
    
	/**
	 * ����һ��bean��Ӧ��Autowire����
	 * @param element
	 * @return
	 */
	Autowire getAutowire(Element element);
   
}
