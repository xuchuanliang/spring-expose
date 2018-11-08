package com.snail.springbootsource.capter05.b2;

import com.snail.springbootsource.capter05.b1.MethodExecutionStatus;
import org.springframework.context.ApplicationEvent;

public class MethodExecutionEvent extends ApplicationEvent {
    private static final long serialVersionUID = -71960369269303337L;
    private String methodName;
    private MethodExecutionStatus methodExecutionStatus;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName, MethodExecutionStatus methodExecutionStatus) {
        super(source);
        this.methodName = methodName;
        this.methodExecutionStatus = methodExecutionStatus;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodExecutionStatus getMethodExecutionStatus() {
        return methodExecutionStatus;
    }

    public void setMethodExecutionStatus(MethodExecutionStatus methodExecutionStatus) {
        this.methodExecutionStatus = methodExecutionStatus;
    }
}
