package example.presentation.controller.worker;


import example.application.service.worker.WorkerQueryService;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("worker/wage/{workerNumber}")
class WageController {

    WorkerQueryService workerQueryService;

    public WageController(WorkerQueryService workerQueryService) {
        this.workerQueryService = workerQueryService;
    }

    @ModelAttribute("worker")
    Worker worker(@PathVariable(value = "workerNumber") WorkerNumber workerNumber) {
        return workerQueryService.choose(workerNumber);
    }

    @GetMapping
    public String init(Worker worker,
                       Model model) {
        return "worker/wage/form";
    }

    @PostMapping
    public String register(Worker worker,
                           @RequestParam("startDate") Date startDate,
                           @RequestParam("hourlyWage") HourlyWage hourlyWage,
                           Model model) {
        // TODO 時給の登録

        return "redirect:/attendance/workers";
    }
}
