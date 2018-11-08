package example.presentation.controller.worker;

import example.application.service.WorkerService;
import example.domain.model.worker.UserCandidate;
import example.domain.model.worker.Worker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("worker/register")
@SessionAttributes({"worker"})
class RegisterController {

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

    WorkerService workerService;

    @GetMapping(value = "")
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/worker/register/input";
    }

    @GetMapping(value = "input")
    String showForm(Model model) {
        UserCandidate worker = new UserCandidate();
        model.addAttribute("worker", worker);
        return "worker/register/form";
    }

    @GetMapping(value = "input/again")
    String showFormToModify() {
        return "worker/register/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute("worker") UserCandidate worker,
                    BindingResult result) {
        if (result.hasErrors()) return "worker/register/form";

        return "worker/register/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute("worker") UserCandidate worker,
            SessionStatus status, RedirectAttributes attributes) {
        Worker registerdWorker = workerService.register(worker);
        status.setComplete();

        attributes.addAttribute("name", registerdWorker.name().toString());
        attributes.addAttribute("id", registerdWorker.identifier().toString());

        return "redirect:/worker/register/completed";
    }

    @GetMapping(value = "completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("id") String id) {
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "worker/register/result";
    }

    RegisterController(WorkerService workerService) {
        this.workerService = workerService;
    }
}
