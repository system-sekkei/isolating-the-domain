package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 法定外休日勤務時間
 */
public class NonLegalDaysOffWorkTime {

    QuarterHour value;

    public NonLegalDaysOffWorkTime(QuarterHour value) {
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
