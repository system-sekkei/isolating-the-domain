package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("worker/{workerNumber}/update")
@SessionAttributes({"worker"})
class UpdateController {

    private static final String[] allowFields =
            {
                    "name.value",
                    "mailAddress.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(allowFields);
    }

    WorkerQueryService workerQueryService;
    WorkerRecordService workerRecordService;

    @GetMapping("")
    String clearSessionAtStart(@PathVariable(value = "workerNumber") WorkerNumber workerNumber,
                               SessionStatus status) {
        status.setComplete();
        return "forward:/worker/" + workerNumber + "/update/input";
    }

    @GetMapping(value = "input")
    String formToEdit(@PathVariable(value = "workerNumber") WorkerNumber workerNumber,
                      Model model) {
        Worker worker = workerQueryService.choose(workerNumber);
        model.addAttribute("worker", worker);
        return "worker/update/form";
    }

    @GetMapping(value = "input/again")
    String formAgain() {
        return "worker/update/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute Worker worker,
                    BindingResult binding) {
        if (binding.hasErrors()) return "worker/update/form";

        return "worker/update/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirect(@ModelAttribute Worker worker,
                                SessionStatus status,
                                RedirectAttributes attributes) {
        workerRecordService.registerName(worker.workerNumber(), worker.name());
        workerRecordService.registerMailAddress(worker.workerNumber(), worker.mailAddress());
        workerRecordService.registerPhoneNumber(worker.workerNumber(), worker.phoneNumber());
        status.setComplete();

        attributes.addAttribute("name", worker.name().toString());
        attributes.addAttribute("workerNumber", worker.workerNumber().toString());

        return "redirect:/worker/someone/update/completed";
    }

    @GetMapping(value = "completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("workerNumber") String workerNumber) {
        model.addAttribute("name", name);
        model.addAttribute("workerNumber", workerNumber);
        return "worker/update/result";
    }

    UpdateController(WorkerRecordService workerRecordService, WorkerQueryService workerQueryService) {
        this.workerRecordService = workerRecordService;
        this.workerQueryService = workerQueryService;
    }
}
