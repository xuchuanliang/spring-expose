package com.snail.springbootsource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSourceApplicationTests {

    @Test
    public void contextLoads() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    }

}
