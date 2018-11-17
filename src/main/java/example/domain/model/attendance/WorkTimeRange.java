package example.domain.model.attendance;

import example.domain.model.labour_standards_law.Midnight;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.HourTimeRange;
import example.domain.type.time.Minute;

/**
 * 業務時刻の範囲
 */
public class WorkTimeRange {

    private final WorkStartTime startTime;
    private final WorkEndTime endTime;

    public WorkTimeRange(WorkStartTime startTime, WorkEndTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Minute workMinute() {
        return startTime.normalizedHourTime().until(endTime.normalizedHourTime());
    }

    public WorkStartTime start() {
        return startTime;
    }

    public WorkEndTime end() {
        return endTime;
    }

    public boolean notWork() {
        return startTime.value.value().equals(endTime.value.value());
    }

    public HourTimeRange toTimeRange() {
        return new HourTimeRange(startTime.value, endTime.value);
    }

    public static WorkTimeRange of(HourTimeRange timeRange) {
        return new WorkTimeRange(new WorkStartTime(timeRange.begin()), new WorkEndTime(timeRange.end()));
    }

    public HourAndMinute midnightWorkTime() {
        WorkTimeRange midnightRange = WorkTimeRange.of(toTimeRange().intersect(new Midnight().range()));
        return HourAndMinute.from(midnightRange.workMinute());
    }

    public HourAndMinute normalWorkTime() {
        return HourAndMinute.from(workMinute().subtract(midnightWorkTime().toMinute()));
    }
}
