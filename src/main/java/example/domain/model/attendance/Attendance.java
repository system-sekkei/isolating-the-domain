package example.domain.model.attendance;

/**
 * 日次勤怠
 */
public class Attendance {

    WorkDay workDay;
    TimeRecord timeRecord;

    @Deprecated
    Attendance() {
    }

    public Attendance(WorkDay workDay, TimeRecord timeRecord) {
        this.workDay = workDay;
        this.timeRecord = timeRecord;
    }

    public WorkDay workDay() {
        return workDay;
    }

    public TimeRecord timeRecord() {
        return timeRecord;
    }

}
