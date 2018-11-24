package example.application.service.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.worker.WorkerNumber;
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
    public void registerAttendance(WorkerNumber workerNumber, Attendance attendance) {
        attendanceRepository.registerAttendance(workerNumber, attendance);
    }

    AttendanceRecordService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
