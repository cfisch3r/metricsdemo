package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MetricsServiceConfiguration.class)
public class SpringMetricsServiceTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Timed {
    }


    class Subject {

        @Timed
        void annotatedRun() {

        }

        void run() {

        }
    }

    @MockBean
    private ExecutionTimer timer;

    @Autowired
    private MetricsService metricsService;

    @Test
    public void when_metrics_are_added_to_object_annotated_method_call_triggers_timer() {
        Subject objectWithTimer = metricsService.addMetrics(new Subject(), Timed.class);
        objectWithTimer.annotatedRun();
        Mockito.verify(timer).start(eq("annotatedRun"), eq(Thread.currentThread().getId()), Matchers.anyLong());
        Mockito.verify(timer).stop(eq("annotatedRun"), eq(Thread.currentThread().getId()), Matchers.anyLong());
    }

    @Test
    public void when_metrics_are_added_to_object_method_call_without_annotation_triggers_timer() {
        Subject objectWithTimer = metricsService.addMetrics(new Subject(), Timed.class);
        objectWithTimer.run();
        Mockito.verify(timer,never()).start(anyString(), anyLong(), anyLong());
        Mockito.verify(timer,never()).stop(anyString(), anyLong(), anyLong());
    }
}
