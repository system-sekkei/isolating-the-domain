package example.domain.model.employee;

import javax.validation.Valid;

/**
 * 従業員
 */
public class Employee {
    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    Profile profile;

    @Deprecated
    public Employee() {
        this(new EmployeeNumber(), new Name(), new MailAddress(), new PhoneNumber());
    }

    public Employee(EmployeeNumber employeeNumber, Name name, MailAddress mailAddress, PhoneNumber phoneNumber) {
        this.employeeNumber = employeeNumber;
        this.profile = new Profile(name, mailAddress, phoneNumber);
    }

    public Employee(EmployeeNumber employeeNumber, Profile profile) {
        this.employeeNumber = employeeNumber;
        this.profile = profile;
    }

    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    public Name name() {
        return profile.name;
    }

    public PhoneNumber phoneNumber() {
        return profile.phoneNumber;
    }

    public MailAddress mailAddress() {
        return profile.mailAddress;
    }

    public Profile profile() {
        return profile;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", name=" +profile.name +
                ", phoneNumber=" + profile.phoneNumber +
                ", mailAddress=" + profile.mailAddress +
                '}';
    }
}
