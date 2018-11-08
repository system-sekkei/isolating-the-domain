package example.presentation.controller.attendance;

import example.application.service.AttendanceRecordService;
import example.application.service.AttendanceQueryService;
import example.application.service.WorkerService;
import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.Date;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

/**
 * 勤怠コントローラー
 */
@Controller
@RequestMapping("attendance")
public class AttendanceController {

    WorkerService workerService;
    AttendanceRecordService attendanceRecordService;
    AttendanceQueryService attendanceQueryService;

    public AttendanceController(WorkerService workerService, AttendanceRecordService attendanceRecordService, AttendanceQueryService attendanceQueryService) {
        this.workerService = workerService;
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @GetMapping
    String init(Model model) {
        model.addAttribute("attendanceOfDay", new AttendanceOfDay());
        return "attendance/form";
    }

    @PostMapping
    String register() {
        // TODO validation

        attendanceRecordService.registerWorkTime(
                // TODO 入力から
                new WorkerIdentifier("1"),
                new AttendanceOfDay(
                        new Date(LocalDate.now()),
                        new HourTime("09:00"),
                        new HourTime("18:00"),
                        new Minute(90)
                )
        );
        return "redirect:/";
    }

    @GetMapping("{userId}/list")
    String list(Model model, @PathVariable("userId") WorkerIdentifier workerIdentifier) {
        Worker worker = workerService.findById(workerIdentifier);
        model.addAttribute("worker", worker);

        AttendanceOfMonth attendanceOfMonth = attendanceQueryService.findMonthlyWorkTimes(worker.identifier(), Date.now().yearMonth());
        model.addAttribute("attendanceOfMonth", attendanceOfMonth);
        return "attendance/list";
    }
}
