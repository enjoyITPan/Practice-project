实现的功能：
1. 无参构造函数，有参构造函数实例化
2.延迟加载
3.自动装配
4.单例模式
5.set注入
6.依赖注入（只支持ByName方式）

具体：
xml解析器使用的是dom4j
Bean的读取缓存在Map中
Bean的解析，包括class,构造函数，set方法，单例模式，延迟加载
getBean:1.单例就缓存后再返回  2.不是就直接返回
支持ByName注入：获取所有set方法，直接返回属性名，在IOC中寻找是否已经具有，有就直接使用invoke调用set方法注入
