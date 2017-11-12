package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import de.agiledojo.metricsdemo.Timed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.eq;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MetricsServiceConfiguration.class)
public class SpringMetricsServiceTest {

    class Subject {

        @Timed("internal.timer")
        void annotatedRun() {

        }
    }

    @MockBean
    private ExecutionTimer timer;

    @Autowired
    private MetricsService metricsService;

    @Test
    public void when_metrics_are_added_to_object_method_call_triggers_timer() {
        Subject objectWithTimer = metricsService.addMetrics(new Subject(),Timed.class);
        objectWithTimer.annotatedRun();
        Mockito.verify(timer).start(eq("annotatedRun"), eq(Thread.currentThread().getId()), Matchers.anyLong());
        Mockito.verify(timer).stop(eq("annotatedRun"), eq(Thread.currentThread().getId()), Matchers.anyLong());
    }
}
