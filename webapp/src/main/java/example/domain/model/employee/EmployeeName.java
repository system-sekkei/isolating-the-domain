package example.domain.model.employee;

import javax.validation.Valid;

public class EmployeeName {

    @Valid
    EmployeeNumber employeeNumber;

    @Valid
    Name name;

	public EmployeeName(EmployeeNumber employeeNumber, Name name) {
		this.employeeNumber = employeeNumber;
		this.name = name;
	}

	public EmployeeNumber employeeNumber() {
		return employeeNumber;
	}

	public Name name() {
		return name;
	}
}
