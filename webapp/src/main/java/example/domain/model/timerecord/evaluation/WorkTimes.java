package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.WeeklyWorkingHoursLimit;
import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

import java.util.List;

/**
 * 勤務時間のリスト
 */
public class WorkTimes {
    List<WorkTime> list;

    public WorkTimes(List<WorkTime> list) {
        this.list = list;
    }

    public OverLegalHoursWorkTime weeklyOverLegalHoursWorkTime() {
        QuarterHour overMinute = total().overMinute(new QuarterHour(WeeklyWorkingHoursLimit.legal().toMinute()));
        return new OverLegalHoursWorkTime(overMinute);
    }

    public OverLegalHoursWorkTime dailyOverLegalHoursWorkTimePerWeek() {
        OverLegalHoursWorkTime overLegalHoursWorkTime = new OverLegalHoursWorkTime(new QuarterHour());
        for (WorkTime workTime : list) {
            overLegalHoursWorkTime = overLegalHoursWorkTime.add(workTime.dailyOverLegalHoursWorkTime());
        }
        return overLegalHoursWorkTime;
    }

    public QuarterHour total() {
        QuarterHour total = new QuarterHour(new Minute(0));
        for (WorkTime workTime : list) {
            total = total.add(workTime.value);
        }
        return total;
    }
}
