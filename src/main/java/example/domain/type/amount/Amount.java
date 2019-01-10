package example.domain.type.amount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 金額
 */
public class Amount {

    BigDecimal value;

    public Amount(BigDecimal value) {
        this.value = value;
    }

    public Amount(Integer value) {
        this(BigDecimal.valueOf(value));
    }

    public Amount(String value) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            Number number = decimalFormat.parse(value);
            this.value = BigDecimal.valueOf(number.intValue());
        } catch (ParseException e) {
            throw new NumberFormatException(value);
        }
    }

    public Amount add(Amount other) {
        return new Amount(value.add(other.value));
    }

    public Amount multiply(Percentage rate, RoundingMode roundingMode) {
        return new Amount(value.multiply(rate.rate()).setScale(value.scale(), roundingMode.value));
    }

    @Override
    public String toString() {
        return new DecimalFormat("#,##0'円'").format(value.intValue());
    }

    public BigDecimal value() {
        return value;
    }
}
