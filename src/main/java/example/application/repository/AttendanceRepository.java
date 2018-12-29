package example.application.repository;

import example.domain.model.workrecord.WorkRecord;
import example.domain.model.attendance.Attendance;
import example.domain.model.workrecord.WorkMonth;
import example.domain.model.worker.WorkerNumber;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {

    void registerAttendance(WorkRecord workRecord);

    Attendance findMonthly(WorkerNumber workerNumber, WorkMonth month);
}
