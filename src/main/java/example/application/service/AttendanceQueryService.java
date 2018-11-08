package example.application.service;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.worker.WorkerIdentifier;
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
     * 日次勤務時間取得
     */
    public AttendanceOfDay findBy(WorkerIdentifier userId, Date workDay) {
        return attendanceRepository.findBy(userId, workDay);
    }

    /**
     * 月次勤務時間取得
     */
    public AttendanceOfMonth findMonthlyWorkTimes(WorkerIdentifier userId, YearMonth month) {
        return attendanceRepository.findMonthly(userId, month);
    }

    AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
