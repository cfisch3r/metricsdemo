package de.agiledojo.metricsdemo;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.mockito.Matchers.eq;

public class InternalMetricsDemoTest {

    @Test
    public void internal_object_is_decorated_with_metrics() {
        MetricsService metricsService = Mockito.mock(MetricsService.class);
        new InternalMetricsDemo(metricsService);
        Mockito.verify(metricsService).addMetrics(Matchers.any(InternalMetricsDemo.InternalObject.class),eq(Timed.class));
    }
}
