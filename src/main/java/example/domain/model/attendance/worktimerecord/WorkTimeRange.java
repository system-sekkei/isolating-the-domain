package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.ClockTimeRange;

/**
 * 業務時刻の範囲
 */
public class WorkTimeRange {

    WorkStartTime startTime;
    WorkEndTime endTime;

    @Deprecated
    WorkTimeRange() {
    }

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

    public static WorkTimeRange of(ClockTimeRange timeRange) {
        return new WorkTimeRange(new WorkStartTime(timeRange.begin()), new WorkEndTime(timeRange.end()));
    }

    public TotalBindingTime totalBindingTime() {
        return new TotalBindingTime(startTime.normalizedHourTime().until(endTime.normalizedHourTime()));
    }

    public NormalBindingTime normalBindingTime() {
        return new NormalBindingTime(totalBindingTime(), midnightBindingTime());
    }

    public MidnightBindingTime midnightBindingTime() {
        return new MidnightBindingTime(normalizedClockTimeRange());
    }

    private ClockTimeRange normalizedClockTimeRange() {
        return new ClockTimeRange(startTime.normalizedHourTime(), endTime.normalizedHourTime());
    }
}
