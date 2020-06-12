package example.domain.type.date;

import java.time.LocalDate;
import java.util.List;

/**
 * 週
 */
public class Week {
    Dates dates;

    public Week(Dates dates) {
        this.dates = dates;
    }

    public static Week from(List<LocalDate> list) {
        return new Week(new Dates(list));
    }
}
