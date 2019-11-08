package example.domain.model.timerecord.evaluation;

import example.domain.model.legislation.Night;
import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.time.QuarterHour;

/**
 * 深夜拘束時間
 */
public class NightBindingTime {

    QuarterHour value;

    public NightBindingTime(QuarterHour value) {
        this.value = value;
    }

    public NightBindingTime(WorkRange workRange) {
        this(workRange, Night.legal());
    }

    public NightBindingTime(WorkRange workRange, Night night) {
        this(new QuarterHour(night.nightMinute(workRange)));
    }

    public QuarterHour quarterHour() {
        return value;
    }

    public NightWorkTime subtract(NightBreakTime nightBreakTime) {
        QuarterHour quarterHour = quarterHour().subtract(nightBreakTime.quarterHourRoundUp());
        return new NightWorkTime(quarterHour);
    }
}
