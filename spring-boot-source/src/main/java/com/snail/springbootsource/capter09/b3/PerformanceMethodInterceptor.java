package com.snail.springbootsource.capter09.b3;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * AroundAdvice类型的Advice，及环绕类型的横切逻辑
 */
public class PerformanceMethodInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch watch = new StopWatch();
        try{
            watch.start();
            return invocation.proceed();
        }finally {
            watch.stop();
            if(LOGGER.isInfoEnabled()){
                LOGGER.info(watch.toString());
            }
        }
    }
}
