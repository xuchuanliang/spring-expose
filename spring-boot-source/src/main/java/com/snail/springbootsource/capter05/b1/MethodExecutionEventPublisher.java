package com.snail.springbootsource.capter05.b1;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件发布者，本身作为事件源，会在合适的时机将相应的事件发布给对应的事件监听器
 */
public class MethodExecutionEventPublisher {

    private List<MethodExecutionEventListener> listeners = new ArrayList<>();

    public void methodToMonitor(){
        MethodExecutionEvent event2Publish = new MethodExecutionEvent(this,"methodToMonitor");
        publishEvent(MethodExecutionStatus.BEGIN,event2Publish);

        //执行事件的逻辑
        //......

        publishEvent(MethodExecutionStatus.END,event2Publish);
    }

    protected void publishEvent(MethodExecutionStatus methodExecutionStatus,MethodExecutionEvent methodExecutionEvent){
        //此处为了防止移除和清除listeners导致问题，使用copy的方式
        List<MethodExecutionEventListener> copyListeners = new ArrayList<>(listeners);
        for(MethodExecutionEventListener listener:copyListeners){
            if(MethodExecutionStatus.BEGIN.equals(methodExecutionStatus)){
                listener.onMethodBegin(methodExecutionEvent);
            }else if(MethodExecutionStatus.END.equals(methodExecutionStatus)){
                listener.onMethodEnd(methodExecutionEvent);
            }
        }
    }

    public void addMethodExecutionEventListener(MethodExecutionEventListener methodExecutionEventListener){
        this.listeners.add(methodExecutionEventListener);
    }

    public void removeMethodExecutorEventListener(MethodExecutionEventListener methodExecutionEventListener){
        if(this.listeners.contains(methodExecutionEventListener)){
            this.listeners.remove(methodExecutionEventListener);
        }
    }

    public void removeAllListener(){
        this.listeners.clear();
    }


}
