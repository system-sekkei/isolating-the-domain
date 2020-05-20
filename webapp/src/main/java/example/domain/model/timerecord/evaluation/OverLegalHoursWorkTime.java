package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DailyWorkingHoursLimit;
import example.domain.model.legislation.WeeklyWorkingHoursLimit;
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
        List<WorkTime> weeklyWorkTimes = weeklyTimeRecords.list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime.workTime()).collect(Collectors.toList());

        OverLegalHoursWorkTime dailyOverLegalHoursWorkTime = dailyOverLegalHoursWorkTimePerWeek(weeklyWorkTimes);
        OverLegalHoursWorkTime weeklyOverLegalHoursWorkTime = weeklyOverLegalHoursWorkTime(weeklyWorkTimes);

        return OverLegalHoursWorkTime.max(dailyOverLegalHoursWorkTime, weeklyOverLegalHoursWorkTime);
    }

    static OverLegalHoursWorkTime dailyOverLegalHoursWorkTimePerWeek(List<WorkTime> weeklyWorkTimes) {
        OverLegalHoursWorkTime overLegalHoursWorkTime = new OverLegalHoursWorkTime(new QuarterHour());
        for (WorkTime workTime : weeklyWorkTimes) {
            overLegalHoursWorkTime = overLegalHoursWorkTime.add(dailyOverLegalHoursWorkTime(workTime));
        }
        return overLegalHoursWorkTime;
    }

    OverLegalHoursWorkTime add(OverLegalHoursWorkTime value) {
        return new OverLegalHoursWorkTime(this.value.add(value.value));
    }

    static OverLegalHoursWorkTime dailyOverLegalHoursWorkTime(WorkTime workTime) {
        QuarterHour overMinute = workTime.quarterHour().overMinute(new QuarterHour(DailyWorkingHoursLimit.legal().toMinute()));
        return new OverLegalHoursWorkTime(overMinute);
    }

    static OverLegalHoursWorkTime weeklyOverLegalHoursWorkTime(List<WorkTime> weeklyWorkTimes) {
        QuarterHour weeklyTotal = new QuarterHour(new Minute(0));
        for (WorkTime workTime : weeklyWorkTimes) {
            weeklyTotal = weeklyTotal.add(workTime.value);
        }
        QuarterHour overMinute = weeklyTotal.overMinute(new QuarterHour(WeeklyWorkingHoursLimit.legal().toMinute()));
        return new OverLegalHoursWorkTime(overMinute);
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
