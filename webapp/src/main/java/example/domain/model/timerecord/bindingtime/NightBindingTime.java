package example.domain.model.timerecord.bindingtime;

import example.domain.model.legislation.Night;
import example.domain.type.time.QuarterHour;
import example.domain.type.time.QuarterRoundClockTimeRange;

/**
 * 深夜拘束時間
 */
public class NightBindingTime {

    QuarterHour value;

    public NightBindingTime(QuarterHour value) {
        this.value = value;
    }

    public NightBindingTime(QuarterRoundClockTimeRange quarterRoundClockTimeRange) {
        this(quarterRoundClockTimeRange, Night.legal());
    }

    public NightBindingTime(QuarterRoundClockTimeRange clockTimeRange, Night night) {
        this(new QuarterHour(night.nightMinute(clockTimeRange.clockTimeRange())));
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
