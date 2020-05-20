package example.domain.model.timerecord.evaluation;

import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

/**
 * 深夜勤務時間
 */
public class NightWorkTime {

    QuarterHour value;

    public NightWorkTime(QuarterHour value) {
        this.value = value;
    }

    public NightWorkTime(Integer value) {
        this(new QuarterHour(new Minute(value)));
    }

    public QuarterHour quarterHour() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
