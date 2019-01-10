package example.domain.type.time;

/**
 * 15分単位の時刻
 */
public class QuarterRoundClockTime {

    ClockTime value;

    public QuarterRoundClockTime(ClockTime value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
