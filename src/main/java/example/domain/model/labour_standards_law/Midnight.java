package example.domain.model.labour_standards_law;

import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;

/**
 * 深夜
 */
public class Midnight {
    ClockTimeRange value;

    public Midnight() {
        this.value = new ClockTimeRange(
                new ClockTime("22:00"),
                new ClockTime("05:00")
        );
    }
    public ClockTimeRange range() {return value;}
}
