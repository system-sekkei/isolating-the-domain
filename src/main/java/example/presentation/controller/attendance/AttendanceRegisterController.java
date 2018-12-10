package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.Attendance;
import example.domain.model.attendance.WorkEndTime;
import example.domain.model.attendance.WorkStartTime;
import example.domain.model.worker.ContractingWorkers;
import example.domain.type.time.ClockTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    String init(Model model) {
        return "attendance/form";
    }

    @PostMapping
    String register(@ModelAttribute("attendanceForm") AttendanceForm attendanceForm) {
        // TODO validation

        attendanceRecordService.registerAttendance(
                attendanceForm.workerNumber,
                new Attendance(
                        attendanceForm.date,
                        new WorkStartTime(new ClockTime(attendanceForm.startHour, attendanceForm.startMinute)),
                        new WorkEndTime(new ClockTime(attendanceForm.endHour, attendanceForm.endMinute)),
                        attendanceForm.normalBreakTime,
                        attendanceForm.midnightBreakTime
                )
        );

        return "redirect:/attendance/" + attendanceForm.workerNumber.value() + "/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "workerNumber",
                "date.value",
                "startHour",
                "startMinute",
                "endHour",
                "endMinute",
                "normalBreakTime",
                "midnightBreakTime"
        );
    }
}
