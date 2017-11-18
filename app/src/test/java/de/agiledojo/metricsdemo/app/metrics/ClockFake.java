package de.agiledojo.metricsdemo.app.metrics;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Fake for System Clock
 */
public class ClockFake extends Clock {

    public static final int TIME_INCREASE_INCREMENT = 500;

    private int milli = 0;

    @Override
    public ZoneId getZone() {
        return null;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        Instant instant = Instant.ofEpochMilli(milli);
        milli += TIME_INCREASE_INCREMENT;
        return instant;

    }
}
