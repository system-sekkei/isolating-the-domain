package example.presentation.controller.user;

import example.application.service.UserService;
import example.domain.model.user.User;
import example.domain.model.user.UserCandidate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("user/register")
@SessionAttributes({"user"})
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

    UserService userService;

    @GetMapping(value = "")
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/user/register/input";
    }

    @GetMapping(value = "input")
    String showForm(Model model) {
        UserCandidate user = new UserCandidate();
        model.addAttribute("user", user);
        return "user/register/form";
    }

    @GetMapping(value = "input/again")
    String showFormToModify() {
        return "user/register/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute("user") UserCandidate user,
                    BindingResult result) {
        if (result.hasErrors()) return "user/register/form";

        return "user/register/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute("user") UserCandidate user,
            SessionStatus status, RedirectAttributes attributes) {
        User registerdUser = userService.register(user);
        status.setComplete();

        attributes.addAttribute("name", registerdUser.name().toString());
        attributes.addAttribute("id", registerdUser.identifier().toString());

        return "redirect:/user/register/completed";
    }

    @GetMapping(value = "completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("id") String id) {
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "user/register/result";
    }

    RegisterController(UserService userService) {
        this.userService = userService;
    }
}
