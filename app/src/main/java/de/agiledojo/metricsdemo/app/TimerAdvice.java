package de.agiledojo.metricsdemo.app;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TimerAdvice implements MethodInterceptor {
    private ExecutionTimer executionTimer;
    private Class<? extends Annotation> annotationClass;

    public <A extends Annotation> TimerAdvice(ExecutionTimer executionTimer, Class<A> annotationClass) {
        this.executionTimer = executionTimer;
        this.annotationClass = annotationClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
//        Annotation annotation = method.getAnnotation(annotationClass);
        executionTimer.start(method.getName(),Thread.currentThread().getId(),System.currentTimeMillis());
        Object returnValue = invocation.proceed();
        executionTimer.stop(method.getName(),Thread.currentThread().getId(),System.currentTimeMillis());
        return returnValue;
    }
}
