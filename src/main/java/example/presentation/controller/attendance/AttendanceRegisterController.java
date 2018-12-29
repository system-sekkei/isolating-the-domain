package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkDate;
import example.domain.model.workrecord.WorkMonth;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 勤務時間の登録
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
    String init(@RequestParam(value = "workerNumber", required = false) WorkerNumber workerNumber, @RequestParam(value = "workDate", required = false) WorkDate workDate, @ModelAttribute AttendanceForm attendanceForm) {
        if (workerNumber != null) {
            attendanceForm.workerNumber = workerNumber;
        }
        if (workDate != null) {
            attendanceForm.workDate = workDate.toString();
        }
        if (workerNumber != null && workDate != null) {
            if (attendanceQueryService.attendanceStatus(workerNumber, workDate).isWork()) {
                WorkRecord workRecord = attendanceQueryService.attendance(workerNumber, workDate);
                attendanceForm.apply(workRecord);
            }
        }
        return "attendance/form";
    }

    @PostMapping
    String register(@Validated @ModelAttribute("attendanceForm") AttendanceForm attendanceForm,
                    BindingResult result) {
        if (result.hasErrors()) return "attendance/form";
        WorkRecord workRecord = attendanceForm.toAttendance();

        attendanceRecordService.registerAttendance(workRecord);

        WorkMonth workMonth = workRecord.workDate().month();

        return "redirect:/attendances/" + attendanceForm.workerNumber.value() + "/" + workMonth.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "workerNumber",
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
