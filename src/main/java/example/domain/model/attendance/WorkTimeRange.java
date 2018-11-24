package example.domain.model.attendance;

import example.domain.model.labour_standards_law.Midnight;
import example.domain.type.time.ClockTimeRange;
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

    public WorkStartTime start() {
        return startTime;
    }

    public WorkEndTime end() {
        return endTime;
    }

    public ClockTimeRange toTimeRange() {
        return new ClockTimeRange(startTime.value, endTime.value);
    }

    public static WorkTimeRange of(ClockTimeRange timeRange) {
        return new WorkTimeRange(new WorkStartTime(timeRange.begin()), new WorkEndTime(timeRange.end()));
    }

    public Minute totalWorkMinute() {
        return startTime.normalizedHourTime().until(endTime.normalizedHourTime());
    }

    public Minute workMinute() {
        return totalWorkMinute().subtract(midnightWorkMinute());
    }

    public Minute midnightWorkMinute() {
        WorkTimeRange midnightRange = WorkTimeRange.of(toTimeRange().intersect(Midnight.legal().range()));
        return midnightRange.totalWorkMinute();
    }
}
