package de.agiledojo.metricsdemo.app.metrics.proxy;

import de.agiledojo.metricsdemo.MetricsService;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.annotation.Annotation;

/**
 * adds proxies to objects for execution time measurement.
 */
public class SpringMetricsService implements MetricsService {

    private final TimeAdviceFactory timeAdviceFactory;

    public SpringMetricsService(TimeAdviceFactory timeAdviceFactory) {
        this.timeAdviceFactory = timeAdviceFactory;
    }

    @Override
    public <T, A extends Annotation> T addMetrics(T subject, Class<A> annotationClass) {
        ProxyFactory factory = new ProxyFactory(subject);
        factory.addAdvice(timeAdviceFactory.createTimerAdvice(annotationClass));
        return (T) factory.getProxy();
    }

}
