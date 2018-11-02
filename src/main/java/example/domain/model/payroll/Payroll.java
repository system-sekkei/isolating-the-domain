package example.domain.model.payroll;

import example.domain.model.attendance.WorkTimes;
import example.domain.model.user.User;

/**
 * 給与
 */
public class Payroll {
    User user;
    WorkTimes workTimes;

    Amount amount() {
        return new Amount();
    }
}
