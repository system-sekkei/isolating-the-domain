package example.domain.model.attendance;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.time.ClockTime;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 日次勤怠
 */
public class Attendance {
    WorkDay workDay;
    WorkTimeRange workTimeRange;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    public Attendance() {
        this(new WorkDay());
    }

    public Attendance(WorkDay workDay) {
        // TODO 休みの扱い
        this(workDay, new WorkStartTime(new ClockTime("00:00")), new WorkEndTime(new ClockTime("00:00")), new NormalBreakTime(new Minute(0)), new MidnightBreakTime("0"));
    }

    public Attendance(WorkDay workDay, WorkStartTime workStartTime, WorkEndTime workEndTime, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        this.workDay = workDay;
        this.workTimeRange = new WorkTimeRange(workStartTime, workEndTime);
        this.normalBreakTime = normalBreakTime;
        this.midnightBreakTime = midnightBreakTime;
    }

    public WorkDay workDay() {
        return workDay;
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

    public int compareTo(Attendance other) {
        return workDay.compareTo(other.workDay);
    }
}
