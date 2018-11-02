package example.presentation.controller.worktime;

import example.application.service.UserService;
import example.domain.model.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("worktime/{userId}")
public class WorkTimeController {

    UserService userService;

    public WorkTimeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String init(Model model, @PathVariable("userId") UserIdentifier userIdentifier) {
        model.addAttribute("user", userService.findById(userIdentifier));
        return "worktime/form";
    }

    @PostMapping
    String register() {
        return "redirect:/";
    }
}
