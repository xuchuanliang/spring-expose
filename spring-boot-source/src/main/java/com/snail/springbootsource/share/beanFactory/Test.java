package com.snail.springbootsource.share.beanFactory;

import com.snail.springbootsource.share.entity.Event;
import com.snail.springbootsource.share.entity.Message;
import com.snail.springbootsource.share.entity.Order;
import com.snail.springbootsource.share.entity.User;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class Test {
    public static void main(String[] args){
//        BeanDefinitionRegistry registry = new DefaultListableBeanFactory();
////        BeanFactory container = bindViaCode(registry);
//        BeanFactory container = bindViaXML(registry);
//        Event event = (Event) container.getBean("event");
//        System.out.println(event);
//        System.out.println(event.getOrder());
//        System.out.println(event.getUser());

        wrapper();
    }

    /**
     * BeanFactory之编码方式使用Spring 简单Ioc容器
     * @param registry
     * @return
     */
    public static BeanFactory bindViaCode(BeanDefinitionRegistry registry){
        //创建Bean定义
        BeanDefinition userDefinition = new RootBeanDefinition(User.class);
        BeanDefinition eventDe = new RootBeanDefinition(Event.class);
        BeanDefinition messageDe = new RootBeanDefinition(Message.class);
        BeanDefinition orderDe = new RootBeanDefinition(Order.class);
        //将Bean定义注册到容器中
        registry.registerBeanDefinition("user",userDefinition);
        registry.registerBeanDefinition("event",eventDe);
        registry.registerBeanDefinition("message",messageDe);
        registry.registerBeanDefinition("order",orderDe);
        //指定依赖关系
        //1.通过构造方法注入方式
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0,orderDe);
        constructorArgumentValues.addIndexedArgumentValue(1,userDefinition);
        ((RootBeanDefinition) eventDe).setConstructorArgumentValues(constructorArgumentValues);
        //2.通过set方法注入
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("order",orderDe);
        mutablePropertyValues.add("user",userDefinition);
        ((RootBeanDefinition) eventDe).setPropertyValues(mutablePropertyValues);
        return (BeanFactory) registry;
    }

    public static BeanFactory bindViaXML(BeanDefinitionRegistry beanDefinitionRegistry){
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        beanDefinitionReader.loadBeanDefinitions("classpath:/beans.xml");
        return (BeanFactory) beanDefinitionRegistry;
    }

    public static void wrapper(){
        BeanWrapperImpl wrapper = new BeanWrapperImpl();
        wrapper.setBeanInstance(new Event());
        wrapper.setPropertyValue("order",new Order());
        System.out.println(wrapper.getWrappedInstance());
        System.out.println(wrapper.getPropertyValue("order"));
    }
}
