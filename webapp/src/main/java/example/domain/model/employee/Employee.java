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
        this(new EmployeeNumber(), new Name(), new MailAddress(), new PhoneNumber());
    }

    public Employee(EmployeeNumber employeeNumber, Name name, MailAddress mailAddress, PhoneNumber phoneNumber) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
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

    public Profile profile() {
        return new Profile(name, mailAddress, phoneNumber);
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
