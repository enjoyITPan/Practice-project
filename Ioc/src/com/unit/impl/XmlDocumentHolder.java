package com.unit.impl;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.unit.DocumentHolder;

public class XmlDocumentHolder implements DocumentHolder{
   //�½�һ��Map���󣬿��Ա�����xml�ļ�������Ҳ���Ա�ֻ֤���ȡһ���ĵ�����
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
		//xml�Ķ�ȡ��
		SAXReader reader = new SAXReader();
		//ʹ���Լ���EntityResolver������ֱ��ȥ������
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
