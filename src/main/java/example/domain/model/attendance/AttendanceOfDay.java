package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.time.HourAndMinute;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;

/**
 * 日次勤怠
 */
public class AttendanceOfDay {
    Date date;
    WorkTimeRange workTimeRange;
    Break breaks;

    public AttendanceOfDay() {
        this(Date.now());
    }

    public AttendanceOfDay(Date date) {
        this(date, new WorkStartTime(new HourTime("00:00")), new WorkEndTime(new HourTime("00:00")), new Break(new Minute(0)));
    }

    public AttendanceOfDay(Date date, WorkStartTime workStartTime, WorkEndTime workEndTime, Break breaks) {
        this.date = date;
        this.workTimeRange = new WorkTimeRange(workStartTime, workEndTime);
        this.breaks = breaks;
    }

    public Date date() {
        return date;
    }

    public WorkTimeRange workTimeRange() {
        return workTimeRange;
    }

    public Break breaks() {
        return breaks;
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
        if(minute.value() > breaks.normalizeValue().value()) {
            return HourAndMinute.from(breaks.subtractFrom(minute));
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
