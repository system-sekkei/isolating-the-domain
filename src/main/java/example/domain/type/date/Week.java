package example.domain.type.date;

import java.time.LocalDate;
import java.util.List;

/**
 * 週
 */
public class Week {
    List<LocalDate> dates;

    public Week(List<LocalDate> dates) {
        this.dates = dates;
    }

    public static Week from(List<LocalDate> list) {
        return new Week(list);
    }
}
