package example.domain.model.attendance;

import example.domain.type.time.QuarterHour;

/**
 * 総勤務時間
 */
public class TotalWorkTime {

    QuarterHour value;

    public TotalWorkTime(QuarterHour value) {
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
