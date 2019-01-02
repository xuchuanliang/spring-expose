package com.snail.springbootsource.share.factoryBean;

import org.springframework.beans.factory.FactoryBean;

import java.util.Calendar;
import java.util.Date;

public class NextDayFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 2);
        return calendar.getTime();
    }

    @Override
    public Class<?> getObjectType() {
        return Date.class;
    }
}
