package de.agiledojo.metricsdemo.app;

public interface MetricReporter {

    void start(String metricName, long threadId, long milliseconds);

    void stop(String metricName, long threadId, long milliseconds);
}
