package example.presentation.controller.user;

import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.application.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by numamino on 2015/08/12.
 */
@Controller
@RequestMapping("/user/delete")
@SessionAttributes("user")
public class DeleteController {

    @Autowired
    UserService userService;

    //入り口 session attribute をクリアする
    @RequestMapping(method = RequestMethod.GET)
    String clearSessionAttribute(SessionStatus sessionStatus,@RequestParam(value="userId") String userId) {
        sessionStatus.setComplete();
        return "forward:/user/delete/" +userId + "/confirm";
    }

    @RequestMapping(value="/{userId}/confirm", method = RequestMethod.GET)
    String input(@PathVariable(value="userId") String userId,Model model) {
        User user = userService.findById(new UserIdentifier(userId));
        model.addAttribute("user", user); //sessionAttributeに格納
        return "user/delete/confirm";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    String execute(@ModelAttribute User user,SessionStatus status) {
        userService.delete(user);
        status.setComplete();
        return "user/delete/result";
    }

}
