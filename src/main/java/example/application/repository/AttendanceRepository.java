package example.application.repository;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.attendance.WorkerAttendance;
import example.domain.model.worker.WorkerNumber;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {

    void registerAttendance(WorkerAttendance workerAttendance);

    MonthlyAttendances findMonthly(WorkerNumber workerNumber, WorkMonth month);
}
