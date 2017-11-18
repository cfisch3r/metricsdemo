package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TimerIntegrationTest.TimedBeanConfiguration.class)
public class TimerIntegrationTest {

    @TestConfiguration
    @EnableAutoConfiguration
    public static class TimedBeanConfiguration {

        @Bean
        public TimedBean timedBean() {
            return new TimedBean();
        }

        @Bean
        public TimedMethodAspect timedMethodAspect(@Autowired ExecutionTimeReporter executionTimeReporter) {
            return new TimedMethodAspect(executionTimeReporter);
        }
    }

    @MockBean
    private ExecutionTimeReporter executionTimeReporter;

    @Autowired
    TimedBean timedBean;

    @Test
    public void without_Timed_Annotation_no_metric_is_reported() {
        timedBean.unTimedRun();
        verify(executionTimeReporter, never()).report(any(ExecutionTimeMeasurement.class));
    }

    @Test
    public void with_Timed_Annotation_metric_is_reported() {
        timedBean.TimedRun();
        verify(executionTimeReporter).report(any(ExecutionTimeMeasurement.class));
    }

}
