package example.domain.model.attendance;

import example.domain.type.date.DateRange;

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

    public boolean inRange(DateRange range) {
        return workDay.value().inRange(range);
    }
}
