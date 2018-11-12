package example.domain.model.payroll;

import java.math.BigDecimal;

/**
 * 賃金
 */
public class Wage {
    BigDecimal value;

    public Wage(BigDecimal value) {
        this.value = value;
    }

    public Wage add(Wage other) {
        return new Wage(value.add(other.value));
    }
}
