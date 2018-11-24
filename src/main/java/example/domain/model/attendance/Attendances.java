package example.domain.model.attendance;

import example.domain.type.date.Date;
import example.domain.type.time.HourAndMinute;

import java.util.List;

/**
 * 月次勤怠
 */
public class Attendances {
    List<AttendanceOfDay> list;
    WorkTime workTime;

    public Attendances(List<AttendanceOfDay> list) {
        this.list = list;
        this.workTime = WorkTime.from(list);
    }

    public List<AttendanceOfDay> list() {
        return list;
    }

    public AttendanceOfDay get(Date date) {
        return list.stream().filter(
                w -> w.date().value().equals(date.value())).findFirst().orElseThrow(() -> new RuntimeException());
    }

    public HourAndMinute workTime() {
        return workTime.workTime();
    }

    public HourAndMinute overTime() {
        return workTime.overTime();
    }

    public HourAndMinute midnightWorkTime() {
        return workTime.midnightWorkTime();
    }

    public HourAndMinute totalWorkTime() {
        return workTime.totalWorkTime();
    }
}
