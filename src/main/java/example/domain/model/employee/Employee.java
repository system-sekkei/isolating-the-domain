package example.domain.model.employee;

import javax.validation.Valid;

/**
 * 従業員
 */
public class Employee {
    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    Name name;

    @Valid
    MailAddress mailAddress;

    @Valid
    PhoneNumber phoneNumber;

    @Deprecated
    public Employee() {
        employeeNumber = new EmployeeNumber();
        name = new Name();
        mailAddress = new MailAddress();
        phoneNumber = new PhoneNumber();
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public Name name() {
        return name;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public MailAddress mailAddress() {
        return mailAddress;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", mailAddress=" + mailAddress +
                '}';
    }
}
