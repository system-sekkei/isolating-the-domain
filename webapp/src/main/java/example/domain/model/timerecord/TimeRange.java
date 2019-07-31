package example.domain.model.timerecord;

import example.domain.model.timerecord.bindingtime.BindingTime;
import example.domain.model.timerecord.bindingtime.DaytimeBindingTime;
import example.domain.model.timerecord.bindingtime.MidnightBindingTime;
import example.domain.type.time.QuarterRoundClockTimeRange;

/**
 * 勤務の開始と終了
 */
public class TimeRange {

    StartTime startTime;
    EndTime endTime;

    @Deprecated
    TimeRange() {
    }

    public TimeRange(StartTime startTime, EndTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    QuarterRoundClockTimeRange quarterRoundClockTimeRange() {
        return new QuarterRoundClockTimeRange(startTime.normalizedClockTime(), endTime.normalizedClockTime());
    }

    public StartTime start() {
        return startTime;
    }

    public EndTime end() {
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
