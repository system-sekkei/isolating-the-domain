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
        this(date, new HourTime("09:00"), new HourTime("15:00"), new Minute(30));
    }

    public AttendanceOfDay(Date day, HourTime start, HourTime end, Minute breaks) {
        this.date = day;
        this.workTimeRange = new WorkTimeRange(start, end);
        this.breaks = new Break(breaks);
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
}
