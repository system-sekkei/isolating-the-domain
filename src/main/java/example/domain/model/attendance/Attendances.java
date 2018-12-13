package example.domain.model.attendance;

import java.util.List;

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

    public Attendance atWorkDay(WorkDay day) {
        return list.stream().filter(attendance -> attendance.workDay().hasSameValue(day)).findFirst().orElseThrow(RuntimeException::new);
    }

    public WorkTime summarize(){
       return WorkTime.from(list);
    }

}
