package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class MetricsServiceConfiguration {

    @Bean
    public MetricsService metricsService(@Autowired ExecutionTimer executionTimer) {
        return new SpringMetricsService(executionTimer);
    }

}
