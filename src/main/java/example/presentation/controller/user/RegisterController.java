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
@SessionAttributes("user")
class RegisterController {

    private static final String[] allowFields ;
    static {
        allowFields = new String[] {
                "identifier.value",
                "name.value",
                "dateOfBirth.value",
                "gender.value",
                "phoneNumber.value",
        };
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(allowFields);
    }

    @Autowired
    UserService userService;

    @ModelAttribute("genderTypes")
    GenderType[] genderTypes() {
        return GenderType.values();
    }

    @GetMapping
    String start(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // session attribute をクリアするためにマーク
        return "forward:/user/register/input"; // session attribute のクリア実行
    }

    @GetMapping(value="/input")
    String form(Model model) {
        User user = userService.prototype();
        model.addAttribute("user", user); //sessionAttributeに格納
        return "user/register/form";
    }

    @PostMapping(value = "/confirm")
    String confirm(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) return "user/register/form";
        if (userService.isExist(user.identifier())) {
            result.rejectValue("identifier","", "ユーザー{0}は登録済みです");
            System.out.printf(result.toString());
            return "user/register/form";
        }

        return "user/register/confirm";
    }

    @GetMapping(value = "/register")
    String register(@ModelAttribute User user, RedirectAttributes attributes) {
        userService.register(user);
        attributes.addFlashAttribute("user", user);
        return "redirect:/user/register/complete";
    }

    @GetMapping(value = "/complete")
    String complete(SessionStatus status) {
        status.setComplete();
        return "user/register/result";
    }
}
