package de.agiledojo.metricsdemo.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class, TimerIntegrationTest.TimedBeanConfiguration.class})
public class TimerIntegrationTest {

    @TestConfiguration
    public static class TimedBeanConfiguration {

        @Bean
        public TimedBean timedBean() {
            return new TimedBean();
        }
    }

    @MockBean
    private ExecutionTimer timer;

    @Autowired
    TimedBean timedBean;

    @Autowired
    TimedMethodAspect timedMethodAspect;

    @Test
    public void without_Timed_Annotation_no_metric_is_reported() {
        timedBean.unTimedRun();
        verify(timer, never()).start(anyString(), anyLong(), anyLong());
        verify(timer, never()).stop(anyString(), anyLong(), anyLong());
    }

    @Test
    public void with_Timed_Annotation_metric_is_reported() {
        timedBean.TimedRun();
        verify(timer).start(eq("demo.timer"), eq(Thread.currentThread().getId()), anyLong());
        verify(timer).stop(eq("demo.timer") ,eq(Thread.currentThread().getId()), anyLong());
    }

}
