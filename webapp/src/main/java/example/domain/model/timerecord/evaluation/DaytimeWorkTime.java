package example.domain.model.timerecord.evaluation;

import example.domain.type.time.QuarterHour;

/**
 * 日中勤務時間
 */
public class DaytimeWorkTime {

    QuarterHour value;

    public DaytimeWorkTime(QuarterHour value) {
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
