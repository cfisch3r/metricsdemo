package de.agiledojo.metricsdemo;

import java.lang.annotation.Annotation;

/**
 * provides metrics proxy decoration for object instances.
 */
public interface MetricsService {

    /**
     * wraps subject into proxy for metrics measurement.
     *
     * @param subject target instance for measurement
     * @param annotationClass metrics are only measured for target instance methods with this annotation
     * @param <T> target instance type
     * @param <A> annotation type
     * @return target instance wrapped in proxy
     */
    <T,A extends Annotation> T addMetrics(T subject, Class<A> annotationClass);
}
