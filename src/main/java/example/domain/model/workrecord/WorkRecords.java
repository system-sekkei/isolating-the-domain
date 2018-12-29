package example.domain.model.workrecord;

import example.domain.type.date.DateRange;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤務実績一覧
 */
public class WorkRecords {
    List<WorkRecord> list;

    public WorkRecords(List<WorkRecord> list) {
        this.list = list;
    }

    public List<WorkRecord> list() {
        return list;
    }

    public WorkRecord at(WorkDate day) {
        return list.stream()
                .filter(worked -> worked.isWorkedAt(day))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(day.toString()));
    }

    public WorkRecords rangeOf(DateRange range) {
        List<WorkRecord> inRangeWorkRecords = list.stream()
                .filter(attendance -> attendance.workDate().inRange(range))
                .collect(Collectors.toList());
        return new WorkRecords(inRangeWorkRecords);
    }

    public Recorded recordedAt(WorkDate workDate) {
        return list.stream().anyMatch(attendance -> attendance.isWorkedAt(workDate)) ? Recorded.記録あり : Recorded.記録なし;
    }
}
