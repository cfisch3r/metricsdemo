package de.agiledojo.metricsdemo.app.metrics;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class ExecutionTimeMeasurementTest {

    @Test
    public void executionTime_is_calculated_with_instants() {
        ExecutionTimeMeasurement measurement = new ExecutionTimeMeasurement("", Instant.EPOCH, Instant.ofEpochMilli(400L));
        Assert.assertEquals(400L,measurement.getExecutionTime());
    }

}
