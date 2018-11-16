package example.domain.model.labour_standards_law;

import example.domain.type.time.HourTime;
import example.domain.type.time.HourTimeRange;

/**
 * 深夜
 */
public class Midnight {
    HourTimeRange value;

    public Midnight() {
        this.value = new HourTimeRange(
                new HourTime("22:00"),
                new HourTime("05:00")
        );
    }
    public HourTimeRange range() {return value;}
}
