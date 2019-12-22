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

    // 第三十五条 使用者は、労働者に対して、毎週少くとも一回の休日を与えなければならない。
    public static DaysOff from(Week week) {
        // TODO: いまはひとまず、土曜日を法定休日とみなしている
        return new DaysOff(week.saturday());
    }

    public Date value() {
        return value;
    }
}
