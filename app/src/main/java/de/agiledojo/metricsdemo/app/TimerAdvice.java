package de.agiledojo.metricsdemo.app;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TimerAdvice implements MethodInterceptor {

    private ExecutionTimeReporter reporter;
    private Class<? extends Annotation> annotationClass;

    public <A extends Annotation> TimerAdvice(ExecutionTimeReporter reporter, Class<A> annotationClass) {
        this.reporter = reporter;
        this.annotationClass = annotationClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long endTime = System.currentTimeMillis();
        Method method = invocation.getMethod();
        if (method.getAnnotation(annotationClass) != null)
            reporter.report(new ExecutionTimeMeasurement(method.getName(), startTime, endTime));
        return returnValue;
    }
}

