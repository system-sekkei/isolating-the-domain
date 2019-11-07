package example.application.service.attendance;

import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.WorkDate;
import org.springframework.stereotype.Service;

/**
 * 勤務時間参照サービス
 */
@Service
public class AttendanceQueryService {

    TimeRecordRepository timeRecordRepository;

    /**
     * 月次勤怠取得
     */
    public Attendance findAttendance(Employee employee, WorkMonth month) {
        TimeRecords timeRecords = timeRecordRepository.findTimeRecords(employee, month);
        return new Attendance(month, timeRecords);
    }

    /**
     * 勤怠状況取得
     */
    public AttendanceStatus attendanceStatus(Employee employee, WorkDate workDate) {
        return findAttendance(employee, WorkMonth.from(workDate)).statusOf(workDate);
    }

    AttendanceQueryService(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }
}
