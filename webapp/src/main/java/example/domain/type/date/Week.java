package example.domain.type.date;

import java.util.List;

/**
 * 週
 */
public class Week {
    Dates dates;

    public Week(Dates dates) {
        this.dates = dates;
    }

    public static Week from(List<Date> list) {
        return new Week(new Dates(list));
    }

    public boolean contains(Date date) {
        for (Date d: dates.list) {
            if (date.hasSameValue(d)) {
                return true;
            }
        }
        return false;
    }
}
