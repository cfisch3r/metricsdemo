package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;
import de.agiledojo.metricsdemo.app.metrics.SpringMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsServiceConfiguration {

    @Bean
    public MetricsService metricsService(@Autowired ExecutionTimeReporter reporter) {
        return new SpringMetricsService(reporter);
    }

}
