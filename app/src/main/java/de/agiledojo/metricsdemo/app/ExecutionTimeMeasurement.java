package de.agiledojo.metricsdemo.app;

public class ExecutionTimeMeasurement {

    private String metricName;

    private long startTime;

    private long endTime;

    public ExecutionTimeMeasurement(String metricName, long startTime, long endTime) {
        this.metricName = metricName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getExecutionTime() {
        return endTime - startTime;
    }

    public String getMetricName() {
        return metricName;
    }
}
