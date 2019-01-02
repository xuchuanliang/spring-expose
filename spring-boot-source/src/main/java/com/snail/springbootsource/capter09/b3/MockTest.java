package com.snail.springbootsource.capter09.b3;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class MockTest {
    public static void main(String[] args) {
//        test();
        test2();
    }

    /**
     * 基于接口的代理，apo织入器
     */
    public static void test() {
        //创建需要代理的目标对象
        MockTask mockTask = new MockTask();
        //创建织入器，用于对目标对象产生代理对象
        ProxyFactory proxyFactory = new ProxyFactory(mockTask);
//        proxyFactory.setProxyTargetClass(true);
//        proxyFactory.setOptimize(true);
        //设置接口，可用于JDK动态代理，此处可以明确设置接口类型，默认情况下ProxyFactory只要检测到目标类实现了相应的接口，也会对目标类进行基于接口的代理
        proxyFactory.setInterfaces(new Class[]{ITask.class});
        //创建一个基于方法名称匹配的Aspect即advisor
        NameMatchMethodPointcutAdvisor matchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        //给Aspect设置方法名称匹配
        matchMethodPointcutAdvisor.setMappedName("execute");
        //给Advisor设置横切逻辑，即设置Aspect
        matchMethodPointcutAdvisor.setAdvice(new PerformanceMethodInterceptor());
        //设置该切面的执行顺序
        matchMethodPointcutAdvisor.setOrder(1);
        //给织入器设置advisor
        proxyFactory.addAdvisor(matchMethodPointcutAdvisor);
        //创建代理类
        ITask proxyObj = (ITask) proxyFactory.getProxy();
        //执行逻辑
        proxyObj.execute(null);
        System.out.println(proxyObj.getClass());
    }

    /**
     * 测试SpringAOP织入器针对没有实现接口的类使用CGLIB动态代理
     */
    public static void test2() {
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
