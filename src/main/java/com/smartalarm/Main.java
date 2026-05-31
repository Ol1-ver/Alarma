package com.smartalarm;

import com.smartalarm.model.Alarm;
import com.smartalarm.model.AlarmCategory;
import com.smartalarm.model.MathChallenge;
import com.smartalarm.model.Recurrence;
import com.smartalarm.model.SoundProfile;
import com.smartalarm.service.AlarmManager;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        AlarmManager manager = new AlarmManager();

        SoundProfile morningTone = new SoundProfile("Morning Breeze", 35, 90);
        SoundProfile workoutTone = new SoundProfile("Energetic Beat", 45, 100);
        SoundProfile medicineTone = new SoundProfile("Soft Reminder", 25, 65);

        Alarm workAlarm = manager.addAlarm("Morning commute", 7, 0, morningTone, Recurrence.weekdays(), AlarmCategory.WORK);
        Alarm yogaAlarm = manager.addAlarm("Weekend yoga", 9, 30, workoutTone, Recurrence.weekends(), AlarmCategory.FITNESS);
        Alarm medicineAlarm = manager.addAlarm("Vitamin D", 13, 0, medicineTone, Recurrence.daily(), AlarmCategory.MEDICINE);
        Alarm travelAlarm = manager.addAlarm("Airport pickup", 6, 15, morningTone, new Recurrence(Set.of(DayOfWeek.SATURDAY)), AlarmCategory.TRAVEL);

        System.out.println("Registered alarms:");
        manager.listAlarms().forEach(System.out::println);

        System.out.println("\nNext active alarm:");
        manager.nextActiveAlarm(LocalDateTime.now()).ifPresentOrElse(
                alarm -> System.out.println("Next alarm: " + alarm.getLabel() + " at " + String.format("%02d:%02d", alarm.getHour(), alarm.getMinute())),
                () -> System.out.println("No active alarms found."));

        System.out.println("\nAlarm conflicts:");
        manager.findConflicts(10).forEach(System.out::println);

        System.out.println("\nEnabling vacation mode. All alarms will be temporarily suppressed.");
        manager.enableVacationMode();
        System.out.println("Vacation mode active: " + manager.isVacationMode());

        System.out.println("\nDisabling vacation mode and testing the math challenge to stop alarms.");
        manager.disableVacationMode();

        LocalDateTime sampleTime = LocalDateTime.of(2026, Month.JUNE, 1, 7, 0);
        System.out.println("Testing alarm evaluation at " + sampleTime + ".");
        java.util.List<Alarm> active = manager.activeAlarms(sampleTime);
        if (active.isEmpty()) {
            System.out.println("No alarms are ringing at this moment.");
        } else {
            for (Alarm alarm : active) {
                System.out.println("Alarm ringing: " + alarm.getLabel());
                MathChallenge challenge = manager.createMathChallenge(alarm.getId(), sampleTime);
                System.out.println("Solve to stop alarm: " + challenge.getQuestion());
                boolean correct = manager.submitMathChallengeAnswer(alarm.getId(), challenge.getExpectedAnswer(), sampleTime);
                System.out.println(correct ? "Answer correct, alarm stopped." : "Answer incorrect, alarm snoozed.");
            }
        }

        System.out.println("\nCurrent sleep statistics:");
        System.out.println(manager.getSleepStatistics());
    }
}
