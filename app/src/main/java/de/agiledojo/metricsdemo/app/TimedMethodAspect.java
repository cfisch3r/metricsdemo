package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimedMethodAspect {

    private ExecutionTimeReporter executionTimeReporter;

    public TimedMethodAspect(ExecutionTimeReporter executionTimeReporter) {

        this.executionTimeReporter = executionTimeReporter;
    }

    @Around("@annotation(de.agiledojo.metricsdemo.Timed)")
    public void measureExecutionTime() {
        executionTimeReporter.report(new ExecutionTimeMeasurement("",0,0));
    }
 }
