package com.snail.springbootsource.capter09.b3;

/**
 * 通过制定更多的ProxyFactory控制属性，来控制使用动态代理还是CGLIB动态代理
 */
public interface ITask {
    void execute(TaskExecutionContext taskExecutionContext);
}

