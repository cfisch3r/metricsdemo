package de.agiledojo.metricsdemo.app.metrics.proxy;

import de.agiledojo.metricsdemo.MetricsService;
import de.agiledojo.metricsdemo.app.metrics.ClockFake;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringMetricsServiceTest.Configuration.class)
public class SpringMetricsServiceTest {

    @TestConfiguration
    public static class Configuration {
        @Bean
        public MetricsService metricsService(@Autowired ExecutionTimeReporter reporter) {
            return new SpringMetricsService(new TimeAdviceFactory(reporter, new ClockFake()));
        }
    }

    private Subject subjectWithTimer;
    private ArgumentCaptor<ExecutionTimeMeasurement> captor = ArgumentCaptor.forClass(ExecutionTimeMeasurement.class);

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Timed {
    }


    static class Subject {

        static String RETURN_VALUE = "result";

        @Timed
        String annotatedRun() {
            return RETURN_VALUE;
        }

        void run() {

        }

        @Timed
        String failedRun() {
            throw new RuntimeException();
        }
    }

    @MockBean
    private ExecutionTimeReporter reporter;

    @Autowired
    private MetricsService metricsService;

    @Before
    public void setUp() throws Exception {
        subjectWithTimer = metricsService.addMetrics(new Subject(), Timed.class);
    }

    @Test
    public void when_annotated_method_is_called_execution_time_is_reported() {
        subjectWithTimer.annotatedRun();
        verify(reporter).report(captor.capture());
        assertEquals(ClockFake.TIME_INCREASE_INCREMENT,captor.getValue().getExecutionTime());
    }

    @Test
    public void when_annotated_method_is_called_metric_name_is_reported() {
        subjectWithTimer.annotatedRun();
        verify(reporter).report(captor.capture());
        assertEquals("annotatedRun",captor.getValue().getMetricName());
    }

    @Test
    public void when_method_without_annotation_is_called_nothing_is_reported() {
        subjectWithTimer.run();
        verify(reporter, never()).report(Matchers.any(ExecutionTimeMeasurement.class));
    }

    @Test
    public void method_call_is_executed() {
        String returnValue = subjectWithTimer.annotatedRun();
        assertEquals(Subject.RETURN_VALUE,returnValue);
    }

    @Test
    public void when_method_throws_exception_nothing_is_reported() {
        try {
            subjectWithTimer.failedRun();
        } catch (Exception e) {
        }
        verify(reporter, never()).report(Matchers.any(ExecutionTimeMeasurement.class));
    }
}
