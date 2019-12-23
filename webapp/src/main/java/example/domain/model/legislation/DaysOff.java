package example.domain.model.legislation;

import example.domain.type.date.Date;
import example.domain.type.date.Week;

/**
 * 休日
 */
public class DaysOff {
    Date value;

    public DaysOff(Date value) {
        this.value = value;
    }

    // 第三十五条 少なくとも週1日、もしくは4週間で4日以上の休日を与える必要がある
    public static DaysOff from(Week week) {
        // TODO: いまはひとまず、土曜日を法定休日とみなしている
        return new DaysOff(week.saturday());
    }

    public Date value() {
        return value;
    }
}
