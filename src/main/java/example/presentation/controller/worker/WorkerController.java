package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 従業員の詳細
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
    public String init(Model model,
                       @RequestParam(value = "updateResult", required = false) String updateResult) {

        model.addAttribute("updateResult", updateResult);

        return "worker/worker";
    }
}
