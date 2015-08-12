package example.web.user;

import example.model.user.User;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user/registration")
@SessionAttributes("user")
public class RegistrationController {

    @Autowired
    UserService userService;

    @ModelAttribute
    User user() {
        return new User();
    }

    @RequestMapping(method = RequestMethod.GET)
    String start() {
        return "user/registration";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    String confirm(@Validated @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) return "user/registration";
        if (userService.findById(user.getId()).isPresent()) {
            result.reject("", new Object[]{user.getId().getValue()}, "ユーザー {0} は登録済みです");
            return "user/registration";
        }
        return "user/confirm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String register(@Validated @ModelAttribute User user, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "user/registration";
        if (userService.findById(user.getId()).isPresent()) {
            result.reject("", new Object[]{user.getId().getValue()}, "ユーザー {0} は登録済みです");
            return "user/registration";
        }
        userService.register(user);
        attributes.addFlashAttribute("userId", user.getId().getValue());
        return "redirect:/user/registration/complete";
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String complete(SessionStatus status) {
        status.setComplete();
        return "user/complete";
    }
}
