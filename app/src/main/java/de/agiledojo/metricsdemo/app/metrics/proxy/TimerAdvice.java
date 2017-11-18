package de.agiledojo.metricsdemo.app.metrics.proxy;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Instant;

/**
 * Method Interceptor which reports execution time for a method call.
 */
class TimerAdvice implements MethodInterceptor {

    private Clock clock;
    private ExecutionTimeReporter reporter;
    private Class<? extends Annotation> annotationClass;

    <A extends Annotation> TimerAdvice(Clock clock, ExecutionTimeReporter reporter, Class<A> annotationClass) {
        this.clock = clock;
        this.reporter = reporter;
        this.annotationClass = annotationClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Instant startTime = clock.instant();
        Object returnValue = invocation.proceed();
        Instant endTime = clock.instant();
        Method method = invocation.getMethod();
        if (hasRequiredAnnotation(method))
            reportMeasurement(method, startTime, endTime);
        return returnValue;
    }

    private void reportMeasurement(Method method, Instant startTime, Instant endTime) {
        reporter.report(new ExecutionTimeMeasurement(method.getName(), startTime, endTime));
    }

    private boolean hasRequiredAnnotation(Method method) {
        return method.getAnnotation(annotationClass) != null;
    }
}

