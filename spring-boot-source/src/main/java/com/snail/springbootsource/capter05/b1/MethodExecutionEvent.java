package com.snail.springbootsource.capter05.b1;

import java.util.EventObject;

/**
 * 针对方法执行事件的自定义事件类型定义
 * 给出自定义事件类型
 */
public class MethodExecutionEvent extends EventObject {

    private String methodName;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source,String methodName){
        super(source);
        this.methodName = methodName;
    }
    public String getMethodName(){
        return this.methodName;
    }
    public void setMethodName(String methodName){
        this.methodName = methodName;
    }
}
