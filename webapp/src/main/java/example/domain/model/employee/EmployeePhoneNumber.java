package example.domain.model.employee;

import javax.validation.Valid;

public class EmployeePhoneNumber {

    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    PhoneNumber phoneNumber;

	public EmployeePhoneNumber(EmployeeNumber employeeNumber, PhoneNumber phoneNumber) {
		this.employeeNumber = employeeNumber;
		this.phoneNumber = phoneNumber;
	}

	public EmployeeNumber employeeNumber() {
		return employeeNumber;
	}

	public PhoneNumber phoneNumber() {
		return phoneNumber;
	}
}
