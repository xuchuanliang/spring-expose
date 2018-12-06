package com.snail.springbootsource.capter09.b3;

/**
 * 测试SpringAOP织入器针对没有实现接口的类使用CGLIB动态代理
 */
public class Executalbe {
    public void execute(){
        System.out.println("executable without any Interface");
    }
}
