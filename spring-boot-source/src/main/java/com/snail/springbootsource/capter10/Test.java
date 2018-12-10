package com.snail.springbootsource.capter10;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

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
