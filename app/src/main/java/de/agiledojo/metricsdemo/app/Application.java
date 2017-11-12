package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Application {

    @Bean
    public MetricAdapter metric(@Autowired GaugeService gaugeService) {
        MetricAdapter metricAdapter = new MetricAdapter(gaugeService);
        return metricAdapter;
    };

    @Bean
    public TimedMethodAspect executionTimer(@Autowired ExecutionTimer reporter) {
        return new TimedMethodAspect(reporter);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}