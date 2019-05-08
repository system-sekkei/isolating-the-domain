package example.domain.type.time;

/**
 * 15分単位の時刻
 */
public class QuarterRoundClockTimeRange {

    ClockTimeRange value;

    public QuarterRoundClockTimeRange(QuarterRoundClockTime begin, QuarterRoundClockTime end) {
        this.value = new ClockTimeRange(begin.value, end.value);
    }

    public QuarterHour between() {
        return new QuarterHour(value.between());
    }

    public ClockTimeRange clockTimeRange() {
        return value;
    }
}
