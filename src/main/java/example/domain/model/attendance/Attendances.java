package example.domain.model.attendance;

import example.domain.type.date.DateRange;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤怠情報
 */
public class Attendances {
    List<Attendance> list;

    public Attendances(List<Attendance> list) {
        this.list = list;
    }

    public List<Attendance> list() {
        return list;
    }

    public WorkTimeSummary summarize() {
        return list.stream().collect(WorkTimeSummary.collector());
    }

    public Attendance at(WorkDate day) {
        return list.stream()
                .filter(worked -> worked.isWorkedAt(day))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(day.toString()));
    }

    public Attendances rangeOf(DateRange range) {
        List<Attendance> inRangeAttendances = list.stream()
                .filter(attendance -> attendance.workDate().inRange(range))
                .collect(Collectors.toList());
        return new Attendances(inRangeAttendances);
    }

    public AttendanceStatus statusOf(WorkDate workDate) {
        return list.stream().anyMatch(attendance -> attendance.isWorkedAt(workDate)) ? AttendanceStatus.出勤 : AttendanceStatus.非出勤;
    }
}
