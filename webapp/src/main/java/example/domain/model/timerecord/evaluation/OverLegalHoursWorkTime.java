package example.domain.model.timerecord.evaluation;

import example.domain.model.attendance.WeeklyTimeRecords;
import example.domain.model.legislation.DailyWorkingHoursLimit;
import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 法定時間外労働 労働時間
 */
public class OverLegalHoursWorkTime {
    QuarterHour value;

    public OverLegalHoursWorkTime(QuarterHour value) {
        this.value = value;
    }

    public static OverLegalHoursWorkTime from(WeeklyTimeRecords weeklyTimeRecords) {
        OverLegalHoursWorkTime dailyOverLegalHoursWorkTime = dailyOverLegalHoursWorkTimePerWeek(weeklyTimeRecords);
        OverLegalHoursWorkTime weeklyOverLegalHoursWorkTime = weeklyOverLegalHoursWorkTime();

        return OverLegalHoursWorkTime.max(dailyOverLegalHoursWorkTime, weeklyOverLegalHoursWorkTime);
    }

    static OverLegalHoursWorkTime dailyOverLegalHoursWorkTimePerWeek(WeeklyTimeRecords weeklyTimeRecords) {
        List<WorkTime> workTimes = weeklyTimeRecords.list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime.workTime()).collect(Collectors.toList());

        OverLegalHoursWorkTime overLegalHoursWorkTime = new OverLegalHoursWorkTime(new QuarterHour());
        for (WorkTime workTime : workTimes) {
            overLegalHoursWorkTime = overLegalHoursWorkTime.add(dailyOverLegalHoursWorkTime(workTime));
        }
        return overLegalHoursWorkTime;
    }

    OverLegalHoursWorkTime add(OverLegalHoursWorkTime value) {
        return new OverLegalHoursWorkTime(this.value.add(value.value));
    }

    static OverLegalHoursWorkTime dailyOverLegalHoursWorkTime(WorkTime workTime) {
        Minute overMinute = workTime.quarterHour().overMinute(DailyWorkingHoursLimit.legal().toMinute());
        return new OverLegalHoursWorkTime(new QuarterHour(overMinute));
    }

    static OverLegalHoursWorkTime weeklyOverLegalHoursWorkTime() {
        // TODO:
        return new OverLegalHoursWorkTime(new QuarterHour());
    }

    static OverLegalHoursWorkTime max(OverLegalHoursWorkTime a, OverLegalHoursWorkTime b) {
        if (a.value.moreThan(b.value)) {
            return a;
        }
        return b;
     }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
