package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 法定休日労働
 */
public class StatutoryWorkOnDaysOff {
    QuarterHour value;

    public StatutoryWorkOnDaysOff(QuarterHour value) {
        this.value = value;
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
