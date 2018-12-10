package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.ContractingWorkers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員の一覧
 */
@Controller
@RequestMapping("workers")
public class WorkersController {

    WorkerQueryService workerQueryService;

    @GetMapping("")
    String workers(Model model) {
        ContractingWorkers contractingWorkers = workerQueryService.contractingWorkers();
        model.addAttribute("workers", contractingWorkers);
        return "worker/list";
    }

    WorkersController(WorkerQueryService workerQueryService) {
        this.workerQueryService = workerQueryService;
    }
}
