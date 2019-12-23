package example.presentation.controller.timerecord;

import example.application.coordinator.timerecord.TimeRecordCoordinator;
import example.application.coordinator.timerecord.TimeRecordQueryCoordinator;
import example.application.service.daysoff.DaysOffRecordService;
import example.application.service.employee.EmployeeQueryService;
import example.application.service.timerecord.TimeRecordRecordService;
import example.domain.model.timerecord.evaluation.*;
import example.domain.validation.BusinessLogic;
import example.domain.validation.Conversion;
import example.domain.validation.Required;
import example.domain.validation.FormatCheck;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.GroupSequence;
import java.time.LocalDate;

/**
 * 勤務時間の登録
 */
@Controller
@RequestMapping("timerecord")
public class TimeRecordRegisterController {

    @GroupSequence({Required.class, FormatCheck.class, Conversion.class, BusinessLogic.class})
    public interface GroupOrder {}

    EmployeeQueryService employeeQueryService;
    TimeRecordRecordService timeRecordRecordService;
    TimeRecordCoordinator timeRecordCoordinator;
    TimeRecordQueryCoordinator timeRecordQueryCoordinator;
    DaysOffRecordService daysOffRecordService;

    public TimeRecordRegisterController(
            EmployeeQueryService employeeQueryService,
            TimeRecordRecordService timeRecordRecordService,
            TimeRecordCoordinator timeRecordCoordinator,
            TimeRecordQueryCoordinator timeRecordQueryCoordinator,
            DaysOffRecordService daysOffRecordService) {
        this.employeeQueryService = employeeQueryService;
        this.timeRecordRecordService = timeRecordRecordService;
        this.timeRecordCoordinator = timeRecordCoordinator;
        this.timeRecordQueryCoordinator = timeRecordQueryCoordinator;
        this.daysOffRecordService = daysOffRecordService;
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
        if (employeeNumber != null && workDate != null) {
            TimeRecord timeRecord = timeRecordQueryCoordinator.timeRecord(employeeNumber, workDate);
            attendanceForm.apply(timeRecord);
        } else {
            TimeRecord timeRecord = timeRecordQueryCoordinator.defaultTimeRecord(new WorkDate(new Date(LocalDate.now())));
            attendanceForm.apply(timeRecord);
        }
        return "timerecord/form";
    }

    @PostMapping
    String register(@Validated(GroupOrder.class) @ModelAttribute("attendanceForm") AttendanceForm attendanceForm,
                    BindingResult result) {
        if (result.hasErrors()) return "timerecord/form";
        TimeRecord timeRecord = attendanceForm.toTimeRecord();

        TimeRecordValidResult valid = timeRecordCoordinator.isValid(timeRecord);
        if (valid.startTimeValidResult().hasError()) {
            result.addError(new FieldError("attendanceForm", "startTime.valid", valid.startTimeValidResult().message()));
        }
        if (valid.endTimeValidResult().hasError()) {
            result.addError(new FieldError("attendanceForm", "endTime.valid", valid.endTimeValidResult().message()));
        }

        if (result.hasErrors()) return "timerecord/form";

        timeRecordRecordService.registerTimeRecord(timeRecord);

        WorkMonth workMonth = WorkMonth.from(timeRecord.workDate());

        return "redirect:/attendances/" + timeRecord.employeeNumber().value() + "/" + workMonth.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "employeeNumber",
                "workDate.value",
                "startTime.hour.value",
                "startTime.minute.value",
                "endTime.hour.value",
                "endTime.minute.value",
                "daytimeBreakTime.value.value",
                "nightBreakTime.value.value",
                "isDaysOff"
        );
    }
}
