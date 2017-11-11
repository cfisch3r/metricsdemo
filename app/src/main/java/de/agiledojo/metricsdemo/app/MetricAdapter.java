package de.agiledojo.metricsdemo.app;

import org.springframework.boot.actuate.metrics.GaugeService;

public class MetricAdapter {
    private GaugeService gaugeService;

    public MetricAdapter(GaugeService gaugeService) {
        this.gaugeService = gaugeService;
    }

    public void submit(String metricName,double value) {
        gaugeService.submit(metricName,value);
    }
}
