package com.smartalarm.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class SleepStatistics {
    private final AtomicInteger totalSnoozes = new AtomicInteger();
    private final AtomicInteger alarmsStopped = new AtomicInteger();
    private final AtomicInteger onTimeStops = new AtomicInteger();
    private final AtomicInteger lateStops = new AtomicInteger();
    private final AtomicInteger missedAlarms = new AtomicInteger();
    private Duration totalSleepTime = Duration.ZERO;

    public void recordSnooze() {
        totalSnoozes.incrementAndGet();
    }

    public void recordStop(LocalDateTime scheduledTime, LocalDateTime actualStopTime) {
        alarmsStopped.incrementAndGet();
        if (actualStopTime.isBefore(scheduledTime.plusMinutes(1))) {
            onTimeStops.incrementAndGet();
        } else {
            lateStops.incrementAndGet();
        }
        if (!actualStopTime.isBefore(scheduledTime)) {
            totalSleepTime = totalSleepTime.plus(Duration.between(scheduledTime, actualStopTime));
        }
    }

    public void recordMissedAlarm() {
        missedAlarms.incrementAndGet();
    }

    public int getTotalSnoozes() {
        return totalSnoozes.get();
    }

    public int getAlarmsStopped() {
        return alarmsStopped.get();
    }

    public int getOnTimeStops() {
        return onTimeStops.get();
    }

    public int getLateStops() {
        return lateStops.get();
    }

    public int getMissedAlarms() {
        return missedAlarms.get();
    }

    public Duration getTotalSleepTime() {
        return totalSleepTime;
    }

    @Override
    public String toString() {
        return String.format("SleepStatistics{alarmsStopped=%d, onTime=%d, late=%d, snoozes=%d, missed=%d, totalSleep=%dh %dm}",
                getAlarmsStopped(), getOnTimeStops(), getLateStops(), getTotalSnoozes(), getMissedAlarms(),
                getTotalSleepTime().toHours(), getTotalSleepTime().toMinutesPart());
    }
}
