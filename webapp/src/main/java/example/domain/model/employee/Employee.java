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

    public MailAddress mailAddress() {
        return profile.mailAddress;
    }

    public PhoneNumber phoneNumber() {
        return profile.phoneNumber;
    }

    public Profile profile() {
        return profile;
    }

    public EmployeeName employeeName() {
    	return new EmployeeName(employeeNumber, name());
    }

    public EmployeeMailAddress employeeMailAddress() {
    	return new EmployeeMailAddress(employeeNumber, mailAddress());
    }

    public EmployeePhoneNumber employeePhoneNumber() {
    	return new EmployeePhoneNumber(employeeNumber, phoneNumber());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", name=" + name() +
                ", mailAddress=" + mailAddress() +
                ", phoneNumber=" + phoneNumber() +
                '}';
    }
}
