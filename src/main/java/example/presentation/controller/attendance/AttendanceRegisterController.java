package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    String init(Model model) {
        model.addAttribute("attendanceOfDay", new AttendanceOfDay());
        return "attendance/form";
    }

    @PostMapping
    String register(@RequestParam("workerNumber") WorkerNumber workerNumber,
                    @RequestParam("date") Date date) {
        // TODO validation

        attendanceRecordService.registerAttendance(
                workerNumber,
                new AttendanceOfDay(
                        date,
                        // TODO 入力から
                        new HourTime("09:00"),
                        new HourTime("18:00"),
                        new Minute(90)
                )
        );

        return "redirect:/attendance/" + workerNumber.value() + "/list";
    }
}
