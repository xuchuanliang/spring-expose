package com.snail.springbootsource.capter09.b2;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DisCountMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retrunValue = invocation.proceed();

        return null;
    }
}
