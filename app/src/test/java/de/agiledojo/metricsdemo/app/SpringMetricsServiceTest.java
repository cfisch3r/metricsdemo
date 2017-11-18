package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeMeasurement;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MetricsServiceConfiguration.class)
public class SpringMetricsServiceTest {

    private Subject subjectWithTimer;
    private ArgumentCaptor<ExecutionTimeMeasurement> captor = ArgumentCaptor.forClass(ExecutionTimeMeasurement.class);

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Timed {
    }


    static class Subject {

        static final int METHOD_EXECUTION_TIME = 1;

        static String RETURN_VALUE = "result";

        @Timed
        String annotatedRun() {
            sleep();
            return RETURN_VALUE;
        }

        @SuppressWarnings("squid:S2925")
        private void sleep() {
            try {
                Thread.sleep(METHOD_EXECUTION_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void run() {
            sleep();
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
        Assert.assertTrue(captor.getValue().getExecutionTime() > 0);
    }

    @Test
    public void when_annotated_method_is_called_metric_name_is_reported() {
        subjectWithTimer.annotatedRun();
        verify(reporter).report(captor.capture());
        Assert.assertEquals("annotatedRun",captor.getValue().getMetricName());
    }

    @Test
    public void when_method_without_annotation_is_called_nothing_is_reported() {
        subjectWithTimer.run();
        Mockito.verify(reporter,never()).report(any(ExecutionTimeMeasurement.class));
    }

    @Test
    public void method_call_is_executed() {
        String returnValue = subjectWithTimer.annotatedRun();
        Assert.assertEquals(Subject.RETURN_VALUE,returnValue);
    }

    @Test
    public void when_method_throws_exception_nothing_is_reported() {
        try {
            subjectWithTimer.failedRun();
        } catch (Exception e) {
        }
        Mockito.verify(reporter,never()).report(any(ExecutionTimeMeasurement.class));
    }
}
