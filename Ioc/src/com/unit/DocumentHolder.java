package com.unit;

import org.dom4j.Document;

//���ڼ���xml�ļ�,��ȡ������Ϣ
public interface DocumentHolder {
	//�����ļ�·������Document����
   Document getDocument(String filePath);
}
