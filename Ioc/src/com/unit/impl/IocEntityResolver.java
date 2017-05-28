package com.unit.impl;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//定义对dtd文件的寻找方式
public class IocEntityResolver implements EntityResolver {

	@Override
	public InputSource resolveEntity(String publicid, String systemId)
			throws SAXException, IOException {
		if(systemId.equals("http://www.panjian.org/beans.dtd")){
		InputStream stream = IocEntityResolver.class.getResourceAsStream("/beans.dtd");
		return new InputSource(stream);
		}else return null;
	}

}
