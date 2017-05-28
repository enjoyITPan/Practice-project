package com.main;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.unit.Autowire;
import com.unit.BeanCreate;
import com.unit.DataElement;
import com.unit.DocumentHolder;
import com.unit.ElementLoader;
import com.unit.ElementReader;
import com.unit.PropertyHandler;
import com.unit.impl.BeanCreateException;
import com.unit.impl.BeanCreateImpl;
import com.unit.impl.ByNameAutowire;
import com.unit.impl.ElementLoaderImpl;
import com.unit.impl.ElementReadImpl;
import com.unit.impl.NoAutowire;
import com.unit.impl.PropertyElement;
import com.unit.impl.PropertyHandlerImpl;
import com.unit.impl.RefElement;
import com.unit.impl.ValueElement;
import com.unit.impl.XmlDocumentHolder;

public abstract class AbstractApplicationContext implements ApplicationContext {
    
	//加载元素
	protected ElementLoader elementLoader = new ElementLoaderImpl();
	//加载xml文档
	protected DocumentHolder documentHolder = new XmlDocumentHolder();
	//缓存bean
	protected Map<String,Object> beans = new HashMap<String, Object>();
	//属性处理类
	protected PropertyHandler propertyHandler = new PropertyHandlerImpl();
	
	//创建bean对象的接口
	protected BeanCreate beanCreate = new BeanCreateImpl();
	
	//Element 元素读取类
	protected ElementReader elementReader = new ElementReadImpl();
	
	//读取xml文件，将各元素缓存
	protected void setUpElements(String[] xmlPaths){
		URL classPathUrl = AbstractApplicationContext.class.getClassLoader().getResource(".");
		String classPath = null;
		try {
			classPath = java.net.URLDecoder.decode(classPathUrl.getPath(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(String path:xmlPaths){
			Document doc = documentHolder.getDocument(classPath+path);
			elementLoader.addElements(doc);
		}
	}
	@Override
	public Object getBean(String id) {
		Object bean = this.beans.get(id);
		if(bean == null){
			bean = handleSingleton(id);
		}
		return bean;
	}

	private Object handleSingleton(String id) {
		Object bean = createBean(id);
		if(isSingleton(id)){
			this.beans.put(id, bean);
		}
		return bean;
	}
	private Object createBean(String id) {
		Element e = elementLoader.getElement(id);
		if(e == null){
			try {
				throw new BeanCreateException("element not found" + id);
			} catch (BeanCreateException e1) {
				e1.printStackTrace();
			}
		}
		Object result = instance(e);
//		System.out.println("创建bean:" + id);
//		System.out.println("该bean是:" + result);
		Autowire autowire = elementReader.getAutowire(e);
		if(autowire instanceof ByNameAutowire){
			autowireByName(result);
		}else if(autowire instanceof NoAutowire){
			setterInject(result,e);
		}
		return result;
	}
	//result : 实例对象    e : 定义的bean
	private void setterInject(Object result, Element e) {
		List<PropertyElement> properties = elementReader.getPropertyValue(e);
		Map<String,Object> propertiesMap = getPropertyArgs(properties);
		try {
			propertyHandler.setProperties(result, propertiesMap);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		
	}
	private Map<String, Object> getPropertyArgs(List<PropertyElement> properties) {
		Map<String,Object> result = new HashMap<String, Object>();
		for(PropertyElement p: properties){
			DataElement de = p.getDataElement();
			if(de instanceof RefElement){
				result.put(p.getName(), this.getBean((String)de.getValue()));
			}else if(de instanceof ValueElement){
				result.put(p.getName(), de.getValue());
			}
		}
		return result;
	}
	private void autowireByName(Object result) {
		Map<String,Method> methods = propertyHandler.getSetterMethodsMap(result);
		for(String s:methods.keySet()){
			Element e = elementLoader.getElement(s);
			if(e == null )continue;
			Object bean = this.getBean(s);
			Method method = methods.get(s);
			try {
				propertyHandler.executeMethod(result, bean, method);
			} catch (BeanCreateException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	private Object instance(Element e) {
		String className = elementReader.getAttribute(e, "class");
		List<Element> constructorElement = elementReader.getConstructorElements(e);
		//System.out.println(constructorElement.size());
		if(constructorElement.size() == 0){
			try {
				return beanCreate.createBeanUseDefaultConstruct(className);
			} catch (BeanCreateException e1) {
				e1.printStackTrace();
			}
		}else {
			List<Object> args = getConstructArgs(e);
			try {
				return beanCreate.createBeanUseDefaultConstruct(className,args);
			} catch (BeanCreateException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	private List<Object> getConstructArgs(Element e) {
		List<DataElement> datas = elementReader.getConstructorValue(e);
		List<Object> result = new ArrayList<Object>();
		for(DataElement d : datas){
			if(d instanceof ValueElement){
				d =(ValueElement) d;
				result.add(d.getValue());
			}else if(d instanceof RefElement){
				d = (RefElement) d;
				String refId = (String) d.getValue();
				result.add(this.getBean(refId));
			}
		}
		return result;
	}
	@Override
	public boolean containsBean(String id) {
		Element e =elementLoader.getElement(id);
		return (e==null)?false:true;
	}

	@Override
	public boolean isSingleton(String id) {
		Element e =elementLoader.getElement(id);
		return elementReader.isSingleton(e);
	}

	@Override
	public Object getBeanIgnoreCreate(String id) {
		return this.beans.get(id);
	}
    
	protected  void  createBeans() {
		Collection<Element> elements = elementLoader.getElements();
		for(Element e:elements){
			boolean lazy = elementReader.isLazy(e);
			if(!lazy){
				String id = e.attributeValue("id");
				Object bean = this.getBean(id);
				if(bean == null){
					handleSingleton(id);
				}
			}
		}
	}
}
