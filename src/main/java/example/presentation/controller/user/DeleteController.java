package example.presentation.controller.user;

import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.application.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user/{userId}/delete")
public class DeleteController {

    @Autowired
    UserService userService;

    @GetMapping(value="/view")
    String input(@PathVariable(value="userId") UserIdentifier identifier,Model model) {
        User user = userService.findById(identifier);
        model.addAttribute("user", user);

        return "user/delete/confirm";
    }

    @GetMapping(value = "")
    String deleteThenRedirect(
            @PathVariable(value="userId") UserIdentifier identifier,
            Model model,
            RedirectAttributes attributes) {
        User user = userService.findById(identifier);
        userService.delete(user);

        attributes.addAttribute("name", user.name().toString());

        String redirectTo = String.format(
                "redirect:/user/%s/delete/completed",
                user.identifier().toString()
        );
        return redirectTo;
    }

    @GetMapping(value = "/completed")
    String showResult(Model model,
                      @RequestParam("name") String name) {
        model.addAttribute("name", name);
        return "user/delete/result";
    }

}
