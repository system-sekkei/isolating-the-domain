package example.domain.type.time;

import example.domain.validation.FormatCheck;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.DateTimeException;

/**
 * 分(数)
 */
public class Minute {
    @Min(value = 0, groups = FormatCheck.class, message = "分が負の値になっています")
    int value;

    @Deprecated
    Minute() {
    }

    public static Minute from(String time) {
        Integer value = time.isEmpty() ? 0 : Integer.parseInt(time);
        if (value < 0) {
            throw new DateTimeException("分が負の値になっています");
        }
        return new Minute(value);
    }

    public Minute(int time) {
        value = time;
    }

    public Minute subtract(Minute minute) {
        return add(0 - minute.value);
    }

    public Minute add(Minute minute) {
        return add(minute.value);
    }

    private Minute add(int value) {
        return new Minute(this.value + value);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    public QuarterHour quarterHourRoundUp() {
        return new QuarterHour((value % 15 == 0) ? this : new Minute((value / 15 + 1) * 15));
    }

    public boolean lessThan(Minute value) {
        return this.value < value.value;
    }

    public BigDecimal bigDecimalValue() {
        return BigDecimal.valueOf(value);
    }

    public boolean moreThan(Minute value) {
        return this.value > value.value;
    }

    public int toInt() {
        return value;
    }
}
