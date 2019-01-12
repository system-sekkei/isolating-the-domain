package example.application.service.attendance;

import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.employee.EmployeeNumber;
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
    public Attendance findAttendance(EmployeeNumber employeeNumber, WorkMonth month) {
        TimeRecords timeRecords = timeRecordRepository.findTimeRecords(employeeNumber, month);
        return new Attendance(employeeNumber, month, timeRecords);
    }

    /**
     * 勤怠状況取得
     */
    public AttendanceStatus attendanceStatus(EmployeeNumber employeeNumber, WorkDate workDate) {
        return findAttendance(employeeNumber, WorkMonth.from(workDate)).statusOf(workDate);
    }

    AttendanceQueryService(TimeRecordRepository timeRecordRepository) {
        this.timeRecordRepository = timeRecordRepository;
    }
}
