package de.agiledojo.metricsdemo.app.metrics;

/**
 * Reporter for Method Execution Times.
 */
public interface ExecutionTimeReporter {

    void report(ExecutionTimeMeasurement executionTime);
}
