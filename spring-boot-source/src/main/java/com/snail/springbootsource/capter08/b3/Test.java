package com.snail.springbootsource.capter08.b3;

import org.springframework.cglib.proxy.Enhancer;

public class Test {
    public static void main(String[] args){
        //使用Enhancer为目标类动态生成子类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Requestable.class);
        enhancer.setCallback(new RequestCallBack());
        Requestable proxy = (Requestable) enhancer.create();
        proxy.request();
    }
}
