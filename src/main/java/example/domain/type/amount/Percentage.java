package example.domain.type.amount;

import java.math.BigDecimal;

/**
 * 率（割増や税などの金額に掛けられるもの）
 */
public class Percentage {

    Integer value;

    public Percentage(Integer value) {
        this.value = value;
    }

    public BigDecimal rate() {
        return BigDecimal.valueOf(value).divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.UNNECESSARY);
    }
}
