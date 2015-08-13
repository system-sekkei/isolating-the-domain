package example.web.user;

import example.model.user.User;
import example.model.user.UserId;
import example.service.UserService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by numamino on 2015/08/12.
 */
@Controller
@RequestMapping("/user/deletion")
@SessionAttributes("user")
public class DeletionController {

    @Autowired
    UserService userService;

    @ModelAttribute
    User user(@RequestParam(required = false, value = "userId") UserId userId) {
        if (userId == null) return new User();
        return userService.findById(userId).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    String confirm() {
        return "user/deletion/confirm";
    }

    @RequestMapping(value = "execute", method = RequestMethod.GET)
    String execute(@ModelAttribute User user, RedirectAttributes attributes) {
        userService.delete(user);
        attributes.addFlashAttribute("userId", user.getId().getValue());
        return "redirect:/user/deletion/complete";
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String complete(SessionStatus status) {
        status.setComplete();
        return "user/deletion/complete";
    }
}
