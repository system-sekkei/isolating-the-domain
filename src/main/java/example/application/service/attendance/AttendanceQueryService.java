package example.application.service.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.worker.WorkerNumber;
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
    public MonthlyAttendances findMonthlyAttendances(WorkerNumber workerNumber, WorkMonth month) {
        return attendanceRepository.findMonthly(workerNumber, month);
    }

    AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
