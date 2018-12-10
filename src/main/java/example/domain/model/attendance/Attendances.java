package example.domain.model.attendance;

import example.domain.type.time.HourAndMinute;

import java.util.List;

/**
 * 月次勤怠
 */
public class Attendances {
    List<Attendance> list;
    WorkTime workTime;

    public Attendances(List<Attendance> list) {
        this.list = list;
        this.workTime = WorkTime.from(list);
    }

    public List<Attendance> list() {
        return list;
    }

    public Attendance get(WorkDay day) {
        return list.stream().filter(attendance -> attendance.workDay().hasSameValue(day)).findFirst().orElseThrow(RuntimeException::new);
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
