package example.domain.model.timerecord;

import example.domain.model.worker.WorkerNumber;

/**
 * 勤務実績
 */
public class TimeRecord {

    WorkerNumber workerNumber;
    WorkDate workDate;
    ActualWorkTime actualWorkTime;

    @Deprecated
    TimeRecord() {
    }

    public TimeRecord(WorkerNumber workerNumber, WorkDate workDate, ActualWorkTime actualWorkTime) {
        this.workerNumber = workerNumber;
        this.workDate = workDate;
        this.actualWorkTime = actualWorkTime;
    }

    public WorkDate workDate() {
        return workDate;
    }

    public ActualWorkTime actualWorkTime() {
        return actualWorkTime;
    }

    public WorkerNumber workerNumber() {
        return workerNumber;
    }

    public boolean isWorkedAt(WorkDate other) {
        return this.workDate.hasSameValue(other);
    }
}
