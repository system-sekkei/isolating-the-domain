package example.domain.model.employee;

/**
 * 氏名の変更
 */
public class NameToChange {

    EmployeeNumber employeeNumber;

    Name name;

    public NameToChange(EmployeeNumber employeeNumber, Name name) {
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
