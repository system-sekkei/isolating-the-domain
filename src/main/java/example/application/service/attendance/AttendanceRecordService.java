package example.application.service.attendance;

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
    public void registerAttendance(WorkerIdentifier workerIdentifier, AttendanceOfDay attendanceOfDay) {
        attendanceRepository.registerAttendance(workerIdentifier, attendanceOfDay);
    }

    AttendanceRecordService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
