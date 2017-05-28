package com.unit.impl;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.unit.DocumentHolder;

public class XmlDocumentHolder implements DocumentHolder{
   //新建一个Map对象，可以保存多份xml文件（这样也可以保证只会读取一次文档对象）
	private Map<String,Document> docs = new HashMap<String,Document>();
	@Override
	public Document getDocument(String filePath) {
        Document doc = this.docs.get(filePath);
        if(doc == null){
        	this.docs.put(filePath, readDcoument(filePath));
        }
		return this.docs.get(filePath);
	}
	private Document readDcoument(String filePath) {
		//xml的读取器
		SAXReader reader = new SAXReader();
		//使用自己的EntityResolver，避免直接去网上找
		reader.setEntityResolver(new IocEntityResolver());
		File xmlFile = new File(filePath);
		Document doc = null;
		try {
			doc = reader.read(xmlFile);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
