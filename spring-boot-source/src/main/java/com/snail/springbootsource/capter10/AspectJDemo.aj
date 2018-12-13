package com.snail.springbootsource.capter10;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.weaver.patterns.WithinPointcut;

@Aspect
public class AspectJDemo {
    @Pointcut("execution(void method1())")
    public void method1Exec(){}

    @Pointcut("execution(void method2())")
    public void method2Exec(){}

    @Pointcut("execution(void method1()) || execution(void method2())")
    public void bothMethodExec(){}

    @Pointcut("method1Exec() || method2Exec()")
    public void bothMethod2Exec(){}

    @Pointcut("within(com.snail.springbootsource.capter10.Foo)")
    public void withinMethod(){}

    @Pointcut("within(com.snail.springbootsource.capter10..*)")
    public void withinMethod2(){}

    @Pointcut("within(com.snail.springbootsource..*)")
    public void withinMethod3(){}

    @Pointcut("args(com.snail.springbootsource.capter10.Foo)")
    public void argsMethod(){}

    @Pointcut("@within(AnyJointpointAnnotation)")
    public void within2Method(){}

    @Pointcut("@annotation(AnyJointpointAnnotation)")
    public void annotationMethod(){}
}
