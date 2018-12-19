package example.presentation.controller.attendance;

import example.application.service.attendance.AttendanceQueryService;
import example.application.service.attendance.AttendanceRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 勤務時間の一覧
 */
@Controller
@RequestMapping("attendances")
public class AttendanceController {

    WorkerQueryService workerQueryService;
    AttendanceRecordService attendanceRecordService;
    AttendanceQueryService attendanceQueryService;

    public AttendanceController(WorkerQueryService workerQueryService, AttendanceRecordService attendanceRecordService, AttendanceQueryService attendanceQueryService) {
        this.workerQueryService = workerQueryService;
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
    }

    @GetMapping("workers")
    String workers(Model model) {
        ContractingWorkers contractingWorkers = workerQueryService.contractingWorkers();
        model.addAttribute("workers", contractingWorkers);

        // TODO 型にする
        Map<String, MonthlyAttendances> map = new HashMap<>();
        // TODO 入力から
        WorkMonth month = new WorkMonth(2018, 11);
        for (Worker worker : contractingWorkers.list()) {
            MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(worker.workerNumber(), month);
            map.put(worker.workerNumber().toString(), monthlyAttendances);
        }
        model.addAttribute("map", map);

        return "attendance/workers";
    }

    @GetMapping("{workerNumber}/{yearMonth}")
    String list(Model model,
                @PathVariable("workerNumber") WorkerNumber workerNumber,
                @PathVariable("yearMonth") WorkMonth workMonth) {
        Worker worker = workerQueryService.choose(workerNumber);
        model.addAttribute("worker", worker);

        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(worker.workerNumber(), workMonth);
        model.addAttribute("monthlyAttendances", monthlyAttendances);
        return "attendance/list";
    }
}
