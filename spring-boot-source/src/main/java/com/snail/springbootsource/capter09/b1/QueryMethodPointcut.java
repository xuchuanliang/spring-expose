package com.snail.springbootsource.capter09.b1;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class QueryMethodPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(method.getName().startsWith("get") && targetClass.getPackage().getName().startsWith("...dao")){
            //做一些处理
        }
        return false;
    }
}
