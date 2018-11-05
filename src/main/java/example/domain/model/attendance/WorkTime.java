package example.domain.model.attendance;

import example.domain.type.date.DayOfMonth;

/**
 * 勤怠
 */
public class WorkTime {
    DayOfMonth dayOfMonth;
    TimeRecord timeRecord;
    public WorkTime() {}
    public WorkTime(DayOfMonth day, TimeRecord timeRecord) {
        this.dayOfMonth = day;
        this.timeRecord = timeRecord;
    }
}
