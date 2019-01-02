package com.snail.springbootsource.capter08.b1;

/**
 * ISubject代理类
 */
public class SubjectProxy implements ISubject {

    private ISubject iSubject;

    public SubjectProxy() {

    }

    public SubjectProxy(ISubject iSubject) {
        this.iSubject = iSubject;
    }

    @Override
    public void request() {
        //逻辑
        iSubject.request();
        //逻辑
    }
}
