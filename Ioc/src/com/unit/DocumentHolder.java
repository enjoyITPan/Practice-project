package com.unit;

import org.dom4j.Document;

//用于加载xml文件,获取配置信息
public interface DocumentHolder {
	//根据文件路径返回Document对象
   Document getDocument(String filePath);
}
