package de.agiledojo.metricsdemo;

public class InternalMetricsDemo {

    static class InternalObject {

    }

    public InternalMetricsDemo(MetricsService metricsService) {
        InternalObject objectWithMetrics = metricsService.addMetrics(new InternalObject(),Timed.class);
    }
}
