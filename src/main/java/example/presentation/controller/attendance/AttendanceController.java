package example.presentation.controller.attendance;

import example.application.service.AttendanceService;
import example.application.service.UserService;
import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("attendance")
public class AttendanceController {

    UserService userService;
    AttendanceService attendanceService;

    public AttendanceController(UserService userService, AttendanceService attendanceService) {
        this.userService = userService;
        this.attendanceService = attendanceService;
    }

    @GetMapping
    String init(Model model) {
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

    @GetMapping("{userId}/list")
    String list(Model model, @PathVariable("userId") UserIdentifier userIdentifier) {
        User user = userService.findById(userIdentifier);
        model.addAttribute("user", user);

        AttendanceOfMonth attendanceOfMonth = attendanceService.findMonthlyWorkTimes(user.identifier(), Date.now().yearMonth());
        model.addAttribute("attendanceOfMonth", attendanceOfMonth);
        return "attendance/list";
    }
}
