package com.snail.springbootsource.capter20;

public class DataSourceThreadLocal {
    private final static ThreadLocal threadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return DataSources.PRIMARY;
        }
    };

    public static void change(DataSources dataSources){
        threadLocal.set(dataSources);
    }
    public static void clear(){
        threadLocal.remove();
    }
    public static void reset(){
        threadLocal.set(DataSources.PRIMARY);
    }
}
