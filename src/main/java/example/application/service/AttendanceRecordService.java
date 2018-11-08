package example.application.service;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.worker.WorkerIdentifier;
import org.springframework.stereotype.Service;

/**
 * 勤務時間記録サービス
 */
@Service
public class AttendanceRecordService {
    AttendanceRepository attendanceRepository;

    /**
     * 勤務時間登録
     */
    public void registerWorkTime(WorkerIdentifier userId, AttendanceOfDay work) {
        attendanceRepository.registerWorkTime(userId, work);
    }

    AttendanceRecordService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
