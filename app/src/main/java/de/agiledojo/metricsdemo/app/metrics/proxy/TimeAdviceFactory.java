package de.agiledojo.metricsdemo.app.metrics.proxy;

import de.agiledojo.metricsdemo.app.metrics.ExecutionTimeReporter;

import java.lang.annotation.Annotation;
import java.time.Clock;

/**
 * Factory for annotation specific time advices.
 */
public class TimeAdviceFactory {
    private ExecutionTimeReporter reporter;
    private Clock clock;

    public TimeAdviceFactory(ExecutionTimeReporter reporter, Clock clock) {
        this.reporter = reporter;
        this.clock = clock;
    }

    <A extends Annotation> TimerAdvice createTimerAdvice(Class<A> annotationClass) {
        return new TimerAdvice(clock, reporter, annotationClass);
    }
}