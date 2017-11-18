package de.agiledojo.metricsdemo.app;

/**
 * Reporter for Method Execution Times.
 */
public interface ExecutionTimeReporter {

    void report(ExecutionTimeMeasurement executionTime);
}
