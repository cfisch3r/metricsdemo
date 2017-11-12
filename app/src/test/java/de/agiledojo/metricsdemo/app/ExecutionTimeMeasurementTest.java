package de.agiledojo.metricsdemo.app;

import org.junit.Assert;
import org.junit.Test;

public class ExecutionTimeMeasurementTest {

    @Test
    public void executionTime_is_calculated() {
        ExecutionTimeMeasurement measurement = new ExecutionTimeMeasurement("", 0l, 230l);
        Assert.assertEquals(230l,measurement.getExecutionTime());
    }
}
