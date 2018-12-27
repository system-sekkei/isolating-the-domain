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

    public WorkDay workDay() {
        return workDay;
    }

    public TimeRecord timeRecord() {
        return timeRecord;
    }

    public HourAndMinute totalBreakTime() {
        return timeRecord.totalBreakTime();
    }

    public HourAndMinute totalWorkTime() {
        return timeRecord.totalWorkTime();
    }

    public HourAndMinute workTime() {
        return timeRecord.workTime();
    }

    public HourAndMinute midnightWorkTime() {
        return timeRecord.midnightWorkTime();
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
