package de.agiledojo.metricsdemo.app.metrics;

import java.time.Instant;

public class ExecutionTimeMeasurement {

    private String metricName;
    private Instant start;
    private Instant end;

    public ExecutionTimeMeasurement(String metricName, Instant start, Instant end) {

        this.metricName = metricName;
        this.start = start;
        this.end = end;
    }

    public long getExecutionTime() {
        return end.toEpochMilli() - start.toEpochMilli();
    }

    public String getMetricName() {
        return metricName;
    }
}
