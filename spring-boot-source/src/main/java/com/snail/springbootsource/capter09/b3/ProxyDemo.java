package com.snail.springbootsource.capter09.b3;

import com.snail.springbootsource.capter09.b2.ResourceSetupBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StopWatch;

/**
 * Spring最基本的织入器：ProxyFactory
 */
public class ProxyDemo {
    public static void main(String[] args){
        try {
            test2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void test1(){
        //织入器
        ProxyFactory proxyFactory = new ProxyFactory();
        //切面
        Advisor advisor = new DefaultPointcutAdvisor();
        //切入点
        Pointcut pointcut = new NameMatchMethodPointcut();
        //横切逻辑
        Advice advice = new ResourceSetupBeforeAdvice(new ClassPathResource(""));

        ((DefaultPointcutAdvisor) advisor).setPointcut(pointcut);
        ((DefaultPointcutAdvisor) advisor).setAdvice(advice);
        proxyFactory.addAdvisor(advisor);

        //设置目标类
        proxyFactory.setTarget(new TargetObject());
        //获取代理对象
        proxyFactory.getProxy();
    }

    /**
     * 使用StopWatch监控性能
     */
    public static void test2() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("first ");
        Thread.sleep(1000);
        stopWatch.stop();
        stopWatch.start("second");
        Thread.sleep(1500);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
