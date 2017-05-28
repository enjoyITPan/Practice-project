package com.main;

public interface ApplicationContext {
	/**
	 * 根据id获得bean，没有的话就尝试创建该bean的实例
	 * @param id
	 * @return
	 */
   Object getBean(String id);
   
    /**
     * IOC容器是否包含id为参数的bean
     * @param id
     * @return
     */
   boolean containsBean(String id);
   
   /**
    * 判断一个bean是不是单例模式
    * @param id
    * @return
    */
   boolean isSingleton(String id);
   
   /**
    * 获得bean , 如果从容器中找不到该bean,返回null
    * @param id
    * @return bean实例
    */
   Object getBeanIgnoreCreate(String id);
   
   
}
