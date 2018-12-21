package example.presentation.controller.wage;


import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 時給の変遷
 */
@Controller
@RequestMapping("wages/{workerNumber}/register")
class WageRegisterController {

    WorkerQueryService workerQueryService;
    ContractRecordService contractRecordService;

    public WageRegisterController(WorkerQueryService workerQueryService, ContractRecordService contractRecordService) {
        this.workerQueryService = workerQueryService;
        this.contractRecordService = contractRecordService;
    }

    @ModelAttribute("worker")
    Worker worker(@PathVariable(value = "workerNumber") WorkerNumber workerNumber) {
        return workerQueryService.choose(workerNumber);
    }

    @GetMapping
    public String init(Worker worker,
                       Model model) {
        return "wage/form";
    }

    @PostMapping(value="confirm")
    public String confirm(Worker worker,
                           @RequestParam("startDate") Date startDate,
                           @RequestParam("hourlyWage") HourlyWage hourlyWage,
                           Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("hourlyWage", hourlyWage);
        return "wage/confirm";
    }

    @PostMapping(value="again")
    public String again(Worker worker,
                          @RequestParam("startDate") Date startDate,
                          @RequestParam("hourlyWage") HourlyWage hourlyWage,
                          Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("hourlyWage", hourlyWage);
        return "wage/form";
    }

    @PostMapping(value="register")
    public String register(Worker worker,
                           @RequestParam("startDate") Date startDate,
                           @RequestParam("hourlyWage") HourlyWage hourlyWage) {
        contractRecordService.registerHourlyWage(worker.workerNumber(), startDate, hourlyWage);
        return String.format("redirect:/wages/%d/register/completed", worker.workerNumber().value());
    }

    @GetMapping(value="completed")
    String showResult(Worker worker) {
        return "wage/result";
    }
}
