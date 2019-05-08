package example.domain.model.timerecord.bindingtime;

import example.domain.model.legislation.Midnight;
import example.domain.type.time.QuarterHour;
import example.domain.type.time.QuarterRoundClockTimeRange;

/**
 * 深夜拘束時間
 */
public class MidnightBindingTime {

    QuarterHour value;

    public MidnightBindingTime(QuarterHour value) {
        this.value = value;
    }

    public MidnightBindingTime(QuarterRoundClockTimeRange quarterRoundClockTimeRange) {
        this(quarterRoundClockTimeRange, Midnight.legal());
    }

    public MidnightBindingTime(QuarterRoundClockTimeRange clockTimeRange, Midnight midnight) {
        this(new QuarterHour(midnight.midnightMinute(clockTimeRange.clockTimeRange())));
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
