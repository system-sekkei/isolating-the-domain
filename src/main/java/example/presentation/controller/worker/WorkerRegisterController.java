package example.presentation.controller.worker;

import example.application.service.worker.WorkerRecordService;
import example.domain.model.worker.Name;
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
 * 従業員の登録
 */
@Controller
@RequestMapping("workers/register")
@SessionAttributes({"newWorker"})
class WorkerRegisterController {

    private static final String[] accept =
            {
                    "name.value",
                    "mailAddress.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(accept);
    }

    WorkerRecordService workerRecordService;

    @GetMapping(value = "")
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/workers/register/input";
    }

    @GetMapping(value = "input")
    String showForm(Model model) {
        NewWorker newWorker = new NewWorker();
        model.addAttribute("newWorker", newWorker);
        return "worker/register/form";
    }

    @GetMapping(value = "input/again")
    String showFormToModify() {
        return "worker/register/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute("newWorker") NewWorker newWorker,
                    BindingResult result) {
        if (result.hasErrors()) return "worker/register/form";

        return "worker/register/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute("newWorker") NewWorker newWorker,
            SessionStatus status, RedirectAttributes attributes) {
        Name name = newWorker.name();
        WorkerNumber workerNumber = workerRecordService.prepareNewContract();
        workerRecordService.registerName(workerNumber, name);
        workerRecordService.registerMailAddress(workerNumber, newWorker.mailAddress());
        workerRecordService.registerPhoneNumber(workerNumber, newWorker.phoneNumber());
        workerRecordService.inspireContract(workerNumber);
        status.setComplete();

        attributes.addAttribute("name", name);
        attributes.addAttribute("workerNumber", workerNumber);

        return "redirect:/workers/register/completed";
    }

    @GetMapping(value = "completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("workerNumber") String workerNumber) {
        model.addAttribute("name", name);
        model.addAttribute("workerNumber", workerNumber);
        return "worker/register/result";
    }

    WorkerRegisterController(WorkerRecordService workerRecordService) {
        this.workerRecordService = workerRecordService;
    }
}
