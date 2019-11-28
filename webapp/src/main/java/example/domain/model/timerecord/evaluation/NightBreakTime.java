package example.domain.model.timerecord.evaluation;

import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

/**
 * 休憩時間（深夜）
 */
public class NightBreakTime {
    Minute value;

    @Deprecated
    NightBreakTime() {
    }

    public NightBreakTime(String value) {
        this(Minute.from(value));
    }

    public NightBreakTime(Minute value) {
        this.value = value;
    }

    public Minute minute() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public QuarterHour quarterHourRoundUp() {
        return value.quarterHourRoundUp();
    }
}
