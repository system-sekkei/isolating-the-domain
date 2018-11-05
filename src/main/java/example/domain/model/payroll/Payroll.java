package example.domain.model.payroll;

import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.user.User;

/**
 * 給与
 */
public class Payroll {
    User user;
    AttendanceOfMonth attendanceOfMonth;

    Amount amount() {
        return new Amount();
    }
}
