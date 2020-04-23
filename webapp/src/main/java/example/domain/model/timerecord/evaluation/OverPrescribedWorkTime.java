package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 所定超勤務時間
 */
public class OverPrescribedWorkTime {

    QuarterHour value;

    public OverPrescribedWorkTime(QuarterHour value) {
        this.value = value;
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
