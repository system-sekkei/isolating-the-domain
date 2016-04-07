package example.web.user;

import example.datasource.infrastructure.url.URLCoding;
import example.model.user.GenderType;
import example.model.user.User;
import example.model.user.UserId;
import example.model.user.validation.OnUpdate;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("user/update")
@SessionAttributes("user")
class UpdateController {

    private static final String[] allowFields ;
    static {
        allowFields = new String[] {
                "name",
                "dateOfBirth",
                "gender",
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

    //入り口
    @RequestMapping(method = RequestMethod.GET)
    String clearSessionAttribute(SessionStatus sessionStatus,@RequestParam(value="userId") String userId) {
        sessionStatus.setComplete();
        return "forward:/user/update/" + new URLCoding(userId).encode()+"/input";
    }

    @RequestMapping(value="/{userId}/input", method = RequestMethod.GET)
    String input(@PathVariable(value="userId") String userId,Model model) {
        model.addAttribute("user", getUser(userId)); //sessionAttributeに格納
        return "user/update/input";
    }

    private User getUser(String userId) {
        String decoded = new URLCoding(userId).decode();
        return userService.findById(new UserId(decoded));
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    String bindAndValidate(@Validated(OnUpdate.class) @ModelAttribute User user, BindingResult binding) {
        if (binding.hasErrors()) return "user/update/input";
        return "user/update/confirm";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    String update(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/user/update/complete";
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String complete(SessionStatus status) {
        status.setComplete();
        return "user/update/complete";
    }
}
