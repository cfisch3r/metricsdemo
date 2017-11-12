package de.agiledojo.metricsdemo;

import java.lang.annotation.Annotation;

public interface MetricsService {

    <T,A extends Annotation> T addMetrics(T subject, Class<A> annotationClass);
}
