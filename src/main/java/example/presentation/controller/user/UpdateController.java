package example.presentation.controller.user;

import example.domain.model.user.GenderType;
import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
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
@RequestMapping("user/update")
@SessionAttributes("user")
class UpdateController {

    private static final String[] allowFields ;
    static {
        allowFields = new String[] {
                "name",
                "dateOfBirth.value",
                "gender.value",
                "phoneNumber",
        };
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(allowFields);
    }

    @ModelAttribute("genderTypes")
    GenderType[] genderTypes() {
        return GenderType.values();
    }

    @Autowired
    UserService userService;

    @GetMapping
    String start(SessionStatus sessionStatus,@RequestParam(value="userId") String userId) {
        sessionStatus.setComplete(); // session Attribute をクリアするためにマークする
        return "forward:/user/update/" +userId + "/input"; // クリアの実行
    }

    @GetMapping(value="/{userId}/input")
    String formWithCurrentData(@PathVariable(value="userId") String userId,Model model) {
        User user = userService.findById(new UserIdentifier(userId));
        model.addAttribute("user", user); //session attribute("user")に格納する
        return "user/update/form";
    }

    @GetMapping(value="/input/again")
    String formAgain() {
        return "user/update/form";
    }

    @PostMapping(value = "/confirm")
    String validate(@Valid @ModelAttribute User user,
                           BindingResult binding, RedirectAttributes attributes) {
        if (binding.hasErrors()) return "user/update/form";
        attributes.addFlashAttribute("user", user);
        return "redirect:confirm";
    }

    @GetMapping(value = "/confirm")
    String show() {
        return "user/update/confirm";
    }

    @GetMapping(value = "/complete")
    String updateNow(@ModelAttribute User user, SessionStatus status) {
        userService.update(user);
        status.setComplete();
        return "user/update/result";
    }
}
