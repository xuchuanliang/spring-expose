package com.snail.springbootsource.capter08.b2;

import com.snail.springbootsource.capter08.b1.ISubject;
import com.snail.springbootsource.capter08.b1.SubjectImpl;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        Proxy.newProxyInstance(RequestCtrlInvocationHandler.class.getClassLoader(), new Class[]{ISubject.class}, new RequestCtrlInvocationHandler(new SubjectImpl()));
    }
}
