package de.agiledojo.metricsdemo;

public interface MetricsService {

    <T> T addMetrics(T subject);
}
