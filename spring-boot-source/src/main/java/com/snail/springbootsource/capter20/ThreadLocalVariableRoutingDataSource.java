package com.snail.springbootsource.capter20;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

public class ThreadLocalVariableRoutingDataSource  extends AbstractRoutingDataSource {



    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
