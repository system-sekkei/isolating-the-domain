package example.domain.model.attendance;

/**
 * 月次勤怠
 */
public class MonthlyAttendances {

    WorkMonth month;
    Attendances attendances;

    public MonthlyAttendances(WorkMonth month, Attendances attendances) {
        this.month = month;
        this.attendances = attendances;
    }

    public WorkMonth month (){
        return month;
    }

    public Attendances attendances(){
        return attendances;
    }

    public Attendance attendanceOf(WorkDay day) {
        return attendances.atWorkDay(day);
    }
}
