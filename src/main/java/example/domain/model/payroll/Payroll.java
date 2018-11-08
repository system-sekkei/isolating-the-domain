package example.domain.model.payroll;

import example.domain.model.attendance.MonthlyAttendances;
import example.domain.model.worker.Worker;

/**
 * 給与
 */
public class Payroll {
    Worker worker;
    MonthlyAttendances monthlyAttendances;

    Amount amount() {
        return new Amount();
    }
}
