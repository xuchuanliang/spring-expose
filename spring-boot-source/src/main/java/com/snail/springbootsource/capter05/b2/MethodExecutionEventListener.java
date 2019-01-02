package com.snail.springbootsource.capter05.b2;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MethodExecutionEventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MethodExecutionEvent) {
            //执行逻辑
        }
    }

}
