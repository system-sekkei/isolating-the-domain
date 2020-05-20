package example.domain.model.timerecord.evaluation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 週の勤務実績
 */
public class WeeklyTimeRecords {
    List<TimeRecord> list;

    public WeeklyTimeRecords(List<TimeRecord> list) {
        this.list = list;
    }

    public OverLegalHoursWorkTime overLegalHoursWorkTime() {
        WorkTimes weeklyWorkTimes = new WorkTimes(list().stream()
                .map(timeRecord -> timeRecord.actualWorkDateTime.workTime()).collect(Collectors.toList()));

        OverLegalHoursWorkTime dailyOverLegalHoursWorkTime = weeklyWorkTimes.dailyOverLegalHoursWorkTimePerWeek();
        OverLegalHoursWorkTime weeklyOverLegalHoursWorkTime = weeklyWorkTimes.weeklyOverLegalHoursWorkTime();

        return OverLegalHoursWorkTime.max(dailyOverLegalHoursWorkTime, weeklyOverLegalHoursWorkTime);
    }

    public List<TimeRecord> list() {
        return list;
    }
}
