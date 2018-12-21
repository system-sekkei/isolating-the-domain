package example.presentation.controller.wage;

import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員ごとの時給の変遷
 */
@Controller
@RequestMapping("wages/{workerNumber}")
public class WageListController {

    WorkerQueryService workerQueryService;

    public WageListController(WorkerQueryService workerQueryService, ContractRecordService contractRecordService) {
        this.workerQueryService = workerQueryService;
    }

    @ModelAttribute("worker")
    Worker worker(@PathVariable(value = "workerNumber") WorkerNumber workerNumber) {
        return workerQueryService.choose(workerNumber);
    }

    @GetMapping("")
    public String list(Model model) {
        return "wage/list";
    }

}
