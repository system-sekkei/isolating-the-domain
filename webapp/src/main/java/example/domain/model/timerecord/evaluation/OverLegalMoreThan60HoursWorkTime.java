package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 法定時間外労働 月60時間超 勤務時間
 */
public class OverLegalMoreThan60HoursWorkTime {

    QuarterHour value;

    public OverLegalMoreThan60HoursWorkTime(QuarterHour value) {
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
