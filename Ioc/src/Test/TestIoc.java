package Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import com.main.ApplicationContext;
import com.main.impl.XmlApplicationContext;
import com.unit.BeanCreate;
import com.unit.DocumentHolder;
import com.unit.ElementLoader;
import com.unit.PropertyHandler;
import com.unit.impl.BeanCreateException;
import com.unit.impl.BeanCreateImpl;
import com.unit.impl.ElementLoaderImpl;
import com.unit.impl.PropertyHandlerImpl;
import com.unit.impl.XmlDocumentHolder;

public class TestIoc {
    private DocumentHolder xmlholder;
    private ElementLoader eleLoader;
	@Before
	public void init(){
		xmlholder = new XmlDocumentHolder();
		eleLoader = new ElementLoaderImpl();
	}
	
	@Test
	public void TestIOC(){
		String[] xmlPaths = {"beans.xml"};
		ApplicationContext ctx = new XmlApplicationContext(xmlPaths);
		
		
		A b2 = (A) ctx.getBean("aa");
		System.out.println(b2);
		
//		B b = (B) ctx.getBean("test3");
//		System.out.println(b);
		

	}
	
	@Test
	public void create() throws BeanCreateException{
		BeanCreate create = new BeanCreateImpl();
		String className = "Test.B";
		List<Object> args = new ArrayList<Object>();
		args.add(15);
		args.add("panjian");
		B b = (B) create.createBeanUseDefaultConstruct(className,args);
		System.out.println(b);
		
	}
    
	@Test
	public void TestSetter() throws NoSuchMethodException{
		B b =new B();
		PropertyHandler pro = new PropertyHandlerImpl();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "panjian_3");
		map.put("age",15);
		pro.setProperties(b, map);
		System.out.println(b.getAge());
		System.out.println(b.getName());
	}
	
	
	@Test
	public void TestGetDoc(){
		String filePath = "D:/A_FILE/java/Ioc/src/beans.xml"; 
		Document doc = xmlholder.getDocument(filePath);
		Element root = doc.getRootElement();
		System.out.println(root.getName());
		List<Element> elements = root.elements();
		for(Element e:elements){
			System.out.println(e.getName());
			System.out.println(e.getText());
			List<Element> elements1 = e.elements();	
			for(Element e1:elements1)
				System.out.println(e1.getName());
		}
		
		Document doc2 = xmlholder.getDocument(filePath);
		System.out.println(doc);
		System.out.println(doc2);
	}
	
	@Test
	public void TestGetElements(){
		String filePath = "D:/A_FILE/java/Ioc/src/beans.xml"; 
		Document doc = xmlholder.getDocument(filePath);
		eleLoader.addElements(doc);
		Element e1 = eleLoader.getElement("test1");
		System.out.println(e1.getName()+ " " + e1.getText());
		
		Element e2 = eleLoader.getElement("test2");
		System.out.println(e2.getName()+ " " + e2.getText());
	}
	
	@Test
	public void TestCon() throws ClassNotFoundException{
		Class clazz = Class.forName("Test.B");
		Class[] argsClass = {String.class,Integer.TYPE};
		try {
			Constructor constructor = clazz.getConstructor(argsClass); 
			Class[] argsTYPE= constructor.getParameterTypes();
			for(int i=0;i<argsTYPE.length;i++)
				System.out.println(argsTYPE[i].getName());
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	@Test
	public void TestIndexof(){
		String str = "java.lang.Integer";
		System.out.println(str.indexOf("Integer"));
	}
	
	@Test
	public void TestJava(){
		String str = "Test.B";
		B a = null;
		try {
			Class clazz = Class.forName(str);
			a = (B)clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(a);
	}
}
