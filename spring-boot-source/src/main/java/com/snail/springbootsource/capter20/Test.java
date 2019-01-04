package com.snail.springbootsource.capter20;

import java.util.concurrent.CompletableFuture;

public class Test {
    private static Integer i = 1;
    private static final ThreadLocal<Integer> t = new ThreadLocal();
    public static final User USER = new User(){
        @Override
        public void setAge(String age) {
            super.setAge(age);
        }
    };
    public static void main(String[] args){
        test1();
        test2();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void test1(){
        CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.set(i);
            t.set(t.get()+1);
            System.out.println("1111:"+t.get());
        });
    }
    public static void test2(){
        CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.set(i);
            t.set(t.get()+1);
            System.out.println("2222"+t.get());
        });
    }
}
