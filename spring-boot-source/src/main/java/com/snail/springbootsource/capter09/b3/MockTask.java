package com.snail.springbootsource.capter09.b3;

/**
 * 要拦截的目标类
 */
public class MockTask implements ITask {
    @Override
    public void execute(TaskExecutionContext taskExecutionContext) {
        System.out.println("task executed");
    }
}
