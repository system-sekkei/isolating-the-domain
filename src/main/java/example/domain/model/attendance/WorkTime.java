package example.domain.model.attendance;

import example.domain.type.time.HourAndMinute;
import example.domain.type.time.Minute;

import java.util.List;

/**
 * 労働時間
 */
public class WorkTime {

    Minute workTime;
    Minute midnightWorkTime;
    Minute overWorkTime;

    public WorkTime() {
        this(new Minute(0), new Minute(0), new Minute(0));
    }

    public WorkTime(Minute workTime, Minute midnightWorkTime, Minute overWorkTime) {
        this.workTime = workTime;
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
                this.workTime.add(other.workTime),
                this.midnightWorkTime.add(other.midnightWorkTime),
                this.overWorkTime.add(other.overWorkTime));
    }

    public HourAndMinute totalWorkTime() {
        return HourAndMinute.from(workTime.add(midnightWorkTime));
    }

    public HourAndMinute workTime() {
        return HourAndMinute.from(workTime);
    }

    public HourAndMinute midnightWorkTime() {
        return HourAndMinute.from(midnightWorkTime);
    }

    public HourAndMinute overTime() {
        return HourAndMinute.from(overWorkTime);
    }

    static WorkTime from(List<Attendance> list) {
        return list.stream()
                .reduce(new WorkTime(),
                        (workTime, attendanceOfDay) -> workTime.addAttendanceOfDay(attendanceOfDay),
                        WorkTime::add
                );
    }
}
