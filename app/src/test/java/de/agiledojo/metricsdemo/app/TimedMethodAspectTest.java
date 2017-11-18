package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.Timed;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TimedMethodAspectTest.Configuration.class)
public class TimedMethodAspectTest {


    @TestConfiguration
    @EnableAutoConfiguration
    public static class Configuration {

        @Bean
        public TimedBean timedBean() {
            return new TimedBean();
        }

        @Bean
        public TimedMethodAspect timedMethodAspect(@Autowired ExecutionTimeReporter executionTimeReporter) {
            return new TimedMethodAspect(executionTimeReporter, new ClockFake());
        }
    }

    /**
     * Test Class with Annotations
     */
    private static class TimedBean {


        public static final String RETURN_VALUE = "result";

        @Timed("demo.timer")
        public String timedRun() {
            return RETURN_VALUE;
        }

        public void unTimedRun() {

        }
    }

    /**
     * Fake for System Clock
     */
    private static class ClockFake extends Clock {

        private int milli = 0;

        @Override
        public ZoneId getZone() {
            return null;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return null;
        }

        @Override
        public Instant instant() {
            Instant instant = Instant.ofEpochMilli(milli);
            milli += 500;
            return instant;

        }
    }

    @MockBean
    private ExecutionTimeReporter executionTimeReporter;

    @Autowired
    TimedBean timedBean;

    private ArgumentCaptor<ExecutionTimeMeasurement> captor = ArgumentCaptor.forClass(ExecutionTimeMeasurement.class);

    @Test
    public void without_Timed_Annotation_no_metric_is_reported() {
        timedBean.unTimedRun();
        verify(executionTimeReporter, never()).report(any(ExecutionTimeMeasurement.class));
    }

    @Test
    public void with_Timed_Annotation_metric_is_reported() {
        timedBean.timedRun();
        verify(executionTimeReporter).report(any(ExecutionTimeMeasurement.class));
    }

    @Test
    public void with_timed_Annotation_method_is_still_executed() {
        Assert.assertEquals(TimedBean.RETURN_VALUE,timedBean.timedRun());
    }

    @Test
    public void with_timed_Annotation_metric_name_is_reported() {
        timedBean.timedRun();
        verify(executionTimeReporter).report(captor.capture());
        Assert.assertEquals("timedRun",captor.getValue().getMetricName());
    }

    @Test
    public void with_timed_Annotation_method_execution_time_is_measured() {
        timedBean.timedRun();
        verify(executionTimeReporter).report(captor.capture());
        Assert.assertEquals(500, captor.getValue().getExecutionTime());
    }
}
