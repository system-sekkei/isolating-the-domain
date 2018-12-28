package example.domain.model.attendance;

import example.domain.type.time.QuarterHour;

/**
 * 総時間外労働時間
 */
public class TotalOverWorkTime {

    QuarterHour value;

    public TotalOverWorkTime(QuarterHour value) {
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
