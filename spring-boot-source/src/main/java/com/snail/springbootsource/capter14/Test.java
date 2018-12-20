package com.snail.springbootsource.capter14;

public class Test {
    public static void main(String[] args){

    }

    /**
     * 测试模板方法
     */
    public static void test1(){
        Vehicle vehicle = new RealVehicle();
        vehicle.driver();
    }
}
