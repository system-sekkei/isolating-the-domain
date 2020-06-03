package example.domain.model.timerecord.evaluation;

import example.domain.model.attendance.WorkMonth;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 週の勤務実績一覧
 */
public class WeeklyTimeRecords {
    List<WeeklyTimeRecord> list;

    public WeeklyTimeRecords(List<WeeklyTimeRecord> list) {
        this.list = list;
    }

    public TimeRecords monthlyTimeRecords(WorkMonth month) {
        return new TimeRecords(list.stream()
                .flatMap(weeklyTimeRecord -> weeklyTimeRecord.findByMonth(month).list().stream())
                .collect(Collectors.toList()));
    }
}
