package example.domain.model.attendance;

import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.YearMonth;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {

    void registerAttendance(WorkerNumber workerNumber, AttendanceOfDay attendanceOfDay);

    MonthlyAttendances findMonthly(WorkerNumber workerNumber, YearMonth month);
}
