package example.domain.model.timerecord.evaluation;

import java.util.List;

/**
 * 前月の勤務実績
 */
public class BeforeMonthlyTimeRecord {
    TimeRecords value;

    public BeforeMonthlyTimeRecord(TimeRecords value) {
        this.value = value;
    }

    public List<TimeRecord> list() {
        return value.list();
    }
}
