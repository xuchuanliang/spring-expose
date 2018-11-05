package com.snail.springbootsource.capter05.b1;

import java.util.EventListener;

/**
 *自定义事件监听器MethodExecutionEventListener定义
 * 实现针对自定义事件类的事件监听器接口
 */
public interface MethodExecutionEventListener extends EventListener {
    /**
     * 处理方法开始执行的时候发布的MethodExecutionEvent事件
     * @param executionEvent
     */
    void onMethodBegin(MethodExecutionEvent executionEvent);

    /**
     * 处理方法执行将结束时候发布的MethodExecutionEvent事件
     * @param executionEvent
     */
    void onMethodEnd(MethodExecutionEvent executionEvent);
}
