package example.presentation.controller.attendance;

import example.application.service.AttendanceService;
import example.application.service.UserService;
import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("attendance/{userId}")
public class AttendanceController {

    UserService userService;
    AttendanceService attendanceService;

    public AttendanceController(UserService userService, AttendanceService attendanceService) {
        this.userService = userService;
        this.attendanceService = attendanceService;
    }

    @GetMapping
    String init(Model model, @PathVariable("userId") UserIdentifier userIdentifier) {
        model.addAttribute("user", userService.findById(userIdentifier));
        model.addAttribute("attendanceOfDay", new AttendanceOfDay());
        return "attendance/form";
    }

    @PostMapping
    String register() {
        // TODO validation

        // TODO register
        // attendanceService.registerWorkTime();
        return "redirect:/";
    }
}
