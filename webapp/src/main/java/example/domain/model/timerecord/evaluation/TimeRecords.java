package example.domain.model.timerecord.evaluation;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 勤務実績一覧
 */
public class TimeRecords {
    List<TimeRecord> list;

    public TimeRecords(List<TimeRecord> list) {
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
        return list.stream().anyMatch(timeRecord -> timeRecord.isWorkedAt(workDate)) ? Recorded.記録あり : Recorded.記録なし;
    }

    public AttendDates attendDates() {
        return new AttendDates(list.stream().map(TimeRecord::workDate).collect(toList()));
    }

    public TimeRecords weeklyRecords(WorkDate day) {
        // TODO: 該当週の勤務実績を抽出する処理を書く
        return this;
    }

    public TimeRecords recordsDayBefore(WorkDate day) {
        return new TimeRecords(list.stream().dropWhile(record -> record.isWorkedAt(day)).collect(toList()));
    }
}
