package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import de.agiledojo.metricsdemo.Timed;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MetricsServiceConfiguration.class)
public class SpringMetricsServiceTest {

    class InternalObject {

        @Timed("internal.timer")
        void run() {

        }
    }

    @MockBean
    private ExecutionTimer timer;

    @Autowired
    private MetricsService metricsService;

    @Test
    public void service_is_available() {
        Assert.assertNotNull(metricsService);
    }

    @Test
    public void test() {
        InternalObject objectWithTimer = metricsService.addMetrics(new InternalObject());
        objectWithTimer.run();
        Mockito.verify(timer).start(eq("internal.timer"), eq(Thread.currentThread().getId()), Matchers.anyLong());
        Mockito.verify(timer).stop(eq("internal.timer"), eq(Thread.currentThread().getId()), Matchers.anyLong());
    }
}
