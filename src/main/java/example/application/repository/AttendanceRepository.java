package example.application.repository;

import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.Attendances;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {

    void registerAttendance(WorkerNumber workerNumber, Attendance attendance);

    MonthlyAttendances findMonthly(WorkerNumber workerNumber, WorkMonth month);

    Attendances getAttendances(WorkerNumber workerNumber, Date startDate, Date endDate);
}
