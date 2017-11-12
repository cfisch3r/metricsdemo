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
        if (hasRequiredAnnotation(method)) {
            startTimer(method.getName());
            Object returnValue = invocation.proceed();
            stopTimer(method.getName());
            return returnValue;
        } else {
//            return invocation.proceed();
            return null;
        }
    }

    private void stopTimer(String name) {
        executionTimer.stop(name,Thread.currentThread().getId(),System.currentTimeMillis());
    }

    private void startTimer(String name) {
        executionTimer.start(name,Thread.currentThread().getId(),System.currentTimeMillis());
    }

    private boolean hasRequiredAnnotation(Method method) {
        return method.getAnnotation(annotationClass) != null;
    }
}
