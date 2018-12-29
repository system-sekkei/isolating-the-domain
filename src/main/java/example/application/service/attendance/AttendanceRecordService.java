package example.application.service.attendance;

import example.application.repository.AttendanceRepository;
import example.domain.model.workrecord.WorkRecord;
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
    public void registerAttendance(WorkRecord workRecord) {
        attendanceRepository.registerAttendance(workRecord);
    }

    AttendanceRecordService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
