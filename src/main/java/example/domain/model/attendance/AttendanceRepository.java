package example.domain.model.attendance;

import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.YearMonth;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {

    void registerAttendance(WorkerIdentifier workerIdentifier, AttendanceOfDay attendanceOfDay);

    MonthlyAttendances findMonthly(WorkerIdentifier workerIdentifier, YearMonth month);
}
