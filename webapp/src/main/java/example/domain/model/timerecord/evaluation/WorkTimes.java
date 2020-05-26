package example.domain.model.timerecord.evaluation;

import example.domain.type.time.Minute;
import example.domain.type.time.QuarterHour;

import java.util.List;

/**
 * 勤務時間のリスト
 */
public class WorkTimes {
    List<WorkTime> list;

    public WorkTimes(List<WorkTime> list) {
        this.list = list;
    }

    public QuarterHour overDailyLimitWorkTimeTotal() {
        QuarterHour total = new QuarterHour();
        for (WorkTime workTime : list) {
            total = total.add(workTime.overDailyLimitWorkTime());
        }
        return total;
    }

    public QuarterHour total() {
        QuarterHour total = new QuarterHour(new Minute(0));
        for (WorkTime workTime : list) {
            total = total.add(workTime.value);
        }
        return total;
    }
}
