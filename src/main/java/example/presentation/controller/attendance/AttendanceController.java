package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.workrecord.WorkRecordRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.Attendance;
import example.domain.model.timerecord.WorkMonth;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
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
@RequestMapping("attendances/{workerNumber}/{yearMonth}")
public class AttendanceController {

    WorkerQueryService workerQueryService;
    WorkRecordRecordService workRecordRecordService;
    AttendanceQueryService attendanceQueryService;

    public AttendanceController(WorkerQueryService workerQueryService, WorkRecordRecordService workRecordRecordService, AttendanceQueryService attendanceQueryService) {
        this.workerQueryService = workerQueryService;
        this.workRecordRecordService = workRecordRecordService;
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
                @PathVariable("workerNumber") WorkerNumber workerNumber,
                @PathVariable("yearMonth") WorkMonth workMonth) {
        Worker worker = workerQueryService.choose(workerNumber);
        model.addAttribute("worker", worker);

        Attendance attendance = attendanceQueryService.findAttendance(worker.workerNumber(), workMonth);
        model.addAttribute("attendance", attendance);
        return "attendance/list";
    }
}
