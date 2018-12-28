package example.domain.model.attendance;

import example.domain.type.time.QuarterHour;

/**
 * 総深夜労働時間
 */
public class TotalMidnightWorkTime {

    QuarterHour value;

    public TotalMidnightWorkTime(QuarterHour value) {
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
