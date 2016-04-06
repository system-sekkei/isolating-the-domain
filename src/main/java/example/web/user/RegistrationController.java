package example.web.user;

import example.model.user.GenderType;
import example.model.user.User;
import example.model.user.validation.OnRegister;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user/registration")
@SessionAttributes("user")
class RegistrationController {

    @Autowired
    UserService userService;

    @ModelAttribute
    User user() {
        return new User();
    }

    @ModelAttribute("genderTypes")
    GenderType[] genderTypes() {
        return GenderType.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    String start() {
        return "user/registration/register";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    String confirm(@Validated(OnRegister.class) @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) return "user/registration/register";
        if (userService.findById(user.getId()) != null) {
            result.reject("", new Object[]{user.getId().getValue()}, "ユーザー {0} は登録済みです");
            return "user/registration/register";
        }
        return "user/registration/confirm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String register(@Validated(OnRegister.class) @ModelAttribute User user, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "user/registration/register";
        if (userService.findById(user.getId()) != null) {
            result.reject("", new Object[]{user.getId().getValue()}, "ユーザー {0} は登録済みです");
            return "user/registration/register";
        }
        userService.register(user);
        attributes.addFlashAttribute("userId", user.getId().getValue());
        return "redirect:/user/registration/complete";
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String complete(SessionStatus status) {
        status.setComplete();
        return "user/registration/complete";
    }
}
