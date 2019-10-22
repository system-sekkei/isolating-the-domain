package example.domain.model.timerecord.bindingtime;

import example.domain.model.timerecord.timefact.WorkRange;
import example.domain.type.time.QuarterHour;

/**
 * 拘束時間
 */
public class BindingTime {

    WorkRange workRange;

    public BindingTime(WorkRange workRange) {
        this.workRange = workRange;
    }

    public QuarterHour quarterHour() {
        return workRange.quarterRoundClockTimeRange().between();
    }

    @Override
    public String toString() {
        return quarterHour().toString();
    }
}
