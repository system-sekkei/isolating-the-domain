package example.presentation.controller.user;

import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.application.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/user/delete")
@SessionAttributes("user")
public class DeleteController {

    @Autowired
    UserService userService;

    //入り口 session attribute をクリアする
    @GetMapping
    String clearSessionAttribute(SessionStatus sessionStatus,@RequestParam(value="userId") String userId) {
        sessionStatus.setComplete();
        return "forward:/user/delete/" +userId + "/confirm";
    }

    @GetMapping(value="/{userId}/confirm")
    String input(@PathVariable(value="userId") UserIdentifier identifier,Model model) {
        User user = userService.findById(identifier);
        model.addAttribute("user", user); //sessionAttributeに格納
        return "user/delete/confirm";
    }

    @GetMapping(value = "/delete")
    String execute(@ModelAttribute User user,SessionStatus status) {
        userService.delete(user);
        status.setComplete();
        return "user/delete/result";
    }

}
