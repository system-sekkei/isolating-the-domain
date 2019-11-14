package example.application.coordinator.timerecord;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordQueryService;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import org.springframework.stereotype.Service;

/**
 * 勤務実績登録コーディネーター
 */
@Service
public class TimeRecordCoordinator {
    TimeRecordQueryService timeRecordQueryService;
    AttendanceQueryService attendanceQueryService;
    EmployeeQueryService employeeQueryService;

    public TimeRecordCoordinator(TimeRecordQueryService timeRecordQueryService, AttendanceQueryService attendanceQueryService, EmployeeQueryService employeeQueryService) {
        this.timeRecordQueryService = timeRecordQueryService;
        this.attendanceQueryService = attendanceQueryService;
        this.employeeQueryService = employeeQueryService;
    }

    /**
     * 前の勤務日と勤務時刻が重複していないかどうか
     */
    public boolean isOverlapWithPreviousWorkRange(TimeRecord timeRecord) {
        Employee employee = employeeQueryService.choose(timeRecord.employeeNumber());

        // TODO: 現在、2日以上またいでの勤務は考慮していないのであり得る場合は対応する。
        WorkDate previousDate = new WorkDate(timeRecord.workDate().value().previousDay());

        if (attendanceQueryService.attendanceStatus(employee, previousDate).isWork()) {
            TimeRecord previous = timeRecordQueryService.timeRecord(employee, previousDate);
            return timeRecord.isOverlap(previous);
        }

        return false;
    }

    /**
     * 次の勤務日と勤務時刻が重複していないかどうか
     */
    public boolean isOverlapWithNextWorkRange(TimeRecord timeRecord) {
        Employee employee = employeeQueryService.choose(timeRecord.employeeNumber());

        // TODO: 現在、2日以上またいでの勤務は考慮していないのであり得る場合は対応する。
        WorkDate nextDate = new WorkDate(timeRecord.workDate().value().nextDay());

        if (attendanceQueryService.attendanceStatus(employee, nextDate).isWork()) {
            TimeRecord next = timeRecordQueryService.timeRecord(employee, nextDate);
            return timeRecord.isOverlap(next);
        }

        return false;
    }
}
