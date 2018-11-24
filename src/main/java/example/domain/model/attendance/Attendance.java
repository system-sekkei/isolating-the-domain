package example.domain.model.attendance;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.date.Date;
import example.domain.type.time.ClockTime;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 日次勤怠
 */
public class Attendance {
    Date date;
    WorkTimeRange workTimeRange;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    public Attendance() {
        this(Date.now());
    }

    public Attendance(Date date) {
        // TODO 休みの扱い
        this(date, new WorkStartTime(new ClockTime("00:00")), new WorkEndTime(new ClockTime("00:00")), new NormalBreakTime(new Minute(0)), new MidnightBreakTime("0"));
    }

    public Attendance(Date date, WorkStartTime workStartTime, WorkEndTime workEndTime, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        this.date = date;
        this.workTimeRange = new WorkTimeRange(workStartTime, workEndTime);
        this.normalBreakTime = normalBreakTime;
        this.midnightBreakTime = midnightBreakTime;
    }

    public Date date() {
        return date;
    }

    public WorkTimeRange workTimeRange() {
        return workTimeRange;
    }

    public HourAndMinute totalBreakTime() {
        return HourAndMinute.from(normalBreakTime.toMinute().add(midnightBreakTime.toMinute()));
    }

    public HourAndMinute totalWorkTime() {
        return HourAndMinute.from(workTime().toMinute().add(midnightWorkTime().toMinute()));
    }

    public HourAndMinute workTime() {
        // TODO 勤務時間を休憩時間が超える場合のバリデーションをどこかでやる
        return HourAndMinute.from(normalBreakTime.subtractFrom(workTimeRange.workMinute()));
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
