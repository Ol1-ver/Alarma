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

public class AlarmManagerDemo {
    public static void main(String[] args) {
        AlarmManager manager = new AlarmManager();
        SoundProfile tone = new SoundProfile("Demo sound", 30, 70);
        Alarm alarm = manager.addAlarm("Demo alarm", 7, 45, tone, Recurrence.weekdays(), AlarmCategory.STUDY);

        System.out.println("Created alarm: " + alarm);
        System.out.println("Next activation: " + alarm.nextActivation(LocalDateTime.of(2026, Month.JUNE, 1, 6, 0)));

        LocalDateTime testTime = LocalDateTime.of(2026, Month.JUNE, 1, 7, 45);
        System.out.println("Is due at " + testTime + "? " + alarm.isDueAt(testTime));

        MathChallenge challenge = manager.createMathChallenge(alarm.getId(), testTime);
        System.out.println("Challenge: " + challenge.getQuestion());
        boolean solved = manager.submitMathChallengeAnswer(alarm.getId(), challenge.getExpectedAnswer(), testTime);
        System.out.println(solved ? "Correct answer, alarm stopped." : "Incorrect answer, alarm snoozed.");

        manager.enableVacationMode();
        System.out.println("Vacation mode active: " + manager.isVacationMode());
        System.out.println("Active alarms during vacation: " + manager.activeAlarms(testTime.plusMinutes(10)).size());
    }
}
