package example.domain.model.timerecord.evaluation;

import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

import javax.validation.Valid;

/**
 * 休憩時間（深夜）
 */
public class NightBreakTime {

    @Valid
    Minute value;

    @Deprecated
    NightBreakTime() {
    }

    public NightBreakTime(Minute value) {
        this.value = value;
    }

    public static NightBreakTime from(String value) {
        return new NightBreakTime(Minute.from(value));
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
