package example._experimental.spa.controller.timerecord;

import example._experimental.spa.view.timerecord.TimeRecordView;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timerecord")
public class TimeRecordAPI {
    EmployeeQueryService employeeQueryService;
    TimeRecordRecordService timeRecordRecordService;
    TimeRecordQueryService timeRecordQueryService;
    AttendanceQueryService attendanceQueryService;

    public TimeRecordAPI(EmployeeQueryService employeeQueryService, TimeRecordRecordService timeRecordRecordService, TimeRecordQueryService timeRecordQueryService, AttendanceQueryService attendanceQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.timeRecordRecordService = timeRecordRecordService;
        this.timeRecordQueryService = timeRecordQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @GetMapping("/prepare/{employeeNumber}/{workDate}")
    PreparedAttendanceForm prepare(@PathVariable("employeeNumber") EmployeeNumber employeeNumber,
                                   @PathVariable("workDate") WorkDate workDate) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        Employee employee = employeeQueryService.choose(employeeNumber);
        AttendanceStatus attendanceStatus = attendanceQueryService.attendanceStatus(employee, workDate);

        if (!attendanceStatus.isWork()) {
            AttendanceForm form = AttendanceForm.of(employeeNumber, workDate);
            return new PreparedAttendanceForm(contractingEmployees, form);
        }

        TimeRecord timeRecord = timeRecordQueryService.timeRecord(employee, workDate);
        AttendanceForm form = AttendanceForm.of(timeRecord);
        return new PreparedAttendanceForm(contractingEmployees, form);
    }

    @PostMapping
    TimeRecordView post(@Validated @RequestBody AttendanceForm form) {
        TimeRecord timeRecord = form.toTimeRecord();
        timeRecordRecordService.registerTimeRecord(timeRecord);
        return new TimeRecordView(timeRecord);
    }

}
