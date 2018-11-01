package com.snail.springbootsource.base.test;

import com.snail.springbootsource.base.entity.FXNewsBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test1 {
    /**
     * 插手容器的启动
     * @return
     * <p>
     *     使用PropertyPlaceholderConfigurer 允许在XML配置文件中使用占位符，默认若properties中未找到相应的配置文件
     *    则会从System.property中获取
     * </p>
     */
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setSearchSystemEnvironment(true);
        propertyPlaceholderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_FALLBACK);
        return propertyPlaceholderConfigurer;
    }

    /**
     * 插手容器的启动
     * @return
     * <p>
     *     PropertyOverrideConfigurer可以对容器中配置的任何想要处理的bean定义的property信息进行覆盖替换
     * </p>
     */
    public PropertyOverrideConfigurer propertyOverrideConfigurer(){
        PropertyOverrideConfigurer propertyOverrideConfigurer = new PropertyOverrideConfigurer();
        return propertyOverrideConfigurer;
    }

    public void test(){
        FXNewsBean fxNewsBean = new FXNewsBean();
        BeanWrapper beanWrapper = new BeanWrapperImpl(fxNewsBean);
    }

}
