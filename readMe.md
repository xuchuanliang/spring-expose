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
行织入操作的系统执行点就成为JoinPoint：方法调用、方法调用执行、构造方法调用，构造方法调用执行、字段设置、字段获取、异常处理执行、类初始化。
- PointCut概念代表的是JoinPoint的表述方式。将横切逻辑织入当前系统的过程中，需要参照PointCut规定的JoinPoint信息，才可以知道应该往系统的哪些JoinPoint上织入横切逻辑，理解成一组JoinPoint或者经过一系列表达式运算后的JoinPoint。
- Advice是单一横向关注点逻辑的载体，它代表将会织入到JoinPoint的横切逻辑。如果将Aspect比作OOP中的class，那么Advice就相当于Class中的method。
>Before Advice，After Advice，Around Advice，Introduction
- Aspect
> Aspect是对系统中的横切关注点逻辑进行模块化封装的AOP概念实体。通常情况下，Aspect可以包含多个Pointcut以及相关Advice定义。
- 织入和织入器
>符合Pointcut所指定的条件，将在织入过程中被织入横切逻辑的对象，称为目标对象。

##第八章 Spring AOP概述以及实现机制
- Spring框架的质量三角：AOP、依赖注入、轻量级服务抽象
- 像日志记录、安全检查、事务管理等系统需求，以AOP的行话而言，这些系统需求就是系统中的横切关注点。

2018年11月27日 22:00:44 162
###Spring AOP的实现机制
>Spring AOP属于第二代AOP，采用动态代理机制和字节码生成技术实现。与最初的AspectJ采用编译器将横切逻辑织入目标对象不同，动态代理机制和字节码技术都是在运行期间
为目标对象生成一个代理对象，而将横切逻辑织入到这个代理对象中，系统最终使用的是织入了横切逻辑的代理对象，而不是真正的目标对象。
- 代理：代理处于访问者和被访问者之间，可以隔离这两者之间的直接交互，访问者跟代理打交道就好像在跟被访问者在打交道一样，因为代理通常几乎会全权拥有被代理者的职能，代理能够处理的
访问请求就不用劳烦被访问者处理了。从这个角度来说，代理可以减少被访问者的负担。另外，即使代理最终要将访问请求转发给真正的被访问者，它也可以在转发请求之前或之后加入特定的逻辑，
比如安全访问限制。
>使用动态字节码生成技术扩展对象行为的原理是：我们可以对目标对象进行集成扩展，为其生成响应的子类，而子类可以通过覆盖来扩展父类的行为，只要将横切逻辑的实现放到子类中，
然后让系统使用扩展后的目标对象的子类，就可以达到与代理模式相同的效果。 

##第九章 Spring AOP一世
### 9.1 Spring AOP中的JoinPoint
- 在Spring AOP中，仅支持方法级别的JoinPoint，更确切说，只支持方法执行类型的JoinPoint
###9.2 Spring AOP中PointCut
>Spring中以接口定义org.springframework.aop.Pointcut作为其AOP框架中所有PointCut的最顶层抽象，该接口定义两个方法来捕捉系统中相应的JoinPoint：org.springframework.aop.Pointcut.getClassFilter、org.springframework.aop.Pointcut.getMethodMatcher
，并提供一个TruePointCut类型实例。
>ClassFilter和MethodMatcher分别用于匹配将被执行织入操作的对象以及相应的方法。
>>ClassFilter接口的作用是对JoinPoint所处的对象进行Class级别的类型匹配。   
>>MethodMatcher接口的作用是对JoinPoint所处对象的方法进行匹配。MethodMatcher通过重载，定义两个matcher()方法，两个方法的分界线是isRuntime()方法，在对对象具体方法进行拦截的时候，可以忽略每次方法执行的时候
调用者传入的参数，也可以每次都检查这些方法调用参数，以强化拦截条件。流程是：   
>>>1.当isRuntime返回false时，表示不会考虑具体JoinPoint的方法参数，这种类型的MethodMatcher称之为StaticMethodMatcher。由于不用
每次都检查参数，那么对于同样类型的方法匹配结果，就可以缓存在框架中，以提高性能。isRuntime()返回false表明当前的MethodMatcher为staticMethodMatcher的时候，只有boolean matcher(Method method,Class targetClass)
方法会执行，他的匹配结果是所属Pointcut主要依据。   
>>>2.当isRuntime()返回true时，表明该MethodMatcher将会每次都对方法调用的参数进行匹配检查，这种类型的methodMatcher称之为DynamicMethodMatcher。因为每次都要匹配，无法缓存，故性能相较StaticMethodMather性能差。
如果isRuntime()返回true，并且matcher(Method method,Class targetClass)返回true时，三个参数的matcher()方法会被执行，若两个参数的matcher()方法返回false，则三个参数的matcher()不会被执行。

- 在MethodMatcher类型的基础上，PointCut可以分为两类：org.springframework.aop.support.StaticMethodMatcherPointcut和org.springframework.aop.support.DynamicMethodMatcherPointcut

- 常见的PointCut
>1.org.springframework.aop.support.NameMatchMethodPointcut:根据方法名进行匹配
>2.org.springframework.aop.support.JdkRegexpMethodPointcut:根据正则表达式进行匹配
>3.org.springframework.aop.support.annotation.AnnotationMatchingPointcut：根据目标对象中存在指定类型的注解来匹配JoinPoint  
>4.org.springframework.aop.support.ControlFlowPointcut：匹配程序的调用流程，不是对某个方法执行所在的JoinPoint处的单一特征进行匹配

###9.3 Spring AOP中的Advice
- 介绍
>Advice实现了将被织入到PointCut规定的JoinPoint处的横切逻辑。在Spring中，Advice按照其自身实例(instance)能否在目标对象类的所有实例中共享这一标准，
可以划分为两大类：per-class类型的Advice和per-instance的Advice.
- per-class类型的Advice
>per-class类型的Advice是指，该类型的Advice的实例可以在目标对象类的所有实例之间共享。这种类型的Advice通常只是提供方法的拦截功能，不会为目标对象保存任何状态或者添加新的特性。   
- 包含：
1.BeforeAdvice
2.ThrowsAdvice
3.AfterReturnAdvice
4.AroundAdvice:org.aopalliance.intercept.MethodInterceptor

>per-instance类型的Advice不会在目标类所有对象实例之间共享，而是会为不同的实例对象保存他们各自的状态以及相关逻辑。
Spring AOP中，唯一一种per-instance的Advice是Introduction
Introduction可以在不改动目标类定义的情况下，为目标类增加新的属性以及行为。

###9.4Spring AOP中的Aspect
- Advisor代表Spring中的Aspect，但是，与正常的Aspect不同，Advisor通常只持有一个Pointcut和Advice
- 我们将Advisor简单划分为两个分支，一个分支是org.springframework.aop.PointcutAdvisor,另一个分支是org.springframework.aop.IntroductionAdvisor

- PointcutAdvisor的实现
>DefaultPointcutAdvisor:最通用的PointcutAdvisor，除了不能指定Introduction类型的Advice之外，剩下的任何类型的Pointcut，任何类型的Advice都能通过DefaultPointcutAdvisor使用【最常用】   
>NameMatchMethodPointcutAdvisor:是细化后的DefaultPointcutAdvisor，限定自身可以使用的Pointcut类型为NameMatchMethodPointcut   
>RegexpMethodPointcutAdvisor:同上   
>DeafultBeanFactoryPointcutAdvisor
- IntroductionAdvisor分支
>IntroductionAdvisor只能应用于类级别的拦截，只能Introduction型的Advice

- Order的作用
>当某些Advisor的Pointcur匹配了同一个Jointpoint的时候就会在同一个JoinPoint处执行多个Advice逻辑。Spring在处理同一个JoinPoint出的多个Advisor的时候，Spring会按照指定顺序和优先级来执行它们，顺序号
决定优先级，顺序号越小，优先级越高从0到1开始指定，一般0顺序号是Spring框架内部使用。在框架中，可以通过让相应的Advisor以及其他顺序紧要的bean实现org.springframework.core.Ordered接口来明确指定相应
的顺序号。

###9.5 Spring AOP的织入
>要进行织入，Aspect使用ajc编译器作为它的织入器，JBoss AOP使用自定义的ClassLoader作为它的织入器，在Spring AOP中，使用类org.springframework.aop.framework.ProxyFactory作为织入器。 

- 基本的织入器：ProxyFactory
>使用ProxyFactory只需要指定如下两个基本东西：
>>第一个是要对其进行织入的目标对象。可以通过构造方法或者对应的setter方法设置   
>>第二个是要应用到目标对象的Aspect，也就是Spring中的Advisor，可以直接使用addAdvice(..)方法，指定各种类型的Advice   
>>>对于Introduction之外的Advice，ProxyFactory内部会为这些Advice构造相应的Advisor，只不过为他们构造Advisor中使用Pointcut为Pointcut.TRUE   
>>>如果添加的Advice类型是Introduction类型，则会根据具体的Introduction类型进行区分：如果是IntroductionInfo的子类实现，因为它本身包含了必要的描述信息，框架内部会为其构造一个DefaultIntroductionAdvisor，
如果是DynamicIntroductionAdvice则会抛出AopConfigException异常。

- Spring AOP在使用代理模式实现了AOP的过程中采用了JDK动态代理和CGLIB两种机制，分别对实现了某些接口的目标类和没有实现接口的目标类进行代理
- 1.基于接口的代理--针对实现了接口的目标类进行织入逻辑，使用织入器代理生成代理对象：
- 2.基于类的代理，若目标对象没有实现任何接口，默认情况下，ProxyFactory会对目标类进行基于类的代理，即使用CGLIB
````java
//这是下面的切面逻辑，及Aspect
/**
 * AroundAdvice类型的Advice，及环绕类型的横切逻辑
 */
public class PerformanceMethodInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch watch = new StopWatch();
        try{
            watch.start();
            return invocation.proceed();
        }finally {
            watch.stop();
            if(LOGGER.isInfoEnabled()){
                LOGGER.info(watch.toString());
            }
        }
    }
}

//基于接口的代理逻辑
public class Test{
        /**
         * 基于接口的代理，apo织入器
         */
        public static void test(){
            //创建需要代理的目标对象
            MockTask mockTask = new MockTask();
            //创建织入器，用于对目标对象产生代理对象
            ProxyFactory proxyFactory = new ProxyFactory(mockTask);
            
            //通过一下两个属性的任意一个可以强制针对对象而非接口进行动态代理，即使用CGLIB
            //proxyFactory.setProxyTargetClass(true);
            //proxyFactory.setOptimize(true);
            
            //设置接口，可用于JDK动态代理，此处可以明确设置接口类型，默认情况下ProxyFactory只要检测到目标类实现了相应的接口，也会对目标类进行基于接口的代理
            proxyFactory.setInterfaces(new Class[]{ITask.class});
            //创建一个基于方法名称匹配的Aspect即advisor
            NameMatchMethodPointcutAdvisor matchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
            //给Aspect设置方法名称匹配
            matchMethodPointcutAdvisor.setMappedName("execute");
            //给Aspect设置横切逻辑，即设置Aspect
            matchMethodPointcutAdvisor.setAdvice(new PerformanceMethodInterceptor());
            //设置该切面的执行顺序
            matchMethodPointcutAdvisor.setOrder(1);
            //给织入器设置advisor
            proxyFactory.addAdvisor(matchMethodPointcutAdvisor);
            //创建代理类
            ITask proxyObj = (ITask) proxyFactory.getProxy();
            //执行逻辑
            proxyObj.execute(null);
        }
    }

    // 2.基于类的代理，若目标对象没有实现任何接口，默认情况下，ProxyFactory会对目标类进行基于类的代理，即使用CGLIB
public class Test{
        /**
         * 测试SpringAOP织入器针对没有实现接口的类使用CGLIB动态代理
         */
        public static void test2(){
            //创建织入器，将需要代理的目标对象通过构造参数传入织入器
            ProxyFactory proxyFactory = new ProxyFactory(new Executalbe());
            //创建基于方法名称的Aspect切面
            NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
            //设置Pointcut的匹配规则，匹配execu开头的方法
            nameMatchMethodPointcutAdvisor.setMappedName("execu*");
            //设置切面逻辑，即为Advisor设置Aspect
            nameMatchMethodPointcutAdvisor.setAdvice(new PerformanceMethodInterceptor());
            //将切面设置到织入器中
            proxyFactory.addAdvisor(nameMatchMethodPointcutAdvisor);
            //生成代理对象，及CGLIB创建目标类的子类，作为代理对象
            Executalbe executalbe = (Executalbe) proxyFactory.getProxy();
            executalbe.execute();
            System.out.println(executalbe.getClass());
        }
}
````

- **注意，SpringUtil中的StopWatch用于监控方法的执行时间，效率分析，注意挖掘和使用Spring中的工具**

- 但是，如果目标对象类实现了至少一个接口，我们也可以通过proxyTargetClass属性强制ProxyFactory采取基于类的代理，optimize属性同样能够起到相同的作用
- 总得来说：满足以下三个条件的任意一种，ProxyFactory也会采用基于类的代理机制：
1.如果目标类没有实现任何接口，不管proxyTargetClass的值是什么，proxyFactory会采用基于类的代理
2.如果proxyFactory的proxyTargetClass属性值被设置成true，ProxyFactory会采用基于类的代理
3.如果ProxyFactory的optimize属性值被设置成true，ProxyFactory会采用基于类的代理

- Introduction的织入
>Introduction可以为已经存在的对象类型添加新的行为，只能应用于对象级别的拦截，而不是通常的Advice的方法级别的拦截，所以，进行Introduction织入的过程中，不需要指定
Pointcut，而只需要指定目标接口类型。
Spring的Intuction支持只能通过接口定义当前对象添加新的行为，所以需要在织入的时机，指定新织入的接口类型。

#### 看清ProxyFactory的本质
根：org.springframework.aop.framework.AopProxy
>Spring AOP框架内使用AopProxy对使用的不同代理实现机制进行了适度的抽象，针对不同的实现机制提供相应的AopProxy子类实现。
1.org.springframework.aop.framework.JdkDynamicAopProxy
2.org.springframework.aop.framework.CglibAopProxy   
不同的AopProxy实现的实例化过程采用工厂模式（抽象工厂模式进行封装），通过org.springframework.aop.framework.AopProxyFactory   
AopProxyFactory需要根据createAopProxy方法传入的AdvisedSupport实例信息，来构建相应的AopProxy。org.springframework.aop.framework.AdvisedSupport所承载的信息
分为两类：org.springframework.aop.framework.ProxyConfig--记载生成代理对象的控制信息；org.springframework.aop.framework.Advised，承载生成代理对象所需要的必要
信息，如相关目标类、Advice、Advisor  
>>ProxyConfig是一个普通JavaBean对象，定义5个boolean属性值，分别控制生成代理对象时，应该采取哪些行为措施：
1.proxyTargetClass：若为true则表示使用Cglib代理，默认false
2.optimize:该属性主要告知代理对象是否需要进一步采取优化措施，同时该属性若为true，则ProxyFactory会使用CGLIB进行代理对象的生成。
3.opaque:该属性用于控制生成的代理对象是否可以强制装换成Advised，默认值是false，表示任何生成的代理对象都可以强制转型为Advised，我们可以通过Advised查询代理对象的一些状态。
4.exposeProxy:设置该属性，可以让Spring AOP框架生成的代理对象时，将当前对象绑定到ThreadLocal。如果目标对象需要访问当前代理对象，可以通过AopContext.currentProxy()取得，
处于性能方面考虑，该属性默认是false
5.forzen:如果将forzen设置为true，那么一旦针对代理对象生成的各项信息配置完成，则不容许更改，默认false。   
>>Advised可以设置或者查询代理对象的具体信息，如要针对那些目标类生成代理对象，要为代理对象生成那些横切逻辑等，简单说：可以使用Advised接口访问相应代理对象所持有的Advisor
，进行添加Advisor、移除Advisor等相关动作。

>ProxyFactory及AopProxyFactory（即AopProxy）和AdvisedSupport于一身，所以可以通过ProxyFactory设置生成代理对象所需要的相关信息，也可以通过ProxyFactory取得最终
生成的代理对象。前者是AdvisedSupport的职责，后者是AopProxy的职责。   
为了重用逻辑，Spring AOP框架在实现的时候，将一些共用逻辑抽取到了org.springframework.aop.framework.ProxyCreatorSupport中，它自身继承了AdvisedSupport，为了
简化子类生成不同类型的AopProxy的工作，ProxyCreatorSupport内部持有一个AopProxyFactory实例，默认采用DefaultAopProxyFactory。   
- ProxyFactory只是Spring Aop 中最基本的织入器实现。
1.org.springframework.aop.aspectj.annotation.AspectJProxyFactory
2.org.springframework.aop.framework.ProxyFactory
3.org.springframework.aop.framework.ProxyFactoryBean

#### 容器的织入器--ProxyFactoryBean
> ProxyFactoryBean = Proxy + FactoryBean,FactoryBean的作用是容器中的某个对象持有某个FactoryBean的引用，它取的不是FactoryBean本身，而是FactoryBean的getObject()
方法所返回的代理对象。

####加快织入的自动化进程
- 自动代理的实现的原理：Spring AOP的自动代理是建立在IOC容器的BeanPostProcessor概念之上，通过BeanPostProcessor，我们可以遍历容器中所有bean的基础上，对遍历到的bean进行一些操作。
，其实只要提供一个BeanPostProcessor，然后在这个BeanPostProcessor内部，当对象实例化时，为其生成一个代理对象并返回，而不是实例化后的目标对象本身，从而达到代理对象自动生成的目的。

- Spring中的自动代理实现类：
>1.org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator【半自动步枪】
2.org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator【全自动步枪】:将该自动代理实现类注册到容器后，它会自动搜寻容器内所有的Advisor，然后
根据各个Advisor所提供的拦截信息，为符合条件的容器中的目标对象生成相应的代理对象。该自动代理类只对Advisor有效，因为只有Advisor才既有Pointcut信息以捕捉符合条件的目标
对象，又有相应的Advice。

### TargetSource
>TargetSource的作用就好像是为了目标对象在外面加了一个壳，或者说，他好像是目标对象的容器。当每个针对目标对象的方法调用经历层层拦截而到达调用链的终点时，就改该调用
目标对象上定义的方法了。但这时，Spring AOP做了点手脚，它不是直接调用这个目标对象上的方法，而是通过插足与调用链和实际目标对象之间的某个TargetSource来取得具体目标对象，
然后调用TargetSource来取得具体目标对象，然后再调用从TargetSource中取得的目标对象上相应的方法。

#### 可用的TargetSource实现类
>1.org.springframework.aop.target.SingletonTargetSource
 2.org.springframework.aop.target.PrototypeTargetSource
 3.org.springframework.aop.target.HotSwappableTargetSource:使用org.springframework.aop.target.HotSwappableTargetSource封装目标对象，可以让我们在应用程序运行
 的时候，根据某种特定条件，动态的替换目标对象类的具体实现。使用swap方法，可以用新的目标对象实例将旧的目标对象实例替换掉。
 4.org.springframework.aop.target.CommonsPoolTargetSource
 5.org.springframework.aop.target.ThreadLocalTargetSource

##第十章 Spring AOP二世
### @AspectJ 形式的AOP使用
```java
@Aspect
public class PerformanceTraceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceTraceAspect.class);

    @Pointcut("execution(public void *.method1()) || execution(public void *.methods())")
    public void pointcutName(){}

    @Around("pointcutName()")
    public Object performanceTrace(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return proceedingJoinPoint.proceed();
        }finally {
            stopWatch.stop();
            LOGGER.error(stopWatch.prettyPrint());
        }
    }
}

```
1.编程方式织入：
```java
public class Test {
    public static void main(String[] args){
        test1();
    }
    public static void test1(){
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory();
        aspectJProxyFactory.setProxyTargetClass(true);
        aspectJProxyFactory.setTargetClass(Foo.class);
        aspectJProxyFactory.addAspect(PerformanceTraceAspect.class);
        Object proxy= aspectJProxyFactory.getProxy();
        ((Foo)proxy).method1();
        ((Foo)proxy).method2();
    }
}

```
2.通过自动代理织入
org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator注入Spring容器，则会自动发现切面

- @AspectJ形式的Pointcut
>@Pointcut是方法级别的注解，附着于方法之上，方法若为private类型，则该Pointcut只允许当前切面使用，若为public类型，则允许其他切面调用。
AspectJ的Pointcut表达式支持&&，||和！逻辑运算符，进行Pointcut表达式之间的逻辑运算。
```java
@Aspect
public class AspectJDemo {
    //设置成private 则其他切面无法调用该Pointcut
    @Pointcut("execution(void method1())")
    private void method1Exec(){}

    @Pointcut("execution(void method2())")
    private void method2Exec(){}

    @Pointcut("execution(void method1()) || execution(void method2())")
    public void bothMethodExec(){}

    @Pointcut("method1Exec() || method2Exec()")
    public void bothMethod2Exec(){}
}
``` 
- @AspectJ形式Pointcut表达式的标识符
>execution：帮助我们匹配拥有指定方法签名的JointPoint，使用该标识符的Pointcut表达式规定的格式如下：
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throw-pattern?)，其中方法返回类型、
方法名和参数部分的匹配模式是必须指定的，其他部分的匹配模式可以省略。
如execution (public void Foo.doSomthing(String))-->execution( void doSomething(String))。
execution表达式中有两种通配符：\*和.. ，
\*可以用于任何部分的匹配模式中，可以匹配相邻的多个字符，即一个word，如：execution(\* \*(String))或者execution(\* \*(\*))；
..通配符可以在两个位置使用，一个是在declaring-type-pattern规定的位置，一个在方法参数匹配模式的的位置，如果用于declaring-type-pattern规定的位置，则可以指定多个
层次的类型声明，如：execution(void cn.spring21.\*.doSomething(\*))【只能指定到cn.spring21这一层下的所有类型】；execution(void cn.spring21..\*.doSomething(\*))【可以匹配cn.spring21包下的所有类型，以及cn.spring21下层包下声明的所有类型】；
..如果用于方法参数列表匹配位置，则表示该方法可以有0到多个参数，类型不限：execution(void \*.doSomething(..)),注：此处如果用*匹配只能配置一个参数。
如：execution(void \*.doSomething(String,\*))【匹配两个参数，第一个参数为String类型，第二个参数类型不限】，
execution(void \*.doSomething(..,String))，【匹配拥有多个参数的doSomething方法，之前几个参数类型不限，但最后一个参数必须是String】,
execution(void \*.doSomething(\*,String,..))【匹配拥有多个参数的doSomething方法，第一个参数类型不限，第二个参数类型为String，其他剩余参数数量，类型均不限】

>within：within标识符只接受类型声明，它将会匹配指定类型下的所有Joinpoint。不过，因为Spring AOP只支持方法级别的Joinpoint，所以，在为within指定某个类后，它将匹配指定类
所声明的所有方法指定。
```java
@Aspect
public class AspectJDemo {
    @Pointcut("within(com.snail.springbootsource.capter10..*)")
    public void withinMethod2(){}

    @Pointcut("within(com.snail.springbootsource..*)")
    public void withinMethod3(){}
}
```
>this和target：   
>args：帮助我们捕捉拥有指定参数类型、指定参数数量的方法级Joinpoint，而不管在什么类型中被声明，与直接使用execution标识符可以直接明确指定方法参数类型不同，
args标识符会在运行期间动态检查参数的类型，例如参数接受是Object user，在运行期间传入的参数只要是User类型，则args就会匹配，而此种情况execution不会匹配
```java
@Aspect
public class AspectJDemo {
    @Pointcut("args(com.snail.springbootsource.capter10.Foo)")
    public void argsMethod(){}
}
```
>@within：如果使用@within指定了某种类型的注解，那么，只要对象标注了该类型的注解，使用了@within标识符的Pointcut表达式将会匹配该对象内部所有的Joinpoint，
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface AnyJointpointAnnotation {
}

@Aspect
public class AspectJDemo {
    @Pointcut("@within(AnyJointpointAnnotation)")
    public void within2Method(){}
}
```
>@target：与@within没有太大区别，只不错@within属于静态匹配，@target则是在运行时动态匹配Joinpoint   
>@args：使用@args标识符的Pointcut表达式将会尝试检查当前方法级的Joinpoint的方法参数类型。如果该方法传入的参数类型拥有@args所指定的注解，当前Joinpoint将被匹配，否则不会被匹配。   
>@annotation：使用@annotation标识符的Pointcut表达式，将会尝试检查系统中所有对象中所有方法级别Joinpoint。如果被检测的方法标注有@annotation标识符所指定的注解类型，
那么当前方法所在的Joinpoint将会被Pointcut表达式所匹配。
>>注意，所有已@开头的标识符，都只能指定注解类型参数。
```java
@Aspect
public class AspectJDemo {
    @Pointcut("@annotation(AnyJointpointAnnotation)")
    public void annotationMethod(){}
}
```
#### @AspectJ形式的Advice
>1.@Before
 2.@AfterReturning
 3.@AfterThrowing
 4.@After
 5.@Aroud
 6.@DeclareParents：用于标注Introdution类型的Advice
 ```java
@Aspect
public class AspectJDemo {
    @Pointcut("execution(void method1())")
    public void method1Exec(){}

    @Pointcut("execution(void method2())")
    public void method2Exec(){}

    @Before("withinMethod()")
    public void setUnpResourceFolder(){
        //业务逻辑
    }

    @After("method2Exec()")
    public void cleanUpResourceFolder(){
        //切面逻辑
    }
    
    @Around("execution(void *.*..*(..))")
    public void onUpResourceFolder(){
        //切面逻辑
    }
}
```

- 2018年12月17日 12:29:29 232/673 基于Schema 的AOP

##第十一章 AOP应用案例

##第十二章 Spring AOP之扩展篇

# 第四部分 使用Spring访问数据
- spring提供的数据访问层分为三个部分：1.统一的数据访问异常层析体系；2.JDBC API的最佳实践；3.以统一的方式对各种ORM方案的继承；
## 第十三章 统一的数据访问异常层析体系

###DAO模式的背景

###梦想照进现实

###发现问题，解决问题

###不重新发明轮子

## 第十四章 JDBC API的最佳实践
- Spring提供两种使用JDBC API的最佳实践：1.以JdbcTemplate为核心的基于Template的JDBC使用方式；2.在JdbcTemplate基础之上构建的基于操作对象的JDBC使用方式。
###基于JdbcTemplate的JDBC使用方式
#### Spring中的DataSource
1.简单的DataSource实现
2.拥有连接缓冲池的DataSource实现
3.支持分布式事务的DataSource实现：javax.sql.XADataSource
>若需要自定义DataSource，只需要扩展org.springframework.jdbc.datasource.AbstractDataSource即可
org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource会持有一组DataSource，当它的getConnection()被调用时，会根据条件从这组
DataSource中查找符合条件的DataSource，然后调用查找取得的DataSource上的getConnection()。如果应用程序中有多个数据库，并且需要根据情况让应用程序访问
不同的数据库，那么扩展并实现这个子类即可【动态数据源使用】。

###基于操作对象的JDBC使用方式
- org.springframework.jdbc.object.RdbmsOperation

##第十五章 Spring对各种ORM的集成
Spring对各种ORM集成主要体现一下三个方面：1.统一资源管理方式；2.特定于ORM的数据访问异常到Spring统一异常体系的转译；3.统一的数据访问事务管理及控制方式

##第十六章 Spring数据访问之扩展篇

# 第五部分 事务管理
## 第十七章 有关事务的~子
### 17.1 认识事务本身
- 事务的四个限定属性：原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）和持久性（Durabilitu）
>隔离性：Read Uncommitted、Read Committed、Repeatable Read和Serializable
脏读、不可重复读、幻读   
>一个典型的事务处理场景中有一下几个参与者：
1.Resource Manager：简称RM，它负责存储并管理系统数据资源的状态，比如数据库服务器、JMS消息服务器等都是相应的Resource Manager。
2.Transaction Process Monitor：简称TPM或TP Monitor，它的职责是在分布式事务场景中协调包含多个RM的事务处理。
3.Transaction Manager：简称TM，它可以认为是TP Monitor中的核心模块，直接负责多RM之间事务处理的协调工作，并且提供事务界定、事务上下文传播等功能接口。
4.Application：以独立形式存在的或者运行于容器中的应用程序。   
>根据整个事务中涉及的RM多寡来区分事务类型的话，可以将事务分为两类，即全局事务和局部事务
1.全局事务：如果整个事务处理过程中有多个RM参与，那么就需要引入TP Monitor来协调多个RM之间的事务处理。TM Monitor将采用两阶段提交协议来保证整个事务的ACID属性。
这种场景下的事务，就称为全局事务或分布式事务。所有应用程序提交的事务请求，需要通过TP Monitor的调配后，直接由TM统一协调，TM将使用两阶段提交协议来协调多个RM
之间的事务处理。
2.局部事务：如果当前事务只有一个RM参与其中，称之为局部事务。局部事务只包含一个RM，所以没有必要引入相应的TP Monitor来帮助协调管理多个RM之间的事务处理，应用程序
可以直接和RM打交道。通常情况下，相应的RM都有内置的事务支持，所有，在局部事务中，我们更倾向于直接使用RM的内置事务支持。

## 第十八章 群雄逐鹿下的Java事务管理
### 18.2java 平台的分布式事务支持
- java平台上的分布式事务管理，主要通过JTA（Java Transaction api）或者JCA(Java Connector Architecture)提供支持
>java平台上存在的几个独立的并且比较成熟的JTA实现产品：JOTM、Atomikos、JBoss Transactions

2019年1月2日 21:34:12 378/673
## 第十九章 Spring事务王国下的架构
- Spring的事务框架设计理念：让事务管理的关注点与数据访问关注点相分离
### 19.1统一中原的过程
>org.springframework.transaction.PlatformTransactionManager是Spring事务抽象架构的核心接口，他的主要作用是为应用程序提供界定事务的统一方式。
### 19.2 和平年代
>Spring事务涉及3个核心接口
1.org.springframework.transaction.PlatformTransactionManager:负责界定事务边界
2.org.springframework.transaction.TransactionDefinition：负责定义事务相关属性，包括隔离级别、传播行为等
3.org.springframework.transaction.TransactionStatus：负责事务开启至事务结束期间的事务状态   
>将TransactionDefinition按照编程式事务和声明式事务分为两个分支。
2019年1月3日 21:53:30 402/673
## 第二十章 使用Spring进行事务管理
## 第二十一章 Spring事务管理之扩展篇
ThreadLocal的概念以及使用

2019年1月4日 22:23:17 438/673
#第六部分 Spring的web MVC框架

##第二十二章 迈向SpringMVC的旅程
###22.3 Servlet与jsp的联盟
>MVC(Model-View-Controller，模型-视图-控制器)，各个组件的作用如下：
1.控制器负责接收视图发送的请求并进行处理，它会根据请求条件通知模型进行应用程序状态的更新，之后选择合适的视图显示给用户。
2.模型通常封装了应用的逻辑以及数据状态。当控制器通知模型进行状态更新的时候，模型封装的相应逻辑将被调用。执行完成后，模型通常会通过事件机制通知视图状态更新完毕，从而
视图可以显示最新的数据状态。
3.视图是面向用户的接口。当用户通过视图发起某种请求的时候，视图将这些请求转发给控制器进行处理。处理流程流经控制器和模型之后，最终视图将接收模型的状态更新通知，然后视图
将结合模型数据，更新自身的显示。
>>可见，最初意义上的MVC模式，在视图与模型间的数据同步工作是采用从模型push到视图的形式完成的。但是对于web应用而言，局限于所用的协议和应用场景，无法实现从模型push数据到
视图的功能。所以，只能对MVC的组件最初的定义做一些调整，由控制器和模型进行交互，在原来的通知模型更新应用程序状态的基础上，还要获取更新的结果数据，然后将更新的模型数据
一并转发给视图。也就是说，我们现在改由控制器从模型中pull数据给视图，这个意义上的mvc称为web mvc。

###22.4 数英雄人物，还看今朝
- 请求驱动的web框架：这种框架是基于Servlet的请求/相应（request/response）处理模型构建的，比如：struts，webwork，SpringMVC
- 事件驱动的web框架：如JSF

>一般webMVC 框架都会接口Font Controller以及page Controller模式，对单一的Servlet控制器做进一步的改进，对原先过于耦合的控制器逻辑进行逐步的分离。具体而言，就是由原来的
单一的Servlet作为整个应用程序的Font Controller。该Servlet在接收到具体的web处理请求后，会参照预先可配置的英社信息，将待处理的web处理请求转发给次一级的控制器（sub-controller）
来处理。

##第二十三章 Spirng MVC初体验
>对于一个设计良好的web应用程序而言，虽然web层依赖于业务层，但是业务层却不应该对web层有任何依赖。web层只是看做是公开业务逻辑的一种视角或者交互方式。这样看待系统的好处是
，业务层可以独立设计并实现，而不需要关心最终通过什么样的手段将服务公开给用户。

##第二十四章 近距离接触Spring MVC的主要角色


##第二十五章 认识更多Spring MVC 家族成员
2019年1月15日 12:30:20 536/673
2019年1月15日 18:55:02 566/673
##第二十六章 Spring MVC中基于注解的Controller

##第二十七章 Spring MVC之扩展篇
2019年1月15日 21:19:08 593/673

#第七部分 Spring框架对J2EE服务的集成和支持

##第二十八章 Spring框架内的JNDI支持

##第二十九章 Spring框架对JMS的集成

##第三十章 使用Spring发送E-mail

##第三十一章 Spring中的任务调度和线程池支持

##第三十二章 Spring远程方案