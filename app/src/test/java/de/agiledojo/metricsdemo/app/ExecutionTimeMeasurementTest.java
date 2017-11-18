package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import org.junit.Assert;
import org.junit.Test;

public class ExecutionTimeMeasurementTest {

    @Test
    public void executionTime_is_calculated() {
        ExecutionTimeMeasurement measurement = new ExecutionTimeMeasurement("", 0L, 230L);
        Assert.assertEquals(230L,measurement.getExecutionTime());
    }
}
