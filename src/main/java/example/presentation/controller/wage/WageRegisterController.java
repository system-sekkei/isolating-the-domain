package example.presentation.controller.wage;


import example.application.service.contract.ContractRecordService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.contract.HourlyWage;
import example.domain.model.contract.HourlyWageContract;
import example.domain.model.contract.MidnightExtraRate;
import example.domain.model.contract.OverTimeExtraRate;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.beans.factory.annotation.Value;
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
    OverTimeExtraRate overTimeExtraRate;
    MidnightExtraRate midnightExtraRate;

    public WageRegisterController(WorkerQueryService workerQueryService, ContractRecordService contractRecordService,
                                  @Value("${example.over-time-extra-rate}") Integer overTimeExtraRate,
                                  @Value("${example.midnight-extra-rate}") Integer midnightExtraRate) {
        this.workerQueryService = workerQueryService;
        this.contractRecordService = contractRecordService;

        this.overTimeExtraRate = new OverTimeExtraRate(overTimeExtraRate);
        this.midnightExtraRate = new MidnightExtraRate(midnightExtraRate);
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

    @PostMapping(value = "confirm")
    public String confirm(Worker worker,
                          @RequestParam("startDate") Date startDate,
                          @RequestParam("hourlyWage") HourlyWage hourlyWage,
                          Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("hourlyWage", hourlyWage);
        return "wage/confirm";
    }

    @PostMapping(value = "again")
    public String again(Worker worker,
                        @RequestParam("startDate") Date startDate,
                        @RequestParam("hourlyWage") HourlyWage hourlyWage,
                        Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("hourlyWage", hourlyWage);
        return "wage/form";
    }

    @PostMapping(value = "register")
    public String register(Worker worker,
                           @RequestParam("startDate") Date startDate,
                           @RequestParam("hourlyWage") HourlyWage hourlyWage) {
        HourlyWageContract hourlyWageContract = new HourlyWageContract(hourlyWage, overTimeExtraRate, midnightExtraRate);
        contractRecordService.registerHourlyWage(worker.workerNumber(), startDate, hourlyWageContract);
        return String.format("redirect:/wages/%d/register/completed", worker.workerNumber().value());
    }

    @GetMapping(value = "completed")
    String showResult(Worker worker) {
        return "wage/result";
    }
}
