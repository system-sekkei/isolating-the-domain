package example.domain.model.attendance.worktimerecord;

import example.domain.type.time.HourAndMinute;

/**
 * 労働時間実績
 */
public class WorkTimeRecord {
    WorkTimeRange workTimeRange;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    @Deprecated
    public WorkTimeRecord() {
    }

    public WorkTimeRecord(WorkTimeRange workTimeRange, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        this.workTimeRange = workTimeRange;
        this.normalBreakTime = normalBreakTime;
        this.midnightBreakTime = midnightBreakTime;
    }

    public WorkTimeRange workTimeRange() {
        return workTimeRange;
    }

    public NormalBreakTime normalBreakTime() {
        return normalBreakTime;
    }

    public MidnightBreakTime midnightBreakTime() {
        return midnightBreakTime;
    }

    public TotalWorkTime totalWorkTime() {
        return new TotalWorkTime(workTimeRange, totalBreakTime());
    }

    public TotalBreakTime totalBreakTime() {
        return new TotalBreakTime(normalBreakTime, midnightBreakTime);
    }

    public HourAndMinute workTime() {
        // TODO 勤務時間を休憩時間が超える場合のバリデーションをどこかでやる
        return HourAndMinute.from(normalBreakTime.subtractFrom(workTimeRange.normalBindingTime()));
    }

    public HourAndMinute midnightWorkTime() {
        // TODO 深夜勤務時間を深夜休憩時間が超える場合のバリデーションをどこかでやる
        return HourAndMinute.from(midnightBreakTime.subtractFrom(workTimeRange.midnightWorkMinute()));
    }

    public OverWorkTime overTime() {
        return new OverWorkTime(totalWorkTime());
    }
}
