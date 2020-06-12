package example.domain.model.daysoff;

import example.domain.model.employee.EmployeeNumber;

import java.time.LocalDate;

/**
 * 休日
 */
public class DaysOff {
    EmployeeNumber employeeNumber;

    LocalDate date;

    public DaysOff(EmployeeNumber employeeNumber, LocalDate date) {
        this.employeeNumber = employeeNumber;
        this.date = date;
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public LocalDate date() {
        return date;
    }
}
