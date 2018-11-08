package example.presentation.controller;

import example.application.service.worker.WorkerService;
import example.domain.model.worker.Workers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class TopController {
    WorkerService workerService;

    @ModelAttribute("users")
    Workers users() {
        return workerService.list();
    }

    @GetMapping
    String show() {
        return "top";
    }

    TopController(WorkerService workerService) {
        this.workerService = workerService;
    }
}
