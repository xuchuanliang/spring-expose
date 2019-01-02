package com.snail.springbootsource.capter10;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Aspect
public class PerformanceTraceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceTraceAspect.class);

    @Pointcut("execution(public void *.method1()) || execution(public void *.methods())")
    public void pointcutName() {
    }

    @Around("pointcutName()")
    public Object performanceTrace(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return proceedingJoinPoint.proceed();
        } finally {
            stopWatch.stop();
            LOGGER.error(stopWatch.prettyPrint());
        }
    }
}
