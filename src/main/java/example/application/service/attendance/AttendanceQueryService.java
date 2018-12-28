package example.application.service.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Service;

/**
 * 勤務時間参照サービス
 */
@Service
public class AttendanceQueryService {

    AttendanceRepository attendanceRepository;

    /**
     * 月次勤怠取得
     */
    public MonthlyAttendances findMonthlyAttendances(WorkerNumber workerNumber, WorkMonth month) {
        return attendanceRepository.findMonthly(workerNumber, month);
    }

    /**
     * 日次勤怠取得
     */
    public Attendance attendance(WorkerNumber workerNumber, WorkDay workDay) {
        return findMonthlyAttendances(workerNumber, workDay.month()).at(workDay);
    }

    /**
     * 出勤状況取得
     */
    public AttendanceStatus attendanceStatus(WorkerNumber workerNumber, WorkDay workDay) {
        return findMonthlyAttendances(workerNumber, workDay.month()).statusOf(workDay);
    }

    AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
