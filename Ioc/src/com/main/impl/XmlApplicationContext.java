package com.main.impl;

import com.main.AbstractApplicationContext;

public class XmlApplicationContext extends AbstractApplicationContext {
	public XmlApplicationContext(String[] xmlPaths) {
           setUpElements(xmlPaths);
           createBeans();
	}
}
