package com.unit;

import java.util.Collection;

import org.dom4j.*;

//���ڻ�ȡxml�ĵ����Ԫ��
public interface ElementLoader {
	/**
	 * ����һ��xml�ļ��������bean
	 * @param doc
	 */
  void addElements(Document doc);
  /**
   * ����id��ȡbean
   * @param id
   * @return
   */
  Element getElement(String id);
  /**
   * ��ȡ����bean
   * @return
   */
  Collection<Element> getElements();
}
