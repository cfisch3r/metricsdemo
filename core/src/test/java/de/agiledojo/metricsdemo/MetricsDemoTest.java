package de.agiledojo.metricsdemo;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class MetricsDemoTest {

    @Test
    public void main_method_exists() throws NoSuchMethodException {

        Method mainMethod = MetricsDemo.class.getDeclaredMethod("main");
        Assert.assertNotNull(mainMethod);
    }
}
