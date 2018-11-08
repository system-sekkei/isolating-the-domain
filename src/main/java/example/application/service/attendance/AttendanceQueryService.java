package example.application.service.attendance;

import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

/**
 * 勤務時間参照サービス
 */
@Service
public class AttendanceQueryService {

    AttendanceRepository attendanceRepository;

    /**
     * 月次勤務時間取得
     */
    public MonthlyAttendances findMonthlyAttendances(WorkerIdentifier workerIdentifier, YearMonth month) {
        return attendanceRepository.findMonthly(workerIdentifier, month);
    }

    AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
