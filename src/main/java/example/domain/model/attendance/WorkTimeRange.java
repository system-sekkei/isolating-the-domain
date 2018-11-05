package example.domain.model.attendance;

import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

/**
 * 業務時刻の範囲
 */
public class WorkTimeRange {

    private final WorkTime startTime;
    private final WorkTime endTime;

    public WorkTimeRange(WorkTime startTime, WorkTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkTimeRange(HourTime start, HourTime end) {
        this(new WorkTime(start), new WorkTime(end));
    }

    public Minute workMinute() {
        return startTime.until(endTime);
    }

    public WorkTime start() {
        return startTime;
    }

    public WorkTime end() {
        return endTime;
    }
}
