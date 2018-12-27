package example.domain.model.attendance;

import example.domain.model.labour_standards_law.DailyOvertimeWork;
import example.domain.type.date.DateRange;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 日次勤怠
 */
public class Attendance {

    WorkDay workDay;
    TimeRecord timeRecord;

    @Deprecated
    Attendance() {
    }

    public Attendance(WorkDay workDay, TimeRecord timeRecord) {
        this.workDay = workDay;
        this.timeRecord = timeRecord;
    }

    public Attendance(WorkDay workDay, WorkStartTime workStartTime, WorkEndTime workEndTime, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
        this.workDay = workDay;
    }

    public WorkDay workDay() {
        return workDay;
    }

    public TimeRecord timeRecord() {
        return timeRecord;
    }

    public HourAndMinute totalBreakTime() {
        return HourAndMinute.from(normalBreakTime.toMinute().add(midnightBreakTime.toMinute()));
    }

    public HourAndMinute totalWorkTime() {
        return HourAndMinute.from(workTime().toMinute().add(midnightWorkTime().toMinute()));
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

    public boolean inRange(DateRange range) {
        return workDay.value().inRange(range);
    }
}
