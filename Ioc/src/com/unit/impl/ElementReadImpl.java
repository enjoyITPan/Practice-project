package com.unit.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import com.unit.Autowire;
import com.unit.DataElement;
import com.unit.ElementReader;

public class ElementReadImpl implements ElementReader {

	@Override
	public String getAttribute(Element element, String name) {
		return element.attributeValue(name);
	}

	@Override
	public boolean isLazy(Element element) {
		// 调用本类的方法获取对应的值
		String lazy = getAttribute(element, "lazy-init");
		Element parent = element.getParent();
		Boolean parentLazy = new Boolean(getAttribute(parent,
				"default-lazy-init"));
		if (parentLazy) {
			if ("false".equals(lazy))
				return false;
			return true;
		} else {
			if ("true".equals(lazy))
				return true;
			return false;
		}
	}

	@Override
	public List<Element> getConstructorElements(Element element) {
		// 得到bean节点下的所有节点
		List<Element> children = element.elements();
		List<Element> result = new ArrayList<Element>();
		// 将有constructor-arg的节点放入结果集
		for (Element e : children) {
			if ("constructor-arg".equals(e.getName())) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public boolean isSingleton(Element element) {
		Boolean singleton = new Boolean(getAttribute(element, "singleton"));
		return singleton;
	}

	@Override
	public List<Element> getPropertyElements(Element element) {
		List<Element> children = element.elements();
		List<Element> result = new ArrayList<Element>();
		for (Element e : children) {
			if ("property".equals(e.getName())) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public Autowire getAutowire(Element element) {
		String value = this.getAttribute(element, "autowire");
		String parentValue = this.getAttribute(element.getParent(),
				"default-autowire");
		if ("no".equals(parentValue)) {
			if ("byName".equals(value))
				return new ByNameAutowire(value);
			else return new NoAutowire(value);
		} else if ("byName".equals(value)) {
			if ("no".equals(value))
				return new NoAutowire(value);
			else return new ByNameAutowire(value);
		}
		return new NoAutowire(value);
	}

	@Override
	public List<DataElement> getConstructorValue(Element element) {
		List<Element> cons = getConstructorElements(element);
		List<DataElement> result = new ArrayList<DataElement>();
		for (Element e : cons) {
			//获取了construtor元素下的元素
			List<Element> eles = e.elements();
			DataElement dataElement = getDataElement(eles.get(0));
			result.add(dataElement);
		}
		return result;
	}

	private DataElement getDataElement(Element element) {
		String name = element.getName();
		if ("value".equals(name)) {
			String ClassTypeName = element.attributeValue("type");
			String data = element.getText();
			return new ValueElement(getValue(ClassTypeName, data));
		} else if ("ref".equals(name)) {
			return new RefElement(this.getAttribute(element, "bean"));
		}
		return null;
	}

	private Object getValue(String classTypeName, String data) {
		if (isType(classTypeName, "Integer")) {
			return Integer.parseInt(data);
		} else if (isType(classTypeName, "Long")) {
			return Long.parseLong(data);
		} else if (isType(classTypeName, "Short")) {
			return Short.parseShort(data);
		}else
			return data;
	}

	private boolean isType(String classTypeName, String type) {
		if (classTypeName.indexOf(type) != -1)
			return true;
		return false;
	}

	@Override
	public List<PropertyElement> getPropertyValue(Element element) {
		//调用本类的方法获得全部的Property节点
		List<Element> properties = getPropertyElements(element);
		
		List<PropertyElement> result = new ArrayList<PropertyElement>();
		for (Element e : properties) {
			List<Element> els = e.elements();
			DataElement dataElement = getDataElement(els.get(0));
			String propertyNameAtt = this.getAttribute(e, "name");

			PropertyElement pe = new PropertyElement(propertyNameAtt,
					dataElement);

			result.add(pe);
		}
		return result;
	}

}
