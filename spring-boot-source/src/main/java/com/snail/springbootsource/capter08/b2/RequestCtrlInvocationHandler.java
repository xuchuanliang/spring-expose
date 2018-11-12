package com.snail.springbootsource.capter08.b2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk动态代理
 */
public class RequestCtrlInvocationHandler implements InvocationHandler {
    private Object target;

    public RequestCtrlInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("request")){
            //只能逻辑
            return method.invoke(target,args);
        }
        return null;
    }
}
