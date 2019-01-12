package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 勤務時間の一覧
 */
@Controller
@RequestMapping("attendances/{employeeNumber}/{yearMonth}")
public class AttendanceController {

    EmployeeQueryService employeeQueryService;
    TimeRecordRecordService timeRecordRecordService;
    AttendanceQueryService attendanceQueryService;

    public AttendanceController(EmployeeQueryService employeeQueryService, TimeRecordRecordService timeRecordRecordService, AttendanceQueryService attendanceQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.timeRecordRecordService = timeRecordRecordService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @ModelAttribute("beforeMonth")
    String beforeMonth(@PathVariable(value = "yearMonth") WorkMonth month) {
        return month.before().toString();
    }

    @ModelAttribute("afterMonth")
    String afterMonth(@PathVariable(value = "yearMonth") WorkMonth month) {
        return month.after().toString();
    }

    @GetMapping
    String list(Model model,
                @PathVariable("employeeNumber") EmployeeNumber employeeNumber,
                @PathVariable("yearMonth") WorkMonth workMonth) {
        Employee employee = employeeQueryService.choose(employeeNumber);
        model.addAttribute("employee", employee);

        Attendance attendance = attendanceQueryService.findAttendance(employee.employeeNumber(), workMonth);
        model.addAttribute("attendance", attendance);
        return "attendance/list";
    }
}
