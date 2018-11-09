package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.ContractingWorkers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("worker")
public class WorkerController {

    WorkerQueryService workerQueryService;

    @GetMapping("workers")
    String workers(Model model) {
        ContractingWorkers contractingWorkers = workerQueryService.contractingWorkers();
        model.addAttribute("workers", contractingWorkers);
        return "worker/workers";
    }

    WorkerController(WorkerQueryService workerQueryService) {
        this.workerQueryService = workerQueryService;
    }
}
