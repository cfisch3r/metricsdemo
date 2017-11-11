package de.agiledojo.metricsdemo.app;

import de.agiledojo.metricsdemo.Timed;

public class TimedBean {


    @Timed("demo.timer")
    public void TimedRun() {

    }

    public void unTimedRun() {

    }
}
