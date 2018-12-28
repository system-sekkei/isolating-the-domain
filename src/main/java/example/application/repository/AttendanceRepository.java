package example.application.repository;

import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.worker.WorkerNumber;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {

    void registerAttendance(Attendance attendance);

    MonthlyAttendances findMonthly(WorkerNumber workerNumber, WorkMonth month);
}
