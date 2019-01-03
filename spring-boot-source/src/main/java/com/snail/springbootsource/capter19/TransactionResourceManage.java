package com.snail.springbootsource.capter19;

public class TransactionResourceManage {
    private static ThreadLocal threadLocal = new ThreadLocal();

    public static Object getResource(){
        return threadLocal.get();
    }

    public static void bindResource(Object resource){
        threadLocal.set(resource);
    }

    public static Object unbindResource(){
        Object res = threadLocal.get();
        threadLocal.remove();
        return res;
    }
}
