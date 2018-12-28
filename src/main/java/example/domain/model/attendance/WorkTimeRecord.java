package example.domain.model.attendance;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

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

    public WorkTimeRecord(WorkStartTime workStartTime, WorkEndTime workEndTime, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        this.workTimeRange = new WorkTimeRange(workStartTime, workEndTime);
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

    public HourAndMinute totalWorkTime() {
        return HourAndMinute.from(workTime().toMinute().add(midnightWorkTime().toMinute()));
    }

    public HourAndMinute totalBreakTime() {
        return HourAndMinute.from(normalBreakTime.toMinute().add(midnightBreakTime.toMinute()));
    }

    public HourAndMinute workTime() {
        // TODO 勤務時間を休憩時間が超える場合のバリデーションをどこかでやる
        return HourAndMinute.from(normalBreakTime.subtractFrom(workTimeRange.normalWorkTime()));
    }

    public HourAndMinute midnightWorkTime() {
        // TODO 深夜勤務時間を深夜休憩時間が超える場合のバリデーションをどこかでやる
        return HourAndMinute.from(midnightBreakTime.subtractFrom(workTimeRange.midnightWorkMinute()));
    }

    public HourAndMinute overTime() {
        Minute totalWorkMinute = totalWorkTime().toMinute();

        DailyOvertimeWork dailyOvertimeWork = DailyOvertimeWork.legal();
        Minute overMinute = dailyOvertimeWork.overMinute(totalWorkMinute);
        return HourAndMinute.from(overMinute);
    }
}
