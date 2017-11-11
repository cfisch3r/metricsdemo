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

    @Test
    public void main_method_is_marked_for_timed_metrics() throws NoSuchMethodException {

        Method mainMethod = MetricsDemo.class.getDeclaredMethod("main");
        Timed annotation = mainMethod.getAnnotation(Timed.class);
        Assert.assertNotNull(annotation);
    }

}
