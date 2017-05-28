package com.main.impl;

import com.main.AbstractApplicationContext;

public class XmlBeanFactory extends AbstractApplicationContext {
	public XmlBeanFactory(String[] xmlPaths) {
         setUpElements(xmlPaths);
	}
}
