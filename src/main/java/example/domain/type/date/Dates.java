package example.domain.type.date;

import java.util.List;

/**
 * 日付
 */
public class Dates {
    List<Date> list;

    public Dates(List<Date> list) {
        this.list = list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Date first() {
        return list.iterator().next();
    }
}
