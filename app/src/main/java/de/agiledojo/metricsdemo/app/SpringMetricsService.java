package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.MetricsService;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.annotation.Annotation;

public class SpringMetricsService implements MetricsService {

    private ExecutionTimer executionTimer;

    public SpringMetricsService(ExecutionTimer executionTimer) {
        this.executionTimer = executionTimer;
    }

    @Override
    public <T, A extends Annotation> T addMetrics(T subject, Class<A> annotationClass) {
        TimerAdvice timerAdvice = new TimerAdvice(executionTimer, annotationClass);
        ProxyFactory factory = new ProxyFactory(subject);
        factory.addAdvice(timerAdvice);
        return (T) factory.getProxy();
    }
}
