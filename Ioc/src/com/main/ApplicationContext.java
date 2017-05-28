package com.main;

public interface ApplicationContext {
	/**
	 * ����id���bean��û�еĻ��ͳ��Դ�����bean��ʵ��
	 * @param id
	 * @return
	 */
   Object getBean(String id);
   
    /**
     * IOC�����Ƿ����idΪ������bean
     * @param id
     * @return
     */
   boolean containsBean(String id);
   
   /**
    * �ж�һ��bean�ǲ��ǵ���ģʽ
    * @param id
    * @return
    */
   boolean isSingleton(String id);
   
   /**
    * ���bean , ������������Ҳ�����bean,����null
    * @param id
    * @return beanʵ��
    */
   Object getBeanIgnoreCreate(String id);
   
   
}
