package example.domain.type.date;

import java.time.LocalDate;
import java.util.List;

/**
 * 日付
 */
// TODO: 削除する
public class Dates {
    List<LocalDate> list;

    public Dates(List<LocalDate> list) {
        this.list = list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public LocalDate first() {
        return list.iterator().next();
    }
}
