package example.domain.model.timerecord;

import example.domain.model.timerecord.bindingtime.BindingTime;
import example.domain.model.timerecord.bindingtime.DaytimeBindingTime;
import example.domain.model.timerecord.bindingtime.NightBindingTime;
import example.domain.type.time.QuarterRoundClockTimeRange;

/**
 * 勤務の開始と終了
 */
public class WorkRange {

    // TODO: StartDateTimeとEndDateTimeをつくるべきかどうかかんがえる
    StartDate startDate;
    StartTime startTime;
    EndDate endDate;
    EndTime endTime;

    @Deprecated
    WorkRange() {
    }

    public WorkRange(WorkDate workDate, StartTime startTime, EndTime endTime) {
        this.startDate = new StartDate(workDate);
        this.startTime = startTime;
        this.endDate = new EndDate(workDate, endTime);
        this.endTime = endTime;
    }

    // TODO: 2暦日にわたるかどうかの判断ロジック

    QuarterRoundClockTimeRange quarterRoundClockTimeRange() {
        return new QuarterRoundClockTimeRange(startTime.normalizedClockTime(), endTime.normalizedClockTime());
    }

    public StartTime start() {
        return startTime;
    }

    public EndTime end() {
        return endTime;
    }

    public WorkDate workDate() {
        return new WorkDate(startDate.value);
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
