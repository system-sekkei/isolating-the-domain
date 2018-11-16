package example.domain.model.attendance;

import example.domain.model.labour_standards_law.Midnight;
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

    public HourAndMinute midnightWorkTime() {
        WorkTimeRange midnightRange = WorkTimeRange.of(workTimeRange.toTimeRange().intersect(new Midnight().range()));
        Minute midnightWorkTime = midnightRange.workMinute();
        //FIXME 休憩時間を深夜時間帯から引いてるのはいずれ直さなきゃ
        if(midnightWorkTime.value() > breaks.value.value()) {
            return HourAndMinute.from(breaks.subtractFrom(midnightWorkTime));
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
