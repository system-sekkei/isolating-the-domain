package example.domain.type.amount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 金額
 */
public class Amount {

    int value;

    @Deprecated
    public Amount() {
    }

    public Amount(int value) {
        this.value = value;
    }

    public Amount(String value) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            Number number = decimalFormat.parse(value);
            this.value = number.intValue();
        } catch (ParseException e) {
            throw new NumberFormatException(value);
        }
    }

    public Amount add(Amount other) {
        return new Amount(value + other.value);
    }

    public Amount multiply(Percentage rate, RoundingMode roundingMode) {
        return new Amount(new BigDecimal(value()).multiply(rate.rate()).setScale(new BigDecimal(value()).scale(), roundingMode.value).intValue());
    }

    @Override
    public String toString() {
        return new DecimalFormat("#,##0'円'").format(value);
    }

    public int value() {
        return value;
    }

    public Amount addAll(Amount... amounts) {
        int total = value;
        for (Amount amount : amounts) {
            total = total + amount.value;
        }
        return new Amount(total);
    }
}
