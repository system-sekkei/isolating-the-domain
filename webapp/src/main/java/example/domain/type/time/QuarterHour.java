package example.domain.type.time;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 15分単位の時間
 */
public class QuarterHour {

    Minute value;

    public QuarterHour() {
        this(new Minute(0));
    }

    public QuarterHour(Minute value) {
        this.value = value;
    }

    public Minute minute() {
        return value;
    }

    public QuarterHour subtract(QuarterHour other) {
        return new QuarterHour(value.subtract(other.value));
    }

    public QuarterHour add(QuarterHour other) {
        return new QuarterHour(value.add(other.value));
    }

    @Override
    public String toString() {
        return HourAndMinute.from(value).toString();
    }

    public BigDecimal bigDecimalValue() {
        return value.bigDecimalValue().divide(BigDecimal.valueOf(60), 2, RoundingMode.UNNECESSARY);
    }
}
