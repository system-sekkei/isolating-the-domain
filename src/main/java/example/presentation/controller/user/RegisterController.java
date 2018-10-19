package example.presentation.controller.user;

import example.application.service.UserService;
import example.domain.model.user.GenderType;
import example.domain.model.user.User;
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
                    "identifier.value",
                    "name.value",
                    	"mailAddress.value",
                    "dateOfBirth.value",
                    "gender.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(accept);
    }

    UserService userService;

    @ModelAttribute("genderTypes")
    GenderType[] addGendersToModel() {
        return GenderType.values();
    }

    @GetMapping(value = "")
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/user/register/input";
    }

    @GetMapping(value = "input")
    String showForm(Model model) {
        User user = userService.prototype();
        model.addAttribute("user", user);
        return "user/register/form";
    }

    @GetMapping(value = "input/again")
    String showFormToModify() {
        return "user/register/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute("user") User user,
                    BindingResult result) {
        if (result.hasErrors()) return "user/register/form";
        if (userService.isExist(user)) return alreadyExists(user, result);

        return "user/register/confirm";
    }

    private String alreadyExists(User user, BindingResult result) {
        String rejectedPath = "identifier.value";
        String messageKey = "error.id.already.exists";
        Object[] arguments = {user.identifier()};
        String defaultMessage = "{0}は登録済みです";
        result.rejectValue(rejectedPath, messageKey, arguments, defaultMessage);
        return "user/register/form";
    }

    @GetMapping(value = "register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute User user,
            SessionStatus status, RedirectAttributes attributes) {
        userService.register(user);
        status.setComplete();

        attributes.addAttribute("name", user.name().toString());
        attributes.addAttribute("id", user.identifier().toString());

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
