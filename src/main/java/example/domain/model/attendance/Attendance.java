package example.domain.model.attendance;

import example.domain.model.attendance.worktimerecord.WorkTimeRecord;
import example.domain.model.worker.WorkerNumber;

/**
 * 日次勤怠
 */
public class Attendance {

    WorkerNumber workerNumber;
    WorkDay workDay;
    WorkTimeRecord workTimeRecord;

    @Deprecated
    Attendance() {
    }

    public Attendance(WorkerNumber workerNumber, WorkDay workDay, WorkTimeRecord workTimeRecord) {
        this.workerNumber = workerNumber;
        this.workDay = workDay;
        this.workTimeRecord = workTimeRecord;
    }

    public WorkDay workDay() {
        return workDay;
    }

    public WorkTimeRecord workTimeRecord() {
        return workTimeRecord;
    }

    public WorkerNumber workerNumber() {
        return workerNumber;
    }
}
