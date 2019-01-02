package com.snail.springbootsource.capter05.b1;

/**
 * 自定义事件监听器具体实现类SimpleMethodExecutionEventListener的定义
 */
public class SimpleMethodExecutionEventListener implements MethodExecutionEventListener {
    @Override
    public void onMethodBegin(MethodExecutionEvent executionEvent) {
        String methodName = executionEvent.getMethodName();
        System.out.println("start to execute the method[" + methodName + "].");
    }

    @Override
    public void onMethodEnd(MethodExecutionEvent executionEvent) {
        String methodName = executionEvent.getMethodName();
        System.out.println("finished to execute the method[" + methodName + "].");
    }
}
