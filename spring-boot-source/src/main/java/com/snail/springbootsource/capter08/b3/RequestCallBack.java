package com.snail.springbootsource.capter08.b3;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLB动态代理
 */
public class RequestCallBack implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getName().equals("request")){
            //执行逻辑
            return method.invoke(o,objects);
        }
        return null;
    }
}
