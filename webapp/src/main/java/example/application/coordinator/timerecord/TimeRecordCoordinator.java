package example.application.coordinator.timerecord;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordQueryService;
import example.domain.model.employee.Employee;
import example.domain.model.timerecord.evaluation.*;
import example.domain.type.date.Date;
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

    public TimeRecordValidResult isValid(TimeRecord timeRecord) {
        StartTimeValidResult startTimeValidResult = checkOverlapWithPreviousWorkRange(timeRecord);
        EndTimeValidResult endTimeValidResult = checkOverlapWithNextWorkRange(timeRecord);

        return new TimeRecordValidResult(startTimeValidResult, endTimeValidResult);
    }

    /**
     * 前の勤務日と勤務開始時刻が重複していないかどうか
     */
    StartTimeValidResult checkOverlapWithPreviousWorkRange(TimeRecord timeRecord) {
        Employee employee = employeeQueryService.choose(timeRecord.employeeNumber());

        WorkDate previousDate = new WorkDate(new Date(timeRecord.workDate().value().value.minusDays(1)));

        if (attendanceQueryService.attendanceStatus(employee, previousDate).isWork()) {
            TimeRecord previous = timeRecordQueryService.timeRecord(employee, previousDate);
            if (timeRecord.isOverlap(previous)) {
                return StartTimeValidResult.前日の勤務時刻と重複;
            }
        }

        return StartTimeValidResult.正常;
    }

    /**
     * 次の勤務日と勤務終了時刻が重複していないかどうか
     */
    EndTimeValidResult checkOverlapWithNextWorkRange(TimeRecord timeRecord) {
        Employee employee = employeeQueryService.choose(timeRecord.employeeNumber());

        WorkDate nextDate = new WorkDate(new Date(timeRecord.workDate().value().value.plusDays(1)));

        if (attendanceQueryService.attendanceStatus(employee, nextDate).isWork()) {
            TimeRecord next = timeRecordQueryService.timeRecord(employee, nextDate);
            if (timeRecord.isOverlap(next)) {
                return EndTimeValidResult.翌日の勤務時刻と重複;
            }
        }

        return EndTimeValidResult.正常;
    }
}
