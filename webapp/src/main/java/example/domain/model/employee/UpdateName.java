package example.domain.model.employee;

/**
 * 氏名の変更
 */
public class UpdateName {

    EmployeeNumber employeeNumber;

    Name name;

    public UpdateName(EmployeeNumber employeeNumber, Name name) {
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
