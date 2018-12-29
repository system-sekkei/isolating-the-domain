package example.application.service.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.model.workrecord.WorkDate;
import example.domain.model.workrecord.WorkMonth;
import example.domain.model.workrecord.WorkRecord;
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
    public Attendance findMonthlyAttendances(WorkerNumber workerNumber, WorkMonth month) {
        return attendanceRepository.findMonthly(workerNumber, month);
    }

    /**
     * 日次勤怠取得
     */
    public WorkRecord attendance(WorkerNumber workerNumber, WorkDate workDate) {
        return findMonthlyAttendances(workerNumber, workDate.month()).at(workDate);
    }

    /**
     * 出勤状況取得
     */
    public AttendanceStatus attendanceStatus(WorkerNumber workerNumber, WorkDate workDate) {
        return findMonthlyAttendances(workerNumber, workDate.month()).statusOf(workDate);
    }

    AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
