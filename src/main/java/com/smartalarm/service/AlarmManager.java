package com.smartalarm.service;

import com.smartalarm.model.Alarm;
import com.smartalarm.model.AlarmCategory;
import com.smartalarm.model.MathChallenge;
import com.smartalarm.model.Recurrence;
import com.smartalarm.model.SoundProfile;
import com.smartalarm.model.SleepStatistics;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AlarmManager {
    private final Map<String, Alarm> alarms = new HashMap<>();
    private final Map<String, MathChallenge> activeChallenges = new HashMap<>();
    private final SnoozeManager snoozeManager = new SnoozeManager();
    private final SleepStatistics sleepStatistics = new SleepStatistics();
    private boolean vacationMode;

    public Alarm addAlarm(String label, int hour, int minute, SoundProfile soundProfile, Recurrence recurrence, AlarmCategory category) {
        Alarm newAlarm = new Alarm(label, hour, minute, soundProfile, recurrence, category);
        if (containsConflict(newAlarm)) {
            System.out.printf("Warning: new alarm may conflict with an existing alarm within 10 minutes at %02d:%02d\n", hour, minute);
        }
        alarms.put(newAlarm.getId(), newAlarm);
        return newAlarm;
    }

    public void removeAlarm(String alarmId) {
        alarms.remove(alarmId);
        snoozeManager.clearSnooze(alarmId);
    }

    public void activateAlarm(String alarmId) {
        getAlarm(alarmId).ifPresent(Alarm::enable);
    }

    public void deactivateAlarm(String alarmId) {
        getAlarm(alarmId).ifPresent(Alarm::disable);
    }

    public Optional<Alarm> getAlarm(String alarmId) {
        return Optional.ofNullable(alarms.get(alarmId));
    }

    public List<Alarm> listAlarms() {
        List<Alarm> sorted = new ArrayList<>(alarms.values());
        sorted.sort(Comparator.comparingInt(Alarm::getHour).thenComparingInt(Alarm::getMinute));
        return Collections.unmodifiableList(sorted);
    }

    public Optional<Alarm> nextActiveAlarm(LocalDateTime now) {
        return listAlarms().stream()
                .filter(alarm -> alarm.isEnabled())
                .filter(alarm -> !vacationMode)
                .map(alarm -> alarm.nextActivation(now))
                .filter(next -> next != null)
                .min(LocalDateTime::compareTo)
                .flatMap(next -> listAlarms().stream()
                        .filter(alarm -> alarm.nextActivation(now) != null)
                        .filter(alarm -> alarm.nextActivation(now).equals(next))
                        .findFirst());
    }

    public List<Alarm> activeAlarms(LocalDateTime now) {
        if (vacationMode) {
            return Collections.emptyList();
        }
        List<Alarm> active = new ArrayList<>();
        for (Alarm alarm : alarms.values()) {
            if (alarm.isDueAt(now) || snoozeManager.isSnoozed(alarm.getId(), now)) {
                active.add(alarm);
            }
        }
        return active;
    }

    public void snooze(String alarmId, LocalDateTime now) {
        getAlarm(alarmId).ifPresent(alarm -> {
            snoozeManager.snooze(alarmId, now);
            sleepStatistics.recordSnooze();
            alarm.snoozeUntil(now.plusMinutes(snoozeManager.getDurationMinutes()));
        });
    }

    public void stop(String alarmId, LocalDateTime now) {
        getAlarm(alarmId).ifPresent(alarm -> {
            alarm.clearSnooze();
            snoozeManager.clearSnooze(alarmId);
            alarm.dismiss();
            LocalDateTime scheduled = now.withHour(alarm.getHour()).withMinute(alarm.getMinute()).withSecond(0).withNano(0);
            sleepStatistics.recordStop(scheduled, now);
        });
    }

    public MathChallenge createMathChallenge(String alarmId, LocalDateTime now) {
        Alarm alarm = getAlarm(alarmId).orElseThrow(() -> new IllegalArgumentException("Alarm not found"));
        if (!alarm.isDueAt(now)) {
            throw new IllegalStateException("Alarm is not currently ringing");
        }
        MathChallenge challenge = MathChallenge.generate(now.toEpochSecond(java.time.ZoneOffset.UTC));
        activeChallenges.put(alarmId, challenge);
        return challenge;
    }

    public boolean submitMathChallengeAnswer(String alarmId, int answer, LocalDateTime now) {
        MathChallenge challenge = activeChallenges.get(alarmId);
        if (challenge == null) {
            throw new IllegalStateException("No math challenge pending for this alarm");
        }
        if (challenge.validateAnswer(answer)) {
            stop(alarmId, now);
            activeChallenges.remove(alarmId);
            return true;
        }
        snooze(alarmId, now);
        activeChallenges.remove(alarmId);
        return false;
    }

    public Optional<MathChallenge> getPendingChallenge(String alarmId) {
        return Optional.ofNullable(activeChallenges.get(alarmId));
    }

    public void enableVacationMode() {
        vacationMode = true;
    }

    public void disableVacationMode() {
        vacationMode = false;
    }

    public boolean isVacationMode() {
        return vacationMode;
    }

    public SleepStatistics getSleepStatistics() {
        return sleepStatistics;
    }

    public List<String> findConflicts(int thresholdMinutes) {
        List<String> conflicts = new ArrayList<>();
        List<Alarm> sorted = listAlarms();
        for (int i = 0; i < sorted.size(); i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                Alarm a = sorted.get(i);
                Alarm b = sorted.get(j);
                if (a.conflictsWith(b, thresholdMinutes)) {
                    conflicts.add(String.format("Conflict: %s at %02d:%02d and %s at %02d:%02d",
                            a.getLabel(), a.getHour(), a.getMinute(), b.getLabel(), b.getHour(), b.getMinute()));
                }
            }
        }
        return conflicts;
    }

    public Set<DayOfWeek> getUsedDays() {
        Set<DayOfWeek> days = new HashSet<>();
        for (Alarm alarm : alarms.values()) {
            days.addAll(alarm.getRecurrence().getDays());
        }
        return days;
    }

    private boolean containsConflict(Alarm newAlarm) {
        for (Alarm alarm : alarms.values()) {
            if (alarm.conflictsWith(newAlarm, 10)) {
                return true;
            }
        }
        return false;
    }
}
