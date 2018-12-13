package example.domain.model.attendance;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

/**
 * 労働時間
 */
public class WorkTime {

    Minute normalTime;
    Minute midnightWorkTime;
    Minute overWorkTime;

    public WorkTime() {
        this(new Minute(0), new Minute(0), new Minute(0));
    }

    public WorkTime(Minute normalTime, Minute midnightWorkTime, Minute overWorkTime) {
        this.normalTime = normalTime;
        this.midnightWorkTime = midnightWorkTime;
        this.overWorkTime = overWorkTime;
    }

    public WorkTime(Attendance attendance) {
        this(attendance.workTime().toMinute(), attendance.midnightWorkTime().toMinute(), attendance.overTime().toMinute());
    }

    public WorkTime addAttendanceOfDay(Attendance attendance) {
        return add(new WorkTime(attendance));
    }

    public WorkTime add(WorkTime other) {
        return new WorkTime(
                this.normalTime.add(other.normalTime),
                this.midnightWorkTime.add(other.midnightWorkTime),
                this.overWorkTime.add(other.overWorkTime));
    }

    public HourAndMinute totalWorkTime() {
        return HourAndMinute.from(normalTime.add(midnightWorkTime));
    }

    public HourAndMinute normalTime() {
        return HourAndMinute.from(normalTime);
    }

    public HourAndMinute midnightWorkTime() {
        return HourAndMinute.from(midnightWorkTime);
    }

    public HourAndMinute overTime() {
        return HourAndMinute.from(overWorkTime);
    }

}
