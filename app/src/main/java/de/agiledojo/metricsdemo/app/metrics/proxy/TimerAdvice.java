package de.agiledojo.metricsdemo.app.metrics.proxy;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Instant;

/**
 * Method Interceptor which reports execution time for a method call.
 */
class TimerAdvice implements MethodInterceptor {

    private ExecutionTimeReporter reporter;
    private Class<? extends Annotation> annotationClass;

    <A extends Annotation> TimerAdvice(ExecutionTimeReporter reporter, Class<A> annotationClass) {
        this.reporter = reporter;
        this.annotationClass = annotationClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long endTime = System.currentTimeMillis();
        Method method = invocation.getMethod();
        if (hasRequiredAnnotation(method))
            reportMeasurement(method, startTime, endTime);
        return returnValue;
    }

    private void reportMeasurement(Method method, long startTime, long endTime) {
        reporter.report(new ExecutionTimeMeasurement(method.getName(), Instant.ofEpochMilli(startTime), Instant.ofEpochMilli(endTime)));
    }

    private boolean hasRequiredAnnotation(Method method) {
        return method.getAnnotation(annotationClass) != null;
    }
}

