package example.domain.model.timerecord;

import example.domain.model.timerecord.bindingtime.BindingTime;
import example.domain.model.timerecord.bindingtime.DaytimeBindingTime;
import example.domain.model.timerecord.bindingtime.NightBindingTime;
import example.domain.type.time.QuarterRoundClockTimeRange;

/**
 * 勤務の開始と終了
 */
public class WorkRange {

    StartDateTime startDateTime;
    EndDateTime endDateTime;

    @Deprecated
    WorkRange() {
    }

    public WorkRange(StartDateTime startDateTime, EndDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    // TODO: 2暦日にわたるかどうかの判断ロジック

    QuarterRoundClockTimeRange quarterRoundClockTimeRange() {
        return new QuarterRoundClockTimeRange(startDateTime.startTime.normalizedClockTime(), endDateTime.endTime.normalizedClockTime());
    }

    public StartTime start() {
        return startDateTime.startTime;
    }

    public EndTime end() {
        return endDateTime.endTime;
    }

    public WorkDate workDate() {
        return new WorkDate(startDateTime.startDate.value);
    }

    public BindingTime bindingTime() {
        return new BindingTime(quarterRoundClockTimeRange().between());
    }

    public DaytimeBindingTime daytimeBindingTime() {
        return new DaytimeBindingTime(bindingTime(), nightBindingTime());
    }

    public NightBindingTime nightBindingTime() {
        return new NightBindingTime(quarterRoundClockTimeRange());
    }
}
