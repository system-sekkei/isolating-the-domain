package example.domain.model.legislation;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 割増率（％）
 */
public class ExtraPayRate {
    Integer value;

    public ExtraPayRate(Integer value) {
        this.value = value;
    }

    public BigDecimal rate() {
        return BigDecimal.valueOf(value).divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
    }
}
