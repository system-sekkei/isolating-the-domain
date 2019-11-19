package example.application.coordinator.timerecord;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordQueryService;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<TimeRecordValidError> isValid(TimeRecord timeRecord) {
        List<TimeRecordValidError> result = new ArrayList<>();
        if (isOverlapWithPreviousWorkRange(timeRecord)) {
            result.add(TimeRecordValidError.前日の勤務時刻と重複);
        }

        if (isOverlapWithNextWorkRange(timeRecord)) {
            result.add(TimeRecordValidError.翌日の勤務時刻と重複);
        }

        return result;
    }

    /**
     * 前の勤務日と勤務時刻が重複していないかどうか
     */
    private boolean isOverlapWithPreviousWorkRange(TimeRecord timeRecord) {
        Employee employee = employeeQueryService.choose(timeRecord.employeeNumber());

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
    private boolean isOverlapWithNextWorkRange(TimeRecord timeRecord) {
        Employee employee = employeeQueryService.choose(timeRecord.employeeNumber());

        WorkDate nextDate = new WorkDate(timeRecord.workDate().value().nextDay());

        if (attendanceQueryService.attendanceStatus(employee, nextDate).isWork()) {
            TimeRecord next = timeRecordQueryService.timeRecord(employee, nextDate);
            return timeRecord.isOverlap(next);
        }

        return false;
    }
}
