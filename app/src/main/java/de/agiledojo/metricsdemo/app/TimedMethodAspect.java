package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.Timed;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimedMethodAspect {

    private ExecutionTimer timer;

    public TimedMethodAspect(ExecutionTimer timer) {
        this.timer = timer;
    }

    @Before(value = "@annotation(timed)")
    public void beforeCallingTimedMethod(Timed timed) {
        timer.start(timed.value(),Thread.currentThread().getId(),System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(timed)")
    public void afterCallingTimedMethod(Timed timed) {
        timer.stop(timed.value(),Thread.currentThread().getId(),System.currentTimeMillis());
    }
}
