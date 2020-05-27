package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 法定休日勤務時間
 */
public class LegalDaysOffWorkTime {

    QuarterHour value;

    public LegalDaysOffWorkTime(QuarterHour value) {
        this.value = value;
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    LegalDaysOffWorkTime add(LegalDaysOffWorkTime value) {
        return new LegalDaysOffWorkTime(this.value.add(value.value));
    }
}
