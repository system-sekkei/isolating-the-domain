package example.domain.type.datetime;


import example.domain.type.time.Minute;


/**
 * 15分単位の日時
 */
public class QuarterRoundDateTime {

    DateTime value;

    public QuarterRoundDateTime(DateTime value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static Minute between(QuarterRoundDateTime start, QuarterRoundDateTime end) {
        return DateTime.between(start.value, end.value);
    }

    public static Minute between(QuarterRoundDateTime start, DateTime end) {
        return DateTime.between(start.value, end);
    }

    public DateTime value() {
        return value;
    }

    public boolean isAfter(DateTime other) {
        return value.isAfter(other);
    }

    public boolean isBefore(DateTime other) {
        return value.isBefore(other);
    }
}
