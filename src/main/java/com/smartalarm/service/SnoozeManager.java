package com.smartalarm.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SnoozeManager {
    private final Map<String, LocalDateTime> snoozeUntil = new HashMap<>();
    private int durationMinutes = 10;

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Snooze duration must be positive");
        }
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public LocalDateTime snooze(String alarmId, LocalDateTime now) {
        LocalDateTime next = now.plusMinutes(durationMinutes);
        snoozeUntil.put(alarmId, next);
        return next;
    }

    public boolean isSnoozed(String alarmId, LocalDateTime now) {
        LocalDateTime until = snoozeUntil.get(alarmId);
        return until != null && now.isBefore(until);
    }

    public Optional<LocalDateTime> getSnoozeUntil(String alarmId) {
        return Optional.ofNullable(snoozeUntil.get(alarmId));
    }

    public void clearSnooze(String alarmId) {
        snoozeUntil.remove(alarmId);
    }

    public Map<String, LocalDateTime> getActiveSnoozes() {
        return Collections.unmodifiableMap(snoozeUntil);
    }
}
