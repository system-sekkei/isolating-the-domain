package example.domain.model.attendance;

import example.domain.type.time.QuarterHour;

/**
 * 週の勤務時間
 */
public class WeekWorkTime {

    QuarterHour value;

    public WeekWorkTime(QuarterHour value) {
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
