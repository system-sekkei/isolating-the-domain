package example.domain.model.daysoff;

import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;

/**
 * 休日
 */
public class DaysOff {
    EmployeeNumber employeeNumber;

    Date date;

    public DaysOff(EmployeeNumber employeeNumber, Date date) {
        this.employeeNumber = employeeNumber;
        this.date = date;
    }
}
