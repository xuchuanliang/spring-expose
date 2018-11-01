package com.snail.springbootsource.base.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 用于解密的自定义BeanPostProcessor实现类
 */
public class PasswordDecodedPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof PasswordDecodable){
            String encodedPassword = ((PasswordDecodable)bean).getEncodedPassword();
            String decodePassword = decodePassword(encodedPassword);
            ((PasswordDecodable)bean).setDecodedPassword(decodePassword);
        }
        return bean;
    }

    /**
     * 模拟密码解密
     * @param encodedPassword
     * @return
     */
    private String decodePassword(String encodedPassword) {
        return "";
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
