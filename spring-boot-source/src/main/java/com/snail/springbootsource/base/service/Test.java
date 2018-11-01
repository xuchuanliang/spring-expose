package com.snail.springbootsource.base.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class Test {
    public void test(){
        ConfigurableBeanFactory configurableBeanFactory = new DefaultListableBeanFactory();
        configurableBeanFactory.addBeanPostProcessor(new PasswordDecodedPostProcessor());
    }
}
