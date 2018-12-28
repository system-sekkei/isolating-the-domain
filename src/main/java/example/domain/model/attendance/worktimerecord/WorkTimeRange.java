package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.QuarterRoundClockTimeRange;

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

    QuarterRoundClockTimeRange quarterRoundClockTimeRange() {
        return new QuarterRoundClockTimeRange(startTime.normalizedClockTime(), endTime.normalizedClockTime());
    }

    public WorkStartTime start() {
        return startTime;
    }

    public WorkEndTime end() {
        return endTime;
    }

    public BindingTime bindingTime() {
        return new BindingTime(quarterRoundClockTimeRange().between());
    }

    public DaytimeBindingTime daytimeBindingTime() {
        return new DaytimeBindingTime(bindingTime(), midnightBindingTime());
    }

    public MidnightBindingTime midnightBindingTime() {
        return new MidnightBindingTime(quarterRoundClockTimeRange());
    }
}
