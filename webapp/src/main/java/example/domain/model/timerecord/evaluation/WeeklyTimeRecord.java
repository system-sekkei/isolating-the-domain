package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DaysOffStatus;
import example.domain.model.legislation.WeeklyWorkingHoursLimit;
import example.domain.model.legislation.WeeklyWorkingHoursStatus;

import java.util.Comparator;
import java.util.Optional;

/**
 * 週の勤務実績
 */
public class WeeklyTimeRecord {
    TimeRecords value;

    public WeeklyTimeRecord(TimeRecords value) {
        this.value = value;
    }

    public WeeklyWorkingHoursStatus weeklyWorkingHoursStatus() {
        if (value.withinDailyLimitWorkTimeTotal().moreThan(WeeklyWorkingHoursLimit.legal().toMinute())) {
            return WeeklyWorkingHoursStatus.週の法定時間内労働時間の累計が４０時間を超えている;
        } else {
            return WeeklyWorkingHoursStatus.週の法定時間内労働時間の累計が４０時間以内;
        }
    }

    public WeeklyTimeRecord recordsToDate(WorkDate workDate) {
        return new WeeklyTimeRecord(value.recordsToDate(workDate));
    }

    public Optional<TimeRecord> lastDayOff() {
        return value.list.stream()
                .filter(record -> record.daysOffStatus == DaysOffStatus.休日)
                .max(Comparator.comparing(r -> r.workDate().toDate().value()));
    }
}
