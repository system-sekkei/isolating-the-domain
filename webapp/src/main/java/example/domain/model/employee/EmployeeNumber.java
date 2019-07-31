package example.domain.model.employee;

/**
 * 従業員番号
 */
public class EmployeeNumber {

    Integer value;

    public EmployeeNumber() {
    }

    public EmployeeNumber(String value) {
        this.value = Integer.parseInt(value);
    }

    public EmployeeNumber(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
