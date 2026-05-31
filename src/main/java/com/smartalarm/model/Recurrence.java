package com.smartalarm.model;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Recurrence {
    private final Set<DayOfWeek> days;

    public Recurrence(Set<DayOfWeek> days) {
        this.days = days == null ? Collections.emptySet() : EnumSet.copyOf(days);
    }

    public static Recurrence none() {
        return new Recurrence(Collections.emptySet());
    }

    public static Recurrence daily() {
        return new Recurrence(EnumSet.allOf(DayOfWeek.class));
    }

    public static Recurrence weekdays() {
        return new Recurrence(EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
    }

    public static Recurrence weekends() {
        return new Recurrence(EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
    }

    public boolean repeatsOn(DayOfWeek day) {
        return days.contains(day);
    }

    public boolean isRepeating() {
        return !days.isEmpty();
    }

    public Set<DayOfWeek> getDays() {
        return Collections.unmodifiableSet(days);
    }

    @Override
    public String toString() {
        if (days.isEmpty()) {
            return "One-time";
        }
        if (days.size() == DayOfWeek.values().length) {
            return "Every day";
        }
        return days.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recurrence)) return false;
        Recurrence that = (Recurrence) o;
        return Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }
}
