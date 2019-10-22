package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.Night;
import example.domain.model.timerecord.timefact.WorkRange;
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

    public NightBindingTime(WorkRange workRange) {
        this(workRange.quarterRoundClockTimeRange(), Night.legal());
    }

    public NightBindingTime(QuarterRoundClockTimeRange clockTimeRange, Night night) {
        this(new QuarterHour(night.nightMinute(clockTimeRange.clockTimeRange())));
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
