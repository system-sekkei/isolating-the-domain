package example.presentation.api.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 勤務時間一覧の取得API
 */
@RestController
@RequestMapping("api/attendances/{employeeNumber}/{yearMonth}")
public class AttendanceGetRestController {

    EmployeeQueryService employeeQueryService;
    TimeRecordRecordService timeRecordRecordService;
    AttendanceQueryService attendanceQueryService;

    public AttendanceGetRestController(EmployeeQueryService employeeQueryService, TimeRecordRecordService timeRecordRecordService, AttendanceQueryService attendanceQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.timeRecordRecordService = timeRecordRecordService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @GetMapping
    AttendanceGetResponse get(@PathVariable("employeeNumber") EmployeeNumber employeeNumber,
                              @PathVariable("yearMonth") WorkMonth workMonth) {
        Employee employee = employeeQueryService.choose(employeeNumber);
        Attendance attendance = attendanceQueryService.findAttendance(employee.employeeNumber(), workMonth);
        return new AttendanceGetResponse(employee, attendance);
    }
}
