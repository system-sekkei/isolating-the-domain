package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkDay;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.attendance.WorkerAttendance;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 勤怠コントローラー
 */
@Controller
@RequestMapping("attendance")
public class AttendanceRegisterController {

    WorkerQueryService workerQueryService;
    AttendanceRecordService attendanceRecordService;
    AttendanceQueryService attendanceQueryService;

    public AttendanceRegisterController(WorkerQueryService workerQueryService, AttendanceRecordService attendanceRecordService, AttendanceQueryService attendanceQueryService) {
        this.workerQueryService = workerQueryService;
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @ModelAttribute("workers")
    ContractingWorkers workers() {
        return workerQueryService.contractingWorkers();
    }

    @ModelAttribute("attendanceForm")
    AttendanceForm attendanceForm() {
        AttendanceForm attendanceForm = new AttendanceForm();
        return attendanceForm;
    }

    @GetMapping
    String init(@RequestParam(value = "workerNumber", required = false) WorkerNumber workerNumber, @RequestParam(value = "workDay", required = false) WorkDay workDay, @ModelAttribute AttendanceForm attendanceForm) {
        if (workerNumber != null) {
            attendanceForm.workerNumber = workerNumber;
        }
        if (workDay != null) {
            attendanceForm.workDay = workDay.toString();
        }
        if (workerNumber != null && workDay != null) {
            if (attendanceQueryService.attendanceStatus(workerNumber, workDay).isWork()) {
                WorkerAttendance attendance = attendanceQueryService.getWorkerAttendance(workerNumber, workDay);
                attendanceForm.apply(attendance);
            }
        }
        return "attendance/form";
    }

    @PostMapping
    String register(@Validated @ModelAttribute("attendanceForm") AttendanceForm attendanceForm,
                    BindingResult result) {
        if (result.hasErrors()) return "attendance/form";
        Attendance attendance = attendanceForm.toAttendance();

        attendanceRecordService.registerAttendance(attendanceForm.workerNumber, attendance);

        WorkMonth workMonth = attendance.workDay().month();

        return "redirect:/attendances/" + attendanceForm.workerNumber.value() + "/" + workMonth.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "workerNumber",
                "workDay",
                "startHour",
                "startMinute",
                "endHour",
                "endMinute",
                "normalBreakTime",
                "midnightBreakTime"
        );
    }
}
