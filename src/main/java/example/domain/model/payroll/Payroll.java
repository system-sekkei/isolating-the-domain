package example.domain.model.payroll;

import example.domain.model.attendance.AttendanceOfMonth;
import example.domain.model.worker.Worker;

/**
 * 給与
 */
public class Payroll {
    Worker worker;
    AttendanceOfMonth attendanceOfMonth;

    Amount amount() {
        return new Amount();
    }
}
