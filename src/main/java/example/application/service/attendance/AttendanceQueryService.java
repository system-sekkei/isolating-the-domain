package example.application.service.attendance;

import example.application.repository.WorkRecordRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.worker.WorkerNumber;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.timerecord.WorkMonth;
import example.domain.model.timerecord.WorkRecords;
import org.springframework.stereotype.Service;

/**
 * 勤務時間参照サービス
 */
@Service
public class AttendanceQueryService {

    WorkRecordRepository workRecordRepository;

    /**
     * 月次勤怠取得
     */
    public Attendance findAttendance(WorkerNumber workerNumber, WorkMonth month) {
        WorkRecords workRecords = workRecordRepository.findWorkRecords(workerNumber, month);
        return new Attendance(workerNumber, month, workRecords);
    }

    /**
     * 勤怠状況取得
     */
    public AttendanceStatus attendanceStatus(WorkerNumber workerNumber, WorkDate workDate) {
        return findAttendance(workerNumber, workDate.month()).statusOf(workDate);
    }

    AttendanceQueryService(WorkRecordRepository workRecordRepository) {
        this.workRecordRepository = workRecordRepository;
    }
}
