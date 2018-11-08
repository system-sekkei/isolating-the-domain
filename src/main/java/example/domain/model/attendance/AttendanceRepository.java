package example.domain.model.attendance;

import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;

/**
 * 勤怠リポジトリ
 */
public interface AttendanceRepository {
    void registerWorkTime(WorkerIdentifier userId, AttendanceOfDay work);
    AttendanceOfDay findBy(WorkerIdentifier userId, Date workDay);

    AttendanceOfMonth findMonthly(WorkerIdentifier userId, YearMonth month);
}
