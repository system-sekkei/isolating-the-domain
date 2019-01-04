package example.domain.model.legislation;

import example.domain.type.time.Hour;
import example.domain.type.time.Minute;

/**
 * 時間外労働
 */
public class DailyOvertimeWork {

    Minute value;

    public static DailyOvertimeWork legal() {
        // 第三十二条第二項で定められている労働時間
        return new DailyOvertimeWork(new Hour(8).toMinute());
    }

    public DailyOvertimeWork(Minute value) {
        this.value = value;
    }

    public Minute overMinute(Minute minute) {
        if (minute.lessThan(value)) {
            return new Minute(0);
        }
        return minute.subtract(value);
    }
}
