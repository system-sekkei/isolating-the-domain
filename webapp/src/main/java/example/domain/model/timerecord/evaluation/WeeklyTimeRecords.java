package example.domain.model.timerecord.evaluation;

import java.util.List;

/**
 * 週の勤務実績
 */
public class WeeklyTimeRecords {
    List<TimeRecord> list;

    public WeeklyTimeRecords(List<TimeRecord> list) {
        this.list = list;
    }

    public List<TimeRecord> list() {
        return list;
    }
}
