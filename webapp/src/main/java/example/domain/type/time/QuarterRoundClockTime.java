package example.domain.type.time;

/**
 * 15分単位の時刻
 */
public class QuarterRoundClockTime {

    Time value;

    public QuarterRoundClockTime(Time value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
