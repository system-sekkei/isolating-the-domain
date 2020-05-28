package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.DaysOffStatus;

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

    public TimeRecords recordsToDate(WorkDate workDate) {
        return value.recordsToDate(workDate);
    }

    public Optional<TimeRecord> lastDayOff() {
        return value.list.stream()
                .filter(record -> record.daysOffStatus == DaysOffStatus.休日)
                .max(Comparator.comparing(r -> r.workDate().toDate().value()));
    }
}
