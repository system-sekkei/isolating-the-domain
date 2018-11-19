package example.application.service.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.Attendances;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
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
    public MonthlyAttendances findMonthlyAttendances(WorkerNumber workerNumber, YearMonth month) {
        return attendanceRepository.findMonthly(workerNumber, month);
    }

    /**
     * 勤務時間取得
     */
    public Attendances getAttendances(WorkerNumber workerNumber, Date startDate, Date endDate) {
        return attendanceRepository.getAttendances(workerNumber, startDate, endDate);
    }


    AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
