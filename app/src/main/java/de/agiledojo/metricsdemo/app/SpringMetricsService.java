package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import org.springframework.aop.framework.ProxyFactory;

public class SpringMetricsService implements MetricsService {

    private ExecutionTimer executionTimer;

    public SpringMetricsService(ExecutionTimer executionTimer) {
        this.executionTimer = executionTimer;
    }

    @Override
    public <T> T addMetrics(T subject) {
        TimerAdvice timerAdvice = new TimerAdvice(executionTimer);
        ProxyFactory factory = new ProxyFactory(subject);
        factory.addAdvice(timerAdvice);
        return (T) factory.getProxy();
    }
}
