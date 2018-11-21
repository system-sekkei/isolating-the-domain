package example.domain.model.contract;

import java.math.BigDecimal;

/**
 * 割増率
 */
public class ExtraPayRate {
    BigDecimal value;

    public ExtraPayRate(String value) {
        this.value = new BigDecimal(value);
    }
    public BigDecimal value() { return value;}
}
