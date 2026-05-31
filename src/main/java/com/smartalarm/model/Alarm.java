package com.smartalarm.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Alarm {
    private final String id;
    private String label;
    private int hour;
    private int minute;
    private SoundProfile soundProfile;
    private boolean enabled;
    private final Recurrence recurrence;
    private AlarmCategory category;
    private LocalDateTime snoozeUntil;
    private boolean dismissed;

    public Alarm(String label, int hour, int minute, SoundProfile soundProfile, Recurrence recurrence, AlarmCategory category) {
        validateTime(hour, minute);
        this.id = UUID.randomUUID().toString();
        this.label = label == null || label.isBlank() ? "Alarm" : label;
        this.hour = hour;
        this.minute = minute;
        this.soundProfile = soundProfile;
        this.enabled = true;
        this.recurrence = recurrence == null ? Recurrence.none() : recurrence;
        this.category = category == null ? AlarmCategory.CUSTOM : category;
        this.snoozeUntil = null;
        this.dismissed = false;
    }

    private void validateTime(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Hour must be 0-23 and minute 0-59");
        }
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public SoundProfile getSoundProfile() {
        return soundProfile;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public AlarmCategory getCategory() {
        return category;
    }

    public Optional<LocalDateTime> getSnoozeUntil() {
        return Optional.ofNullable(snoozeUntil);
    }

    public void setLabel(String label) {
        if (label != null && !label.isBlank()) {
            this.label = label;
        }
    }

    public void setTime(int hour, int minute) {
        validateTime(hour, minute);
        this.hour = hour;
        this.minute = minute;
    }

    public void setSoundProfile(SoundProfile soundProfile) {
        this.soundProfile = soundProfile;
    }

    public void setCategory(AlarmCategory category) {
        this.category = category == null ? AlarmCategory.CUSTOM : category;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isRepeating() {
        return recurrence.isRepeating();
    }

    public void snoozeUntil(LocalDateTime dateTime) {
        this.snoozeUntil = dateTime;
    }

    public void clearSnooze() {
        this.snoozeUntil = null;
    }

    public boolean isSnoozed(LocalDateTime now) {
        return snoozeUntil != null && now.isBefore(snoozeUntil);
    }

    public boolean isDueAt(LocalDateTime now) {
        if (!enabled || dismissed) {
            return false;
        }
        LocalTime currentTime = now.toLocalTime().withSecond(0).withNano(0);
        if (!currentTime.equals(LocalTime.of(hour, minute))) {
            return false;
        }
        if (isSnoozed(now)) {
            return false;
        }
        if (!recurrence.isRepeating()) {
            LocalDateTime scheduled = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0);
            return !scheduled.isAfter(now.plusSeconds(1));
        }
        return recurrence.repeatsOn(now.getDayOfWeek());
    }

    public LocalDateTime nextActivation(LocalDateTime from) {
        if (!enabled) {
            return null;
        }
        LocalDateTime start = from.withSecond(0).withNano(0);
        if (!recurrence.isRepeating()) {
            LocalDateTime candidate = start.withHour(hour).withMinute(minute);
            if (!candidate.isAfter(start)) {
                candidate = candidate.plusDays(1);
            }
            return candidate;
        }
        for (int daysAhead = 0; daysAhead < 14; daysAhead++) {
            LocalDateTime candidate = start.plusDays(daysAhead).withHour(hour).withMinute(minute);
            if (recurrence.repeatsOn(candidate.getDayOfWeek()) && candidate.isAfter(start.minusSeconds(1))) {
                return candidate;
            }
        }
        return null;
    }

    public boolean conflictsWith(Alarm other, int thresholdMinutes) {
        if (!this.enabled || !other.enabled) {
            return false;
        }
        if (this == other) {
            return false;
        }
        int thisMinutes = this.hour * 60 + this.minute;
        int otherMinutes = other.hour * 60 + other.minute;
        return Math.abs(thisMinutes - otherMinutes) <= thresholdMinutes;
    }

    public void dismiss() {
        this.dismissed = true;
    }

    public void resetDismiss() {
        this.dismissed = false;
    }

    @Override
    public String toString() {
        return String.format("Alarm[id=%s, label=%s, time=%02d:%02d, enabled=%s, recurrence=%s, category=%s, sound=%s]",
                id, label, hour, minute, enabled, recurrence, category, soundProfile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alarm)) return false;
        Alarm alarm = (Alarm) o;
        return Objects.equals(id, alarm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
