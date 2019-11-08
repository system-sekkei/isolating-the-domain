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

    public DateTime value() {
        return value;
    }
}
