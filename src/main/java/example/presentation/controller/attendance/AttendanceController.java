package example.presentation.controller.attendance;

import example.application.service.AttendanceService;
import example.application.service.UserService;
import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.user.User;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import example.domain.type.time.HourTime;
import example.domain.type.time.Minute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

/**
 * 勤怠コントローラー
 */
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

        attendanceService.registerWorkTime(
                // TODO 入力から
                new UserIdentifier("1"),
                new AttendanceOfDay(
                        new Date(LocalDate.now()),
                        new HourTime("09:00"),
                        new HourTime("18:00"),
                        new Minute(90)
                )
        );
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
