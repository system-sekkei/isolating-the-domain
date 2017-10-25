package example.presentation.controller.user;

import example.application.service.UserService;
import example.domain.model.user.GenderType;
import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user/{userId}/update")
@SessionAttributes({"user"})
class UpdateController {

    private static final String[] allowFields =
            {
                    "email.value",
                    "name.value",
                    "dateOfBirth.value",
                    "gender.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(allowFields);
    }

    @ModelAttribute("genderTypes")
    GenderType[] addGendersToModel() {
        return GenderType.values();
    }

    @Autowired
    UserService userService;

    @GetMapping("")
    String clearSessionAtStart(@PathVariable(value = "userId") String userId,
                               SessionStatus status) {
        status.setComplete();
        return "forward:/user/" + userId + "/update/input";
    }

    @GetMapping(value = "input")
    String formToEdit(@PathVariable(value = "userId") UserIdentifier userId,
                      Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        System.out.println(user);
        return "user/update/form";
    }

    @GetMapping(value = "input/again")
    String formAgain() {
        return "user/update/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute User user,
                    BindingResult binding) {
        if (binding.hasErrors()) return "user/update/form";

        return "user/update/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirect(@ModelAttribute User user,
                                SessionStatus status,
                                RedirectAttributes attributes) {
        userService.update(user);
        status.setComplete();

        attributes.addAttribute("name", user.name().toString());
        attributes.addAttribute("email", user.email().toString());
        attributes.addAttribute("id", user.identifier().toString());

        return "redirect:/user/someone/update/completed";
    }

    @GetMapping(value = "completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("email") String email,
                      @RequestParam("id") String id) {
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("id", id);
        return "user/update/result";
    }
}
