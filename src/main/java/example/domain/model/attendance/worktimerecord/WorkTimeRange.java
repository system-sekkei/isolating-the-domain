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

    public BindingTime bindingTime() {
        return new BindingTime(startTime.normalizedHourTime().until(endTime.normalizedHourTime()));
    }

    public DaytimeBindingTime daytimeBindingTime() {
        return new DaytimeBindingTime(bindingTime(), midnightBindingTime());
    }

    public MidnightBindingTime midnightBindingTime() {
        return new MidnightBindingTime(normalizedClockTimeRange());
    }

    private ClockTimeRange normalizedClockTimeRange() {
        return new ClockTimeRange(startTime.normalizedHourTime(), endTime.normalizedHourTime());
    }
}
