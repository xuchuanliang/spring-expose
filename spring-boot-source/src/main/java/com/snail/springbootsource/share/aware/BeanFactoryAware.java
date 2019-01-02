package com.snail.springbootsource.share.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

public class BeanFactoryAware implements org.springframework.beans.factory.BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        
    }
}
