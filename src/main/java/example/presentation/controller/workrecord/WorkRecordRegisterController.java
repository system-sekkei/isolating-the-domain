package example.presentation.controller.workrecord;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.workrecord.WorkRecordQueryService;
import example.application.service.workrecord.WorkRecordRecordService;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 勤務時間の登録
 */
@Controller
@RequestMapping("workrecord")
public class WorkRecordRegisterController {

    EmployeeQueryService employeeQueryService;
    WorkRecordRecordService workRecordRecordService;
    WorkRecordQueryService workRecordQueryService;
    AttendanceQueryService attendanceQueryService;

    public WorkRecordRegisterController(EmployeeQueryService employeeQueryService, WorkRecordRecordService workRecordRecordService, WorkRecordQueryService workRecordQueryService, AttendanceQueryService attendanceQueryService) {
        this.employeeQueryService = employeeQueryService;
        this.workRecordRecordService = workRecordRecordService;
        this.workRecordQueryService = workRecordQueryService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @ModelAttribute("employees")
    ContractingEmployees employees() {
        return employeeQueryService.contractingEmployees();
    }

    @ModelAttribute("attendanceForm")
    AttendanceForm attendanceForm() {
        AttendanceForm attendanceForm = new AttendanceForm();
        return attendanceForm;
    }

    @GetMapping
    String init(@RequestParam(value = "employeeNumber", required = false) EmployeeNumber employeeNumber,
                @RequestParam(value = "workDate", required = false) WorkDate workDate,
                @ModelAttribute AttendanceForm attendanceForm,
                Model model) {
        if (employeeNumber != null) {
            attendanceForm.employeeNumber = employeeNumber;
        }
        if (workDate != null) {
            attendanceForm.workDate = workDate.toString();
        }
        if (employeeNumber != null && workDate != null) {
            if (attendanceQueryService.attendanceStatus(employeeNumber, workDate).isWork()) {
                TimeRecord timeRecord = workRecordQueryService.workRecord(employeeNumber, workDate);
                attendanceForm.apply(timeRecord);
            }
        }
        return "workrecord/form";
    }

    @PostMapping
    String register(@Validated @ModelAttribute("attendanceForm") AttendanceForm attendanceForm,
                    BindingResult result) {
        if (result.hasErrors()) return "workrecord/form";
        TimeRecord timeRecord = attendanceForm.toAttendance();

        workRecordRecordService.registerWorkRecord(timeRecord);

        WorkMonth workMonth = WorkMonth.from(timeRecord.workDate());

        return "redirect:/attendances/" + attendanceForm.employeeNumber.value() + "/" + workMonth.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "employeeNumber",
                "workDate",
                "startHour",
                "startMinute",
                "endHour",
                "endMinute",
                "daytimeBreakTime",
                "midnightBreakTime"
        );
    }
}
