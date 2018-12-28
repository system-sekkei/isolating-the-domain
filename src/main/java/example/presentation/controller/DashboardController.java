package example.presentation.controller;

import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.ContractingWorkers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ダッシュボード
 */
@Controller
@RequestMapping("/")
class DashboardController {
    WorkerQueryService workerQueryService;

    @ModelAttribute("workers")
    ContractingWorkers workers() {
        return workerQueryService.contractingWorkers();
    }

    @GetMapping
    String show() {
        return "dashboard";
    }

    DashboardController(WorkerQueryService workerQueryService) {
        this.workerQueryService = workerQueryService;
    }
}
