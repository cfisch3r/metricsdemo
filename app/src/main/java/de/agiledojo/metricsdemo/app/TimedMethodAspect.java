package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.Clock;

@Aspect
public class TimedMethodAspect {

    private ExecutionTimeReporter executionTimeReporter;
    private Clock clock;

    public TimedMethodAspect(ExecutionTimeReporter executionTimeReporter, Clock clock) {
        this.executionTimeReporter = executionTimeReporter;
        this.clock = clock;
    }

    @Around("@annotation(de.agiledojo.metricsdemo.Timed)")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = clock.instant().toEpochMilli();
        Object result = proceedingJoinPoint.proceed();
        long endTime = clock.instant().toEpochMilli();
        executionTimeReporter.report(new ExecutionTimeMeasurement(proceedingJoinPoint.getSignature().getName(),startTime,endTime));
        return result;
    }
 }
