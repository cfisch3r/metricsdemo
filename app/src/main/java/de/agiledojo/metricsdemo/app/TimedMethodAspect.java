package de.agiledojo.metricsdemo.app;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimedMethodAspect {

    private ExecutionTimer timer;

    public TimedMethodAspect(ExecutionTimer timer) {
        this.timer = timer;
    }

    @Before("@annotation(de.agiledojo.metricsdemo.Timed)")
    public void beforeCallingTimedMethod() {
        timer.start("demo.timer",Thread.currentThread().getId(),System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(de.agiledojo.metricsdemo.Timed)")
    public void afterCallingTimedMethod() {
        timer.stop("demo.timer",Thread.currentThread().getId(),System.currentTimeMillis());
    }
}
