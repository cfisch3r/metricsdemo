package de.agiledojo.metricsdemo.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
@TestPropertySource(properties = {"management.security.enabled=false"})
@AutoConfigureMockMvc
public class MetricAdapterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetricAdapter metricAdapter;

    @Test
    public void when_submitted_metric_is_listed_in_web_interface() throws Exception {
        metricAdapter.submit("demo.timedMethodAspect",300);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/metrics"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$..['gauge.demo.timedMethodAspect']").exists());
    }
}
