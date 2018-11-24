package example.domain.model.labour_standards_law;

import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;

/**
 * 深夜
 */
public class Midnight {
    ClockTimeRange value;

    public Midnight(ClockTimeRange value) {
        this.value = value;
    }

    public static Midnight legal() {
        // 第三十七条第四項で定められている深夜
        return new Midnight(new ClockTimeRange(
                new ClockTime("22:00"),
                new ClockTime("05:00")
        ));
    }

    public ClockTimeRange range() {return value;}
}
