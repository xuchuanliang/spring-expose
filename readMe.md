#第二部分：spring的IOC容器
##第二章：IOC的基本概念
- 三种依赖注入的方式：构造方法注入、setter方法注入、接口注入

##第三章：掌管大局的IOC Service Provider
- IOC Service Provider是一个抽象出来的概念，它可以指代任何将IOC场景中的业务对象绑定到一起的实现方式。
- IOC Service Provider的职责是业务对象的构建管理和业务对象间的依赖绑定
- IOC Service Provider产品使用的注册对象管理信息的方式主要有三种：直接编码方式、配置文件方式、元数据方式

##第四章：spring的IOC容器之BeanFactory
### 4.2BeanFactory的对象注册和与依赖绑定关系
#### 4.2.1 直接编码的方式
>Beanfactory只是一个接口，我们最终需要一个该接口的实现类来进行实际的Bean的管理，DefaultListableBeanFactory就是一个比较通用的BeanFactory实现类，
DefaultListableBeanFactory除了间接地实现了BeanFactory接口，还实现了BeanDefinitionRegistry接口，该接口才是在BeanFactory的实现中担当Bean注册管理的角色，。
**_基本上，BeanFactory接口只定义如何访问容器内管理Bean的方法，各个BeanFactory的具体实现类负责具体Bean的注册以及管理工作_**。BeanDefinitionRegistry接口定义了
抽象了Bean的注册逻辑。通常情况下，具体的BeanFactory实现类会实现这个接口来管理Bean的注册。  

- BeanFactory若比作图书馆，则BeanDefinitionRegistry则就像图书馆的书架，所有的书都是放在书架上，虽然借书和还书都是跟图书馆打交道
>每一个受管的对象，在容器中都会有一个BeanDefinition实例与之相对应，该BeanDefinition的实例负责保存对象的所有必要信息，包含对应的class类型、是否是抽象类、
构造方法参数以及其他属性等。当客户端向BeanFactory请求相应的对象时，BeanFactory会通过这些信息为客户端返回一个完备可用的对象实例。RootBeanDefinition和ChildBeanDefinition
是BeanDefinition的两个主要实现类。

#### 4.2.2 外部配置文件的方式
>采用外部配置文件时，Spring的IOC容器有一个统一的处理方式。通常情况下，需要根据不同的外部配置文件格式，给出相应的BeanDefinitionReader实现类，由BeanDefinitionReader
的相应实现类将相应的配置文件内容读取并映射到BeanDefinition，然后将映射后的BeanDefinition注册到一个BeanDefinitionRegistry，之后，BeanDefinitionRegistry
即完成Bean的注册和加载。当然，大部分工作，包括解析文件格式、装配BeanDefinition之类的工作，都是由BeanDefinitionReader的相应实现类来做的，BeanDefinitionRegister
只不过负责保管而已。

###4.3.5 Bean的scope
>scope用来声明容器中的对象所应该处的限定场景或者说该对象的存活时间，即容器在对象进入其相应的scope之前，生成并装配这些对象，在该对象不再处于这些scope的限定之后，容器通常会销毁这些对象。
打个比方吧！我们都是处于社会（容器）中，如果把中学教师作为一个类定义，那么当容器初始化这些类之后，中学教师只能局限在中学这样的场景中；中学，就可以看作中学教师的scope。

##第五章：Spting IOC容器 ApplicationContext
- Spring 为ApplicationContext类型容器提供以下几个常用的实现：
>org.springframework.context.support.FileSystemXmlApplicationContext:在默认情况下，从文件系统加载bean定义以及相关资源的ApplicationContext实现。  
>org.springframework.context.support.ClassPathXmlApplicationContext:在默认情况下，从Classpath加载bean定义以及相关资源的ApplicationContext实现。  
>org.springframework.web.context.support.XmlWebApplicationContext:spring提供的用于web应用程序的ApplicationContext实现。

### 5.1 统一资源加载策略
>Spring中的org.springframework.core.io.Resource接口是作为所有资源的抽象和访问接口，有如下特定实现：  
>>ByteArrayResource。将字节（byte）数组提供的数据作为一种资源进行封装，如果通过InputStream形式访问该类型的资源，该实现会根据字节数组的数据，构造相应的ByteArrayInputStream并返回。  
>>ClassPathResource。该实现从Java应用程序的ClassPath中加载具体资源并进行封装，可以使用指定的类加载器（ClassLoader）或者给定的类进行资源加载。  
>>FileSystemResource。对java.io.File类型的封装，所以，我们可以以文件或者URL的形式对该类型资源进行访问，只要能跟File打的交道，基本上跟FileSystemResource也可以。  
>>UrlResource。通过java.net.URL进行的具体资源查找定位的实现类，内部委派URL进行具体的资源操作。  
>>InputStreamResource。将给定的InputStream视为一种资源的Resource实现类，较为少用。  

>spring中的org.springframework.core.io.ResourceLoader接口是资源查找定位策略的统一抽象，具体的资源查找定位策略则由相应的ResourceLoader实现类给出。  
>>ResourceLoader的默认实现：org.springframework.core.io.DefaultResourceLoader，该默认的资源查找处理逻辑如下：
>>>首先检查资源路径是否以classpath:前缀打头，如果是，则尝试构造ClassPathResource类型资源并返回。
>>>否则， (a) 尝试通过URL，根据资源路径来定位资源，如果没有抛出MalformedURLException，有则会构造UrlResource类型的资源并返回；(b)如果还是无法根据资源路径定位指定的资源，则委派getResourceByPath(String)方法来定位，
DefaultResourceLoader的getResourceByPath(String)方法默认实现逻辑是，构造ClassPathResource类型的资源并返回。  
>>FileSystemResourceLoader
>>>从文件系统加载资源并以FileSystemResource类型返回  
>org.springframework.core.io.support.ResourcePatternResolver ——批量查找的ResourceLoader
>>ResourcePatternResolver扩展了ResourceLoader，不止可以加载单个资源，还可以根据指定的资源路径匹配模式，每次返回多个Resource实例。  
>>ResourcePatternResolver的实现类org.springframework.core.io.support.PathMatchingResourcePatternResolver
-- 2018年11月1日 21:04:37 该看5.2国际化信息支持

