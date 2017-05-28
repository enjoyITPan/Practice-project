package com.unit;

import java.util.Collection;

import org.dom4j.*;

//用于获取xml文档里的元素
public interface ElementLoader {
	/**
	 * 加入一份xml文件里的所有bean
	 * @param doc
	 */
  void addElements(Document doc);
  /**
   * 根据id获取bean
   * @param id
   * @return
   */
  Element getElement(String id);
  /**
   * 获取所有bean
   * @return
   */
  Collection<Element> getElements();
}
