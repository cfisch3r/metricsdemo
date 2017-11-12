package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.Timed;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class TimerAdvice implements MethodInterceptor {
    private ExecutionTimer executionTimer;

    public TimerAdvice(ExecutionTimer executionTimer) {
        this.executionTimer = executionTimer;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Timed timedAnnotation = method.getAnnotation(Timed.class);
        executionTimer.start(timedAnnotation.value(),Thread.currentThread().getId(),System.currentTimeMillis());
        Object returnValue = invocation.proceed();
        executionTimer.stop(timedAnnotation.value(),Thread.currentThread().getId(),System.currentTimeMillis());
        return returnValue;
    }
}
