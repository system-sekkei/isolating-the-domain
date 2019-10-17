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

    // TODO: あとでStartDateとEndDateを追加
    public WorkRange(StartTime startTime, EndTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // TODO: 2暦日にわたるかどうかの判断ロジック
    // TODO: 就業日を返すメソッド実装

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
        return new DaytimeBindingTime(bindingTime(), nightBindingTime());
    }

    public NightBindingTime nightBindingTime() {
        return new NightBindingTime(quarterRoundClockTimeRange());
    }
}
