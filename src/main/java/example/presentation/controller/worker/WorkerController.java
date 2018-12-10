package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員詳細
 */
@Controller
@RequestMapping("workers/{workerNumber}")
public class WorkerController {

    WorkerQueryService workerQueryService;

    public WorkerController(WorkerQueryService workerQueryService) {
        this.workerQueryService = workerQueryService;
    }

    @ModelAttribute("worker")
    Worker worker(@PathVariable(value = "workerNumber") WorkerNumber workerNumber) {
        return workerQueryService.choose(workerNumber);
    }

    @GetMapping
    public String init(Worker worker) {
        return "worker/worker";
    }
}
