package example.presentation.controller.user;

import example.domain.model.user.GenderType;
import example.domain.model.user.User;
import example.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("user/register")
@SessionAttributes({"user"})
class RegisterController {

    private static final String[] allowFields =
            {
                    "identifier.value",
                    "name.value",

                    "dateOfBirth.value",
                    "gender.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(allowFields);
    }

    @Autowired
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

    @GetMapping(value = "/input")
    String showForm(Model model) {
        User user = userService.prototype();
        model.addAttribute("user", user);
        return "user/register/form";
    }

    @GetMapping(value = "/input/again")
    String showFormToModify() {
        return "user/register/form";
    }

    @PostMapping(value = "/confirm")
    String validate(@Valid @ModelAttribute User user,
                    BindingResult result) {
        if (result.hasErrors()) return "user/register/form";
        if (userService.isExist(user)) return formWithError(user, result);

        return "user/register/confirm";
    }

    private String formWithError(User user, BindingResult result) {
        String alreadyExists = "{0}は登録済みです";
        Object[] arguments = {user.identifier()};
        result.rejectValue("identifier.value",
                "error.id.already.exists", arguments,
                alreadyExists);
        return "user/register/form";
    }

    @GetMapping(value = "/register")
    String registerThenRedirect(@ModelAttribute User user,
                               SessionStatus status,
                               RedirectAttributes attributes) {
        userService.register(user);
        status.setComplete(); // セッション上のモデルに終了マーク

        attributes.addAttribute("name", user.name().toString());
        attributes.addAttribute("id", user.identifier().toString());

        return "redirect:/user/register/completed";
    }

    @GetMapping(value = "/completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("id") String id) {
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "user/register/result";
    }
}
