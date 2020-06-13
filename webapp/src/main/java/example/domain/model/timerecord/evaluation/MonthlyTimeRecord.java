package example.domain.model.timerecord.evaluation;

import java.util.List;

/**
 * 月の勤務実績
 */
public class MonthlyTimeRecord {
    TimeRecords value;

    public MonthlyTimeRecord(TimeRecords value) {
        this.value = value;
    }

    public List<TimeRecord> list() {
        return value.list();
    }
}
