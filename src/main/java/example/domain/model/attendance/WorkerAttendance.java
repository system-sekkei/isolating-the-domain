package example.domain.model.attendance;

import example.domain.model.worker.WorkerNumber;

/**
 * 従業員勤怠
 */
public class WorkerAttendance {

    WorkerNumber workerNumber;
    Attendance attendance;

    public WorkerAttendance(WorkerNumber workerNumber, Attendance attendance) {
        this.workerNumber = workerNumber;
        this.attendance = attendance;
    }

    public WorkerNumber workerNumber() {
        return workerNumber;
    }

    public Attendance attendance() {
        return attendance;
    }
}
