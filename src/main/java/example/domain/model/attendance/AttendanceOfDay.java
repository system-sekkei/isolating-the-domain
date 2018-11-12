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
        Minute workMinute = workTimeRange.workMinute();
        Minute workingMinute = breaks.subtractFrom(workMinute);
        return HourAndMinute.from(workingMinute);
    }

    public Attendance attendance() {
        if (workTimeRange.notWork()) {
            return Attendance.休み;
        }
        return Attendance.出勤;
    }
}
