package example.domain.model.timerecord;

import example.domain.model.worker.WorkerNumber;

/**
 * 勤務実績
 */
public class WorkRecord {

    WorkerNumber workerNumber;
    WorkDate workDate;
    WorkTimeRecord workTimeRecord;

    @Deprecated
    WorkRecord() {
    }

    public WorkRecord(WorkerNumber workerNumber, WorkDate workDate, WorkTimeRecord workTimeRecord) {
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
