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

/**
 * 従業員の更新
 */
@Controller
@RequestMapping("workers/{workerNumber}/update")
@SessionAttributes({"worker"})
class WorkerUpdateController {

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
        return "forward:/workers/" + workerNumber + "/update/input";
    }

    @GetMapping(value = "input")
    String formToEdit(@PathVariable(value = "workerNumber") WorkerNumber workerNumber,
                      Model model) {
        Worker worker = workerQueryService.choose(workerNumber);
        model.addAttribute("worker", worker);
        return "worker/update/form";
    }


    @PostMapping(value = "register")
    String registerThenRedirect(@Validated @ModelAttribute Worker worker,
                                BindingResult result,
                                SessionStatus status,
                                RedirectAttributes attributes) {
        if (result.hasErrors()) return "worker/update/form";

        workerRecordService.registerName(worker.workerNumber(), worker.name());
        workerRecordService.registerMailAddress(worker.workerNumber(), worker.mailAddress());
        workerRecordService.registerPhoneNumber(worker.workerNumber(), worker.phoneNumber());

        status.setComplete();

        attributes.addAttribute("updateResult","completed");

        return "redirect:/workers/" + worker.workerNumber();
    }

    WorkerUpdateController(WorkerRecordService workerRecordService, WorkerQueryService workerQueryService) {
        this.workerRecordService = workerRecordService;
        this.workerQueryService = workerQueryService;
    }
}
