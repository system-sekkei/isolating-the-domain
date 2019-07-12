package example.api.controller.timerecord;

import example.api.view.timerecord.PreparedAttendanceFormView;
import example.api.view.timerecord.TimeRecordView;
import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.presentation.controller.timerecord.AttendanceForm;
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
    PreparedAttendanceFormView prepare(@PathVariable("employeeNumber") EmployeeNumber employeeNumber,
                                       @PathVariable("workDate") WorkDate workDate) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        AttendanceStatus attendanceStatus = attendanceQueryService.attendanceStatus(employeeNumber, workDate);

        if (!attendanceStatus.isWork()) {
            AttendanceForm form = AttendanceForm.of(employeeNumber, workDate);
            return new PreparedAttendanceFormView(contractingEmployees, form);
        }

        TimeRecord timeRecord = timeRecordQueryService.timeRecord(employeeNumber, workDate);
        AttendanceForm form = AttendanceForm.of(timeRecord);
        return new PreparedAttendanceFormView(contractingEmployees, form);
    }

    @PostMapping
    TimeRecordView post(@Validated @RequestBody AttendanceForm form) {
        TimeRecord timeRecord = form.toAttendance();
        timeRecordRecordService.registerTimeRecord(timeRecord);
        return new TimeRecordView(timeRecord);
    }

}
