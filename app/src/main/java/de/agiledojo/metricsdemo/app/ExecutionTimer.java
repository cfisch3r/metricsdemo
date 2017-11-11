package de.agiledojo.metricsdemo.app;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ExecutionTimer {

    private MetricReporter reporter;

    public ExecutionTimer(MetricReporter reporter) {
        this.reporter = reporter;
    }

    @Before("@annotation(de.agiledojo.metricsdemo.Timed)")
    public void beforeCallingTimedMethod() {
        reporter.start("demo.timer",Thread.currentThread().getId(),System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(de.agiledojo.metricsdemo.Timed)")
    public void afterCallingTimedMethod() {
        reporter.stop("demo.timer",Thread.currentThread().getId(),System.currentTimeMillis());
    }
}
