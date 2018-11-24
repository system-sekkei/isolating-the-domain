package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.ClockTime;
import example.domain.type.time.Minute;

/**
 * 日次勤怠
 */
public class AttendanceOfDay {
    Date date;
    WorkTimeRange workTimeRange;
    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    public AttendanceOfDay() {
        this(Date.now());
    }

    public AttendanceOfDay(Date date) {
        // TODO 休みの扱い
        this(date, new WorkStartTime(new ClockTime("00:00")), new WorkEndTime(new ClockTime("00:00")), new NormalBreakTime(new Minute(0)), new MidnightBreakTime("0"));
    }

    public AttendanceOfDay(Date date, WorkStartTime workStartTime, WorkEndTime workEndTime, NormalBreakTime normalBreakTime, MidnightBreakTime midnightBreakTime) {
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

    public NormalBreakTime normalBreakTime() {
        return normalBreakTime;
    }

    public HourAndMinute workTime() {
        return subtractBreaks(workTimeRange.workTime().toMinute());
    }

    public HourAndMinute overTime() {
        return subtractBreaks(workTimeRange.overWorkTime().toMinute());
    }

    public HourAndMinute midnightWorkTime() {
        return subtractBreaks(workTimeRange.midnightWorkTime().toMinute());
    }

    private HourAndMinute subtractBreaks(Minute minute) {
        ////FIXME 休憩時間の扱い
        if(minute.value() > normalBreakTime.value.quarterHourRoundUp().value()) {
            return HourAndMinute.from(normalBreakTime.subtractFrom(minute));
        } else {
            return HourAndMinute.from(new Minute(0));
        }
    }

    public Attendance attendance() {
        if (workTimeRange.notWork()) {
            return Attendance.休み;
        }
        return Attendance.出勤;
    }
}
