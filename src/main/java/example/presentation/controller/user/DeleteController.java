package example.presentation.controller.user;

import example.application.service.UserService;
import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user/{userId}/delete")
public class DeleteController {

    UserService userService;

    @GetMapping(value = "view")
    String show(@PathVariable(value = "userId") UserIdentifier identifier, Model model) {
        User user = userService.findById(identifier);
        model.addAttribute("user", user);

        return "user/delete/confirm";
    }

    @GetMapping(value = "")
    String deleteThenRedirect(
            @PathVariable(value = "userId") UserIdentifier identifier,
            Model model, RedirectAttributes attributes) {
        User user = userService.findById(identifier);
        userService.delete(user);

        attributes.addAttribute("name", user.name().toString());

        return "redirect:/user/someone/delete/completed";
    }

    @GetMapping(value = "/completed")
    String showResult(Model model,
                      @RequestParam("name") String name) {
        model.addAttribute("name", name);
        return "user/delete/result";
    }

    DeleteController(UserService userService) {
        this.userService = userService;
    }
}
