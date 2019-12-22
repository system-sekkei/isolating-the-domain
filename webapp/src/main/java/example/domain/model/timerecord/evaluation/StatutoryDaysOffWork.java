package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 法定休日労働
 */
public class StatutoryDaysOffWork {
    QuarterHour value;

    public StatutoryDaysOffWork(QuarterHour value) {
        this.value = value;
    }

    public QuarterHour quarterHour() {
        return value;
    }
}
