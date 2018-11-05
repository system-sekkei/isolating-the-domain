package example.application.service;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Service;

/**
 * 勤怠サービス
 */
@Service
public class AttendanceService {
    AttendanceRepository attendanceRepository;

    /**
     * 勤務時間登録
     */
    public void registerWorkTime(UserIdentifier userId, AttendanceOfDay work) {
        attendanceRepository.registerWorkTime(userId, work);
    }

    /**
     * 日次勤務時間取得
     */
    public AttendanceOfDay findBy(UserIdentifier userId, Date workDay) {
        return attendanceRepository.findBy(userId, workDay);
    }

    /**
     * 月次勤務時間取得
     */
    public AttendanceOfMonth findMonthlyWorkTimes(UserIdentifier userId, YearMonth month) {
        return attendanceRepository.findMonthly(userId, month);
    }

    AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
