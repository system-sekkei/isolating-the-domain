package example.web.user;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
@RequestMapping("user/update")
@SessionAttributes("user")
class UpdateController {

    private static final String[] allowFields ;
    static {
        allowFields = new String[] {
                "UserId",
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

    @RequestMapping(method = RequestMethod.GET)
    String entryPoint(SessionStatus sessionStatus,@RequestParam(value="userId") UserId userId) {
        sessionStatus.setComplete();
        return "forward:/user/update/" + encode(userId.getValue())+"/start";
    }

    private String encode(String source) {
        try {
            String result;
            result = URLEncoder.encode(source, "UTF-8");
            System.out.println(result);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String decode(String source) {
        try {
            String result;
            result = URLDecoder.decode(source, "UTF-8");
            System.out.println(result);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="/{userId}/start", method = RequestMethod.GET)
    String init(@PathVariable(value="userId") String userId,Model model) {
        System.out.println(userId);
        User user = userService.findById(new UserId(decode(userId)));
        model.addAttribute("user", user);
        return "user/update/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String register(@Validated(OnUpdate.class) @ModelAttribute User user, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "user/update/register";
        userService.update(user);
        attributes.addFlashAttribute("userId", user.getId().getValue());
        return "redirect:/user/update/complete";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    String confirm(@Validated(OnUpdate.class) @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) return "user/update/register";
        return "user/update/confirm";
    }



    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String complete(SessionStatus status) {
        status.setComplete();
        return "user/update/complete";
    }
}
