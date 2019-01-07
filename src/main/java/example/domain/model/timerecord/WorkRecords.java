package example.domain.model.timerecord;

import java.util.List;

/**
 * 勤務実績一覧
 */
public class WorkRecords {
    List<TimeRecord> list;

    public WorkRecords(List<TimeRecord> list) {
        this.list = list;
    }

    public List<TimeRecord> list() {
        return list;
    }

    public TimeRecord at(WorkDate day) {
        return list.stream()
                .filter(worked -> worked.isWorkedAt(day))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(day.toString()));
    }

    public Recorded recordedAt(WorkDate workDate) {
        return list.stream().anyMatch(workRecord -> workRecord.isWorkedAt(workDate)) ? Recorded.記録あり : Recorded.記録なし;
    }
}
