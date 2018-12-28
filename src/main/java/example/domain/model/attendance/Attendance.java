package example.domain.model.attendance;

import example.domain.model.attendance.worktimerecord.WorkTimeRecord;

/**
 * 日次勤怠
 */
public class Attendance {

    WorkDay workDay;
    WorkTimeRecord workTimeRecord;

    @Deprecated
    Attendance() {
    }

    public Attendance(WorkDay workDay, WorkTimeRecord workTimeRecord) {
        this.workDay = workDay;
        this.workTimeRecord = workTimeRecord;
    }

    public WorkDay workDay() {
        return workDay;
    }

    public WorkTimeRecord workTimeRecord() {
        return workTimeRecord;
    }

}
