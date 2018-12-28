package example.domain.model.attendance;

import example.domain.model.attendance.worktimerecord.WorkTimeRecord;
import example.domain.model.worker.WorkerNumber;

/**
 * 日次勤怠
 */
public class Attendance {

    WorkerNumber workerNumber;
    WorkDate workDate;
    WorkTimeRecord workTimeRecord;

    @Deprecated
    Attendance() {
    }

    public Attendance(WorkerNumber workerNumber, WorkDate workDate, WorkTimeRecord workTimeRecord) {
        this.workerNumber = workerNumber;
        this.workDate = workDate;
        this.workTimeRecord = workTimeRecord;
    }

    public WorkDate workDate() {
        return workDate;
    }

    public WorkTimeRecord workTimeRecord() {
        return workTimeRecord;
    }

    public WorkerNumber workerNumber() {
        return workerNumber;
    }

    public boolean isWorkedAt(WorkDate other) {
        return this.workDate.hasSameValue(other);
    }
}
