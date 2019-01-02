package com.snail.springbootsource.capter14;

/**
 * 模板方法：主要是解决共有逻辑问题
 */
public abstract class Vehicle {
    /**
     * 模板方法
     */
    public final void driver() {
        startTheEngine();
        putIntoGear();
        looseHandBrake();
        stepOnTheGasAndGo();
    }

    protected abstract void putIntoGear();

    private void stepOnTheGasAndGo() {
        //逻辑
    }

    private void looseHandBrake() {
        //逻辑
    }

    private void startTheEngine() {
        //逻辑
    }
}
