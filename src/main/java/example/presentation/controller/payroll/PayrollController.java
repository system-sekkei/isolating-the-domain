package example.presentation.controller.payroll;

import example.application.service.payroll.PayrollQueryService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.attendance.WorkMonth;
import example.domain.model.payroll.Payroll;
import example.domain.model.worker.ContractingWorkers;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.YearMonth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 給与コントローラー
 */
@Controller
@RequestMapping("payroll")
public class PayrollController {

    WorkerQueryService workerQueryService;
    PayrollQueryService payrollQueryService;

    public PayrollController(WorkerQueryService workerQueryService, PayrollQueryService payrollQueryService) {
        this.workerQueryService = workerQueryService;
        this.payrollQueryService = payrollQueryService;
    }

    @GetMapping("workers")
    String workers(Model model) {
        return workers(model, new YearMonth(java.time.YearMonth.now()).toString());
    }

    @GetMapping("workers/{yearMonth}")
    String workers(Model model, @PathVariable("yearMonth") String yearMonth) {
        ContractingWorkers contractingWorkers = workerQueryService.contractingWorkers();
        model.addAttribute("workers", contractingWorkers);

        // TODO 型にする
        Map<String, Payroll> map = new HashMap<>();

        YearMonth month = new YearMonth(yearMonth);

        WorkMonth attributeValue = new WorkMonth(month);
        model.addAttribute("workMonth", attributeValue);

        for (Worker worker : contractingWorkers.list()) {
            Payroll payroll = payrollQueryService.getPayroll(worker, month);
            map.put(worker.workerNumber().toString(), payroll);
        }
        model.addAttribute("map", map);

        return "payroll/workers";
    }

    @GetMapping("{workerNumber}/list")
    String list(Model model, @PathVariable("workerNumber") WorkerNumber workerNumber) {
        Worker worker = workerQueryService.choose(workerNumber);
        model.addAttribute("worker", worker);

//        MonthlyAttendances monthlyAttendances = attendanceQueryService.findMonthlyAttendances(worker.workerNumber(), Date.now().yearMonth());
//        model.addAttribute("monthlyAttendances", monthlyAttendances);
        return "attendance/list";
    }
}
