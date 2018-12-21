package example.domain.model.attendance;

import java.util.List;
import java.util.Optional;

/**
 * 勤怠情報
 */
public class Attendances {
    List<Attendance> list;

    public Attendances(List<Attendance> list) {
        this.list = list;
    }

    public List<Attendance> list() {
        return list;
    }

    public WorkTime summarize() {
        return list.stream()
                .reduce(new WorkTime(),
                        WorkTime::addAttendanceOfDay,
                        WorkTime::add
                );
    }

    Attendance at(WorkDay day) {
        Optional<Attendance> attendance = list.stream()
                .filter(worked -> worked.workDay().hasSameValue(day))
                .findFirst();
        return attendance.orElse(new Attendance(day));
    }
}
