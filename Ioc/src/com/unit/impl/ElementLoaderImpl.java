package com.unit.impl;

import java.util.Collection;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.Element;

import com.unit.ElementLoader;

public class ElementLoaderImpl implements ElementLoader {
    private Map<String,Element> elements = new HashMap<String,Element>();
	@Override
	public void addElements(Document doc) {
	 //�Ȼ�ȡ��Ԫ�أ�Ȼ���ȡ��Ԫ���µ�����beanԪ��
	  List<Element> eles = doc.getRootElement().elements();
	  //��beanԪ�ص�id��Ԫ�ؽ��а�
	  for(Element e:eles){
		  String id = e.attributeValue("id");
		  elements.put(id, e);
	  }
	}

	@Override
	public Element getElement(String id) {
		return elements.get(id);
	}

	@Override
	public Collection<Element> getElements() {
		return this.elements.values();
	}

}
