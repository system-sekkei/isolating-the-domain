package example.domain.model.payroll;

/**
 * 時給
 */
public class HourlyWage {
    Integer value;
    public HourlyWage(Integer value) {
        this.value = value;
    }

    public HourlyWage(String value) {
        this.value = Integer.parseInt(value);
    }

    public Integer value() {
        return value;
    }

    public String toString() {
        return value.toString();
    }
}
