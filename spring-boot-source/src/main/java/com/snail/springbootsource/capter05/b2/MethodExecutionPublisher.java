package com.snail.springbootsource.capter05.b2;

import com.snail.springbootsource.capter05.b1.MethodExecutionStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class MethodExecutionPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    public void methodToMonitor(){
        MethodExecutionEvent methodExecutionEvent = new MethodExecutionEvent(this,"methodExecutionEvent",MethodExecutionStatus.BEGIN);
        applicationEventPublisher.publishEvent(methodExecutionEvent);
        //执行代码逻辑
        //
        MethodExecutionEvent methodExecutionEvent1 = new MethodExecutionEvent(this,"methodExecutionEvent",MethodExecutionStatus.END);
        applicationEventPublisher.publishEvent(methodExecutionEvent1);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
