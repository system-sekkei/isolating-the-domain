package example.presentation.controller;

import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.ContractingWorkers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class TopController {
    WorkerQueryService workerQueryService;

    @ModelAttribute("workers")
    ContractingWorkers workers() {
        return workerQueryService.contractingWorkers();
    }

    @GetMapping
    String show() {
        return "dashboard";
    }

    TopController(WorkerQueryService workerQueryService) {
        this.workerQueryService = workerQueryService;
    }
}
