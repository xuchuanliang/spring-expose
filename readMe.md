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

### 4.4 容器背后的秘密
#### 4.4.1战略性观望
- Spring的IOC所起的作用：Spring的IOC容器会以某种方式加载Configuration Metadata（配置文件）,然后根据这些信息绑定整个系统的对象，最终组装成一个可用的基于轻量级容器的应用系统。
- Spring的IOC容器基本划分两个阶段，即容器启动阶段和Bean实例化阶段。
> Spring的IOC容器启动阶段：加载配置、分析配置信息、装备到BeanDefinition、其他后序处理；
Bean实例化阶段：实例化对象、装配依赖、生命周期回调、对象其他处理、注册会调接口；
- 容器启动阶段
> 容器启动开始，首先会通过某种途径加载Configuration MetaData。除了代码方式比较直接，在大部分情况下，容器需要依赖某些工具类（BeanDefinitionReader）对加载的Configuration MetaData进行解析和分析，并
将分析后的信息编组为相应的BeanDefinition，最后把这些保存Bean定义必要信息的BeanDefinition，注册到相应的BeanDefinitionRegistry，这样容器的启动工作就完成了。这一阶段是准备性的，重点侧重于对象信息的收集、验证性工作、辅助性工作。
- Bean实例化阶段
> 经过第一个阶段，现在所有的Bean定义都通过BeanDefinition的方式注册到了BeanDefinitionRegistry中。当某个请求方通过容器的getBean方法明确请求某个对象，或者因依赖关系容器需要隐式的调用getBean方法时，就会触发第二阶段的活动。
该阶段，容器会首先检查所请求的对象之前是否已经初始化。如果没有，则会根据注册的BeanDefinition所提供的信息实例化被请求对象，并为其注入依赖。  
>Spring提供一共叫做BeanFactoryPostProcessor的容器扩展机制，允许我们在容器实例化相应对象之前，对注册到容器的BeanDefinition所保存的信息做相应的修改。  

>PropertyPlaceholderConfigurer不单会从其配置的properties文件中加载配置项，同时还会检查Java的System类中的Properties  
>PropertyOverrideConfigurer可以对容器中配置的任何想要处理的bean定义的property信息进行覆盖替换  

>配 置 在 properties 文 件 中 的 信 息 通 常 都 以 明 文 表 示 ， PropertyOverrideConfigurer的 父 类PropertyResourceConfigurer提供了一个protected类型的方法convertPropertyValue，允许子类
 覆盖这个方法对相应的配置项进行转换，如对加密后的字符串解密之后再覆盖到相应的bean定义中。当然，既然PropertyPlaceholderConfigurer也同样继承了PropertyResourceConfigurer，我们
 也可以针对PropertyPlaceholderConfigurer应用类似的功能。  
 >CustomEditorConfigurer：完成这种由字符串到具体对象的转换（不管这个转换工作最终由谁来做），都需要这种转换规则相关的信息，而CustomEditorConfigurer就是帮助我们传达类似信息的。
 
 ###了解Bean的一生
 >我们已经可以通过使用BeanFactoryPostProcessor来敢于容器启动阶段  
 > 容器启动后，并不会马上就实例化相应的Bean定义，容器现在仅仅拥有所有对象的BeanDefinition来保存实例化阶段将要用的必要信息。只有当请求通过BeanFactory的getBean()方法来请求某个对象的实例的时候，才有可能触发
 Bean实例化阶段的活动。BeanFactory的getBean可以被客户端显示调用，也可以在容器内部隐式的调用。  
 >>隐式调用一般有如下两种情况：  
 >>>对于BeanFactory而言，对象实例化默认采用延迟初始化。  
 >>>ApplicationContext启动之后会实例化所有的Bean定义。    <br/>

>getBean()第一次被调用时，不管是显示还是隐式的，Bean的实例化阶段的活动会被触发。第二次调用时会直接返回容器中第一次实例化的缓存（property类型bean除外）  
>当getBean()方法内部发现该bean定义之前还没有被实例化之后，会通过createBean()方法进行具体对象实例化
>>实例化过程：实例化Bean对象->设置对象属性->检查Aware相关接口并设置相关依赖->BeanPostProcessor前置处理->检查是否是InitializingBean以决定是否调用afterPropertiesSet方法->检查是否配置自定义的init-method->BeanPostProcessor后置处理
->注册必要的Destrutcion相关回调接口->使用中->是否实现DisposableBean接口->是否配置有自定义的destroy方法

- bean的实例化与BeanWrapper  
>>容器在内部实现的时候，采用策略模式来决定使用何种方式初始化bean实例。通常可以使用反射或者CGLIB动态字节码来生成初始化相应的bean实例或者动态生成其子类。
org.springframework.beans.factory.support.InstantiationStrategy定义是实例化策略的抽象接口，其直接子类SimpleInstantiationStrategy实现了简单的对象实例化功能，可以通过反射来实例化对象实例，
CglibSubclassingInstantiationStrategy继承了SimpleInstantiationStrategy的以反射方式实例化对象的功能，并且通过CGLIB的动态字节码生成功能，该策略实现类可以动态生成某个类的子类，
进而满足了方法注入所需的对象实例化需求。默认情况下，容器内部采用的是CglibSubclassingInstantiationStrategy。   
>容器根据BeanDefinition取得实例化信息，通过SimpleInstantiationStrategy或CglibSubclassingInstantiationStrategy返回实例化对象，但是返回的不是构造完成的对象实例，而是对象的包装类：BeanWrapper，主要是为了方便设置对象属性。  

- 各色的Aware接口  
>当对象实例化完成并且相关属性以及依赖设置完成之后， Spring容器会检查当前对象实例是否实现了一系列的以Aware命名结尾的接口定义。如果是，则将这些Aware接口定义中规定的依赖注入给当前对象实例  
>>BeanFactory的Aware接口有以下几种：  
>>>org.springframework.beans.factory.BeanNameAware。如果Spring容器检测到当前对象实例实现了该接口，会将该对象实例的bean定义对应的beanName设置到当前对象实例。  
>>>org.springframework.beans.factory.BeanClassLoaderAware。如果容器检测到当前对象实例实现了该接口，会将对应加载当前bean的Classloader注入当前对象实例。默认会使用加载org.springframework.util.ClassUtils类的Classloader。  
>>>org.springframework.beans.factory.BeanFactoryAware。如果对象声明实现了BeanFactoryAware接口， BeanFactory容器会将自身设置到当前对象实例。这样，当前对象实例就拥有了一个BeanFactory容器的引用，并且可以对这个容器内允许访问的对象按照需要进行访问。  
>>ApplicationContext的Aware接口有以下几种：  
>>>org.springframework.context.ResourceLoaderAware 。 ApplicationContext 实 现 了Spring的ResourceLoader接口（后面会提及详细信息）。当容器检测到当前对象实例实现了ResourceLoaderAware接口之后，
会将当前ApplicationContext自身设置到对象实例，这样当前对象实例就拥有了其所在ApplicationContext容器的一个引用。  
>>>org.springframework.context.ApplicationEventPublisherAware 。 ApplicationContext还实现了ApplicationEventPublisher接口，这样，它就可以作为ApplicationEventPublisher来使用。
所以，当前ApplicationContext容器如果检测到当前实例化的对象实例声明了ApplicationEventPublisherAware接口，则会将自身注入当前对象。  
>>>org.springframework.context.MessageSourceAware。ApplicationContext通过MessageSource接口提供国际化的信息支持，即I18n（Internationalization）。它自身就实现了MessageSource接口，
所以当检测到当前对象实例实现了MessageSourceAware接口，则会将自身注入所以当检测到当前对象实例实现了MessageSourceAware接口，则会将自身注入  
>>>org.springframework.context.ApplicationContextAware。 如果ApplicationContext容器检测到当前对象实现了ApplicationContextAware接口，则会将自身注入当前对象实例。

- BeanPostProcessor  
>>BeanPostProcessor是存在于对象实例化阶段，BeanFactoryPostProcessor则是存在于容器启动阶段
>>>通常比较常见的使用BeanPostProcessor的场景，是处理标记接口实现类，或者为当前对象提供代理实现。ApplicationContext对应的那些Aware接口实际上就是通过BeanPostProcessor的方式进行处理的。
当ApplicationContext中每个对象的实例化过程走到BeanPostProcessor前置处理这一步时， ApplicationContext容器会检测到之前注册到容器的ApplicationContextAwareProcessor这个BeanPostProcessor的实现类，
然后就会调用其postProcessBeforeInitialization()方法，检查并设置Aware相关依赖

- InitializingBean和init-method
 >org.springframework.beans.factory.InitializingBean 该接口的作用是在对象实例化过程吊用过"BeanPostProcessor的前置处理"之后，会接着检测当前对象是否实现了InitializingBean接口，如果是，则会调用
 afterPropertiesSet()方法进一步调整对象实例的状态。Spring还提供了另一种方式来指定自定义的对象初始化操作，使用<bean>的init-method属性
 
 - 5. DisposableBean与destroy-method
 
 - 小结：Spring的IoC容器主要有两种，即BeanFactory和ApplicationContext。
 
 ## 第五章：Spring IOC容器：ApplicationContext

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

### 5.1 国际化信息支持
- 在java的国际化信息处理，主要涉及lianggelei：java.util.Locale和java.util.ResourceBundle
>不同的locale代表不同的国家和地区  
>ResourceBundle用来保存某个特定的Locale信息。通常，ResourceBundle管理一组信息序列，所有的信息序列有统一的一个basename，然后特定的Locale信息，可以根据basename后追加的语言或者地区代码来区分，比如，我们
用一组properties文件分别来标识不同国家和地区的信息，可以像接下来命名响应的properties文件：message.properties,message_zh.properties,messages_zh_CN.properties,messages_en.properties,messages_en_US.properties.
其中，文件名中的message部分称作ResourceBundle将加载资源的basename，其他语言或地区的资源在basename的基础上追加Locale特定代码。
>>每个资源文件中都有相同的键来标识具体资源条目，但每个资源内部对应相同键的资源条目内容，则根据Locale的不同而不同。如：
 ```
# messages_zh_CN.properties文件中
menu.file=文件({0})
menu.edit=编辑
...
# messages_en_US.properties文件中
menu.file=File({0})
menu.edit=Edit
....
````
>有了ResourceBundle对应的资源文件之后，我们就可以通过ResourceBundle的getBundle(String basename,Locale locale)方法取得不同的Local对应的ResourceBundle，然后根据资源的键取得相应Locale的资源条目内容。
通过结合ResourceBundle和Locale，就能实现应用程序的国际化支持。  
>Spring在javaSE的基础上进一步抽象了国际化信息访问接口，org.springframework.context.MessageSource，其中接口中定义的方法，用户访问国际化资源，ApplicationContext同时也实现了MessageSource接口，
ApplicationContext将委派容器中一个名称为messageSource的MessageSource接口实现来完成MessageSource应该完成的职责。如果找不到这样一个名字的MessageSource实现， 
ApplicationContext内部会默认实例化一个不含任何内容的StaticMessageSource实例，以保证相应的方法调用。通常情况下，如果要提供容器内的国际化支持，一般会自己注入一个MessageSource的实现类到容器中。  
>可用的MessageSource实现：
>>org.springframework.context.support.StaticMessageSource：MessageSource接口的简单实现，可以通过编程的方式添加条目，多用于测试，不用于生产环境。
>>org.springframework.context.support.ResourceBundleMessageSource：基础标准的java.util.ResourceBundle而实现的MessageSource，对其父类AbstractMessageSource的行为进行了扩展，提供对多个ResourceBundle
的缓存以提高查询速度。同时，对于参数化的信息和非参数化信息的处理进行了优化，并对用于参数化信息格式化的MessageFormat实例也进行了缓存。它是最常用的、用于正式生产环境下的MessageSource实现。
>>org.springframework.context.support.ReloadableResourceBundleMessageSource：同样基于标准的java.util.ResourceBundle而构建的MessageSource实现类 ，但通过其cacheSeconds属性可以指定时间段，以定期刷新
并检查底层的properties资源文件是否有变更。

###容器内部事件发布
>Java SE提供了实现自定义事件发布（Custom Event publication）功能的基础类，即java.util.EventObject类和java.util.EventListener接口。所有的自定义事件类型可以通过扩展EventObject来实现，而事件的监听器则扩展自EventListener。  
>组合事件类和监听器，发布事件。 有了自定义事件和自定义事件监听器，剩下的就是发布事件，然后让相应的监听器监听并处理事件了。通常情况下，我们会有一个事件发布者（EventPublisher），它本身作为事件源，会在合适的时点，将相应事件发布给对应的事件监听器。

## 第六章 Spring IOC容器之扩展篇

# 第三部分 Spring AOP框架
##第七章 一起来看AOP

-- 静态AOP，即第一代AOP
-- 动态AOP，第二代AOP
### 7.3 java平台上AOP的实现机制
- 动态代理：在运行期间，为相应的接口生成对应的代理对象；缺点是需要织入横切关注点逻辑的模块类都得实现相应的接口。spring AOP默认情况下采用这种实现机制实现AOP功能
- 动态字节码增强：使用ASM或者CGLIB等java工具库，在程序运行期间，动态构建字节码的class文件
- Java代码生成
- 自定义类加载器
### 7.4 AOP国家的公民
- JoinPoint：在系统运行之前，AOP的功能模块都需要织入到OOP的功能模块中。所以，要进行这种织入过程，我们需要知道在系统的哪些执行点上进行织入操作，这些将要在其之上进
行织入操作的系统执行点就成为JoinPoint。
- PointCut概念代表的是JoinPoint的表述方式。将横切逻辑织入当前系统的过程中，需要参照PointCut规定的JoinPoint信息，才可以知道应该往系统的哪些JoinPoint上织入横切逻辑。
- Advice是单一横向关注点逻辑的载体，它代表将会织入到JoinPoint的横切逻辑。如果将Aspect比作OOP中的class，那么Advice就相当于Class中的method。
>Before Advice，After Advice，Around Advice，Introduction
- Aspect
> Asprct是对系统中的横切关注点逻辑进行模块化封装的AOP概念实体。通常情况下，Aspect可以包含多个Pointcut以及相关Advice定义。
- 织入和织入器
>符合Pointcut所指定的条件，将在织入过程中被织入横切逻辑的对象，称为目标对象。
- 2018年11月9日 22:00:02  第八章 144/673

##第八章 Spring AOP概述以及实现机制

##第九章 Spring AOP一世

##第十章 Spring AOP二世

##第十一章 AOP应用案例

##第十二章 Spring AOP之扩展篇