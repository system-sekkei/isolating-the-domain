package example.domain.model.attendance;

import java.util.List;

/**
 * 勤怠情報
 */
public class Attendances{
    List<Attendance> list;

    public Attendances(List<Attendance> list) {
        this.list = list;
    }

    public List<Attendance> list() {
        return list;
    }

    public boolean isWorked(WorkDay day) {
        return list.stream().anyMatch(attendance -> attendance.workDay().hasSameValue(day));
    }

    public boolean notWorked(WorkDay day) {
        return !isWorked(day);
    }

    public WorkTime summarize(){
        return list.stream()
                .reduce(new WorkTime(),
                        WorkTime::addAttendanceOfDay,
                        WorkTime::add
                );
    }
}
