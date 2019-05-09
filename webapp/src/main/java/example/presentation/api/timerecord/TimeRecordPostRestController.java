package example.presentation.api.timerecord;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.AttendanceStatus;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 勤務時間の登録API
 */
@RestController
@RequestMapping("api/timerecord")
public class TimeRecordPostRestController {
    EmployeeQueryService employeeQueryService;
    TimeRecordRecordService timeRecordRecordService;
    TimeRecordQueryService timeRecordQueryService;
    AttendanceQueryService attendanceQueryService;

    public TimeRecordPostRestController(EmployeeQueryService employeeQueryService, TimeRecordRecordService timeRecordRecordService, TimeRecordQueryService timeRecordQueryService, AttendanceQueryService attendanceQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.timeRecordRecordService = timeRecordRecordService;
        this.timeRecordQueryService = timeRecordQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @GetMapping("prepare")
    TimeRecordPreparedPostResponse prepareNew() {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        TimeRecordPostRequest prepare = TimeRecordPostRequest.prepare();
        return new TimeRecordPreparedPostResponse(contractingEmployees, prepare);
    }

    @GetMapping("prepare/{employeeNumber}/{workDate}")
    TimeRecordPreparedPostResponse prepareEdit(@PathVariable("employeeNumber") EmployeeNumber employeeNumber,
                                               @PathVariable("workDate") WorkDate workDate) {
        ContractingEmployees contractingEmployees = employeeQueryService.contractingEmployees();
        TimeRecordPostRequest prepare = TimeRecordPostRequest.prepare();

        AttendanceStatus attendanceStatus = attendanceQueryService.attendanceStatus(employeeNumber, workDate);
        if (!attendanceStatus.isWork()) return new TimeRecordPreparedPostResponse(contractingEmployees, prepare);

        TimeRecord timeRecord = timeRecordQueryService.timeRecord(employeeNumber, workDate);
        prepare.apply(timeRecord);
        return new TimeRecordPreparedPostResponse(contractingEmployees, prepare);
    }

    @PostMapping
    TimeRecordPostResponse post(@Validated @ModelAttribute("attendanceForm") TimeRecordPostRequest request,
                                BindingResult result) {
        if (result.hasErrors()) return TimeRecordPostResponse.ng(result);
        TimeRecord timeRecord = request.toAttendance();
        timeRecordRecordService.registerTimeRecord(timeRecord);
        return TimeRecordPostResponse.ok(timeRecord, result);
    }

}
