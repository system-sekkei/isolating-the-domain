package example.domain.model.employee;

import javax.validation.Valid;

public class EmployeeMailAddress {

    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    MailAddress mailAddress;

	public EmployeeMailAddress(EmployeeNumber employeeNumber, MailAddress mailAddress) {
		this.employeeNumber = employeeNumber;
		this.mailAddress = mailAddress;
	}

	public EmployeeNumber employeeNumber() {
		return employeeNumber;
	}

	public MailAddress mailAddress() {
		return mailAddress;
	}
}
