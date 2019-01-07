package example.presentation.controller.payroll;

import example.application.service.payroll.PayrollQueryService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.payroll.Payrolls;
import example.domain.model.timerecord.WorkMonth;
import example.domain.model.worker.ContractingWorkers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 給与の一覧
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

    @GetMapping()
    String workers(Model model) {
        return workers(model, new WorkMonth());
    }

    @GetMapping("{workMonth}")
    String workers(Model model, @PathVariable("workMonth") WorkMonth workMonth) {
        ContractingWorkers contractingWorkers = workerQueryService.contractingWorkers();
        Payrolls payrolls = payrollQueryService.payrolls(contractingWorkers, workMonth);
        model.addAttribute("payrolls", payrolls);
        return "payroll/list";
    }
}
